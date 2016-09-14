package cn.hongjitech.vehicle.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.adapter.StudentAdapter;
import cn.hongjitech.vehicle.bean.Student;
import cn.hongjitech.vehicle.http.NetworkAsyncTask;
import cn.hongjitech.vehicle.http.NetworkImgAsyncTask;
import cn.hongjitech.vehicle.javaBean.ExamAdminRoot;
import cn.hongjitech.vehicle.javaBean.InitializationRoot;
import cn.hongjitech.vehicle.javaBean.StudentInfo;
import cn.hongjitech.vehicle.javaBean.StudentInfoRoot;
import cn.hongjitech.vehicle.javaBean.User;
import cn.hongjitech.vehicle.parameter.BParameter;
import cn.hongjitech.vehicle.util.FilesUtil;
import cn.hongjitech.vehicle.util.SharedPrefsUtils;
import cn.hongjitech.vehicle.util.StringUtils;
import cn.hongjitech.vehicle.volleyHttp.VolleyManager;

/**
 * 考生考试主页面
 */
public class StuApproveActivity extends BaseActivity {

    @InjectView(R.id.lv_approve_list)
    ListView lv_approve_list;//学员信息列表
    @InjectView(R.id.iv_approve_begin_exam)
    ImageView iv_approve_begin_exam;//开始考试
    @InjectView(R.id.iv_approve_return)
    ImageView iv_approve_return;//返回
    @InjectView(R.id.tv_approve_stuInfo_name)
    TextView tv_approve_stuInfo_name;//学员姓名
    @InjectView(R.id.tv_approve_stuInfo_examcard)
    TextView tv_approve_stuInfo_examcard;//准考证号
    @InjectView(R.id.tv_approve_stuInfo_idcard)
    TextView tv_approve_stuInfo_idcard;//身份证号
    @InjectView(R.id.tv_approve_stuInfo_admin)
    TextView tv_approve_stuInfo_admin;//预约教练
    @InjectView(R.id.tv_approve_stuInfo_carType)
    TextView tv_approve_stuInfo_carType;//预约车型
    @InjectView(R.id.tv_approve_stuInfo_count)
    TextView tv_approve_stuInfo_count;//预约次数
    @InjectView(R.id.iv_approve_sys_photo)
    ImageView iv_approve_sys_photo;//公安系统照片
    @InjectView(R.id.iv_approve_exam_photo)
    ImageView iv_approve_exam_photo;//考试签到照片
    @InjectView(R.id.tv_approve_stuInfo_exam_time)
    TextView tv_approve_stuInfo_exam_time;//课时
    @InjectView(R.id.tv_approve_stuInfo_time)
    TextView tv_approve_stuInfo_time;//显示当前时间
    /*-----------------------------*/
    private final String TAG = "StuApproveActivity";
    private List<Student> students;
    private Intent intent;//跳转用Intent
    private Bitmap bitmapForSys;//公安系统获取到的图片
    private Bitmap bitmapForExam;//考试签到的图片
    private String car_num;//车牌号
    private String token;//访问服务器的token码
    private String url_student = "http://examination.91vh.com/api/candidate_information";//考生信息URL
    private String url_admin = "http://examination.91vh.com/api/examiner_information";//考官信息URL
    private String var = "1.0";//版本号
    private String number = "2";//考生个数
    private StudentInfoRoot studentInfoRoot;//考生信息根
    private StudentAdapter studentAdapter;//考生信息适配器
    private ExamAdminRoot examAdminRoot;//考官信息根
    private User user;//考生信息
    private String endTime;//预约结束的时间
    private TimeThread timeThread;//时间的线程
    private boolean timeFlag = true;//判断时间线程是否继续进行
    private Gson gson;//Gson对象
    private Map<String, String> paramMap;//设置访问服务器参数
    private NetworkAsyncTask networkAsyncTask;//服务器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_stu_approve);
        ButterKnife.inject(StuApproveActivity.this);
        initListener();
        initData();
    }

    /**
     * 设置测试数据
     */
    private void initData() {
        gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        timeThread = new TimeThread();
        timeThread.start();
        if (getCarNum() != null && !SharedPrefsUtils.getValue(StuApproveActivity.this, "token", "").equals("")) {
            car_num = getCarNum();
            token = SharedPrefsUtils.getValue(StuApproveActivity.this, "token", "");
            if (StringUtils.isNetworkConnected(StuApproveActivity.this)) {
                getStudentInfo();
            } else {
                Toast.makeText(StuApproveActivity.this, "当前网络不佳!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 读取保存的车牌号
     *
     * @return
     */
    private String getCarNum() {
        try {
            return FilesUtil.readFiles(StuApproveActivity.this, "carCode");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加载点击事件监听函数
     */
    private void initListener() {
        iv_approve_begin_exam.setOnClickListener(new ClickListener());
        iv_approve_return.setOnClickListener(new ClickListener());
        lv_approve_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                studentAdapter.setSelectedPosition(position);
                studentAdapter.notifyDataSetChanged();
                getAdminInfo();
                tv_approve_stuInfo_name.setText(studentAdapter.getItem(position).getXm());
                tv_approve_stuInfo_examcard.setText(studentAdapter.getItem(position).getZkzmbh());
                tv_approve_stuInfo_idcard.setText(studentAdapter.getItem(position).getSfzmhm());
                tv_approve_stuInfo_carType.setText(studentAdapter.getItem(position).getKscx());
                tv_approve_stuInfo_exam_time.setText(String.valueOf(Integer.parseInt(studentAdapter.getItem(position).getXxsj()) / 60) + "小时");
                tv_approve_stuInfo_count.setText(studentAdapter.getItem(position).getKscs() + "次");
                getImg(studentAdapter.getItem(position).getZp(),iv_approve_sys_photo);
                getImg(studentAdapter.getItem(position).getSign_photos(),iv_approve_exam_photo);
                initUserData(studentAdapter.getItem(position));
            }
        });
    }

    /**
     * 获取服务器系统图片
     */
    private void getImg(String url,ImageView iv) {
        VolleyManager.newInstance().ImageLoaderRequest
                (iv, url, R.drawable.default_ptr_flip, R.drawable.default_ptr_flip, 125, 250);
    }

    /**
     * 声明点击事件监听类
     */
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_approve_begin_exam://开始考试
                    //设置开始扣分判断
                    BParameter.ifStart = true;
                    if (user != null && !endTime.equals("")) {
                        sendImgUrl();
                    } else {
                        Toast.makeText(StuApproveActivity.this, "未找到考生!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.iv_approve_return://返回主页面
                    exitThread();
                    intent = new Intent(StuApproveActivity.this, MainActivity.class);
                    startActivity(intent);
                    StuApproveActivity.this.finish();
                    break;
            }
        }
    }

    /**
     * 加载User信息
     */
    private void initUserData(StudentInfo studentInfo) {
        user = new User();
        user.setUser_truename(studentInfo.getXm());
        user.setUser_id_card(studentInfo.getSfzmhm());
        endTime = StringUtils.getAfterTime(15);
    }

    /**
     * 访问服务器获取考生信息
     */
    private void getStudentInfo() {
        String param = "car_num=" + car_num + "&ver=" + var + "&timestamp=" + String.valueOf(System.currentTimeMillis()) + "&number=" + number;
        networkAsyncTask = new NetworkAsyncTask(url_student,
                "POST",
                token,
                param,
                handler,
                1);
        networkAsyncTask.execute();
    }

    /**
     * 访问服务器获取考官信息
     */
    private void getAdminInfo() {
        String param = "car_num=" + car_num + "&ver=" + var + "&timestamp=" + String.valueOf(System.currentTimeMillis());
        networkAsyncTask = new NetworkAsyncTask(url_admin,
                "POST",
                token,
                param,
                handler,
                2);
        networkAsyncTask.execute();
    }

    /**
     * 处理服务器获取的数据
     */
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            gson = new Gson();
            switch (msg.what) {
                case 0://返回结果为空
                    Log.e("访问服务器", "返回结果为空!");
                    break;
                case 1://访问服务器获取考生信息
                    studentInfoRoot = gson.fromJson(msg.obj.toString(), StudentInfoRoot.class);
                    if (studentInfoRoot.getResult().equals("succeed")) {
                        studentAdapter = new StudentAdapter(StuApproveActivity.this, studentInfoRoot.getData());
                        lv_approve_list.setAdapter(studentAdapter);
                    }
                    break;
                case 2:
                    examAdminRoot = gson.fromJson(msg.obj.toString(), ExamAdminRoot.class);
                    if (examAdminRoot.getResult().equals("succeed")) {
                        tv_approve_stuInfo_admin.setText(examAdminRoot.getData().getXm());
                    }
                    break;
                case 101:
                    InitializationRoot initializationRoot = gson.fromJson(msg.obj.toString(), InitializationRoot.class);
                    if (initializationRoot.getResult().equals("succeed")) {
                        exitThread();
                        intent = new Intent(StuApproveActivity.this, ExamProcessActivity.class);
                        intent.putExtra("activity", "stuApprove");
                        intent.putExtra("user", user);
                        intent.putExtra("endTime", endTime);
                        startActivity(intent);
                        StuApproveActivity.this.finish();
                    } else {
                        Toast.makeText(StuApproveActivity.this, "身份验证失败!", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
            return false;
        }
    });

    /**
     * 显示时间的线程
     */
    public class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    mHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (timeFlag);
        }
    }

    /**
     * 对组件进行绑定,在界面显示
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://时间相关
                    tv_approve_stuInfo_time.setText(StringUtils.getCurrentTime("HH:mm:ss"));
                    break;
            }
        }
    };

    /**
     * 获取考生信息对比
     */
    private void sendImgUrl() {
        paramMap = new HashMap<String, String>();
        paramMap.put("kskm", "2");
        paramMap.put("sfzmhm", user.getUser_id_card());
        paramMap.put("ksysfzmhm", examAdminRoot.getData().getSfzmhm());
        paramMap.put("car_num", car_num);
        paramMap.put("timestamp", String.valueOf(System.currentTimeMillis()));

        Map<String, File> fileMap = new HashMap<String, File>();
        fileMap.put("zp", new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/assess_fail.png"));
        NetworkImgAsyncTask networkImgAsyncTask = new NetworkImgAsyncTask("http://examination.91vh.com/api/information_match",
                token, paramMap, fileMap, handler, 101);
        networkImgAsyncTask.execute();
    }

    /**
     * 停止任务或进程
     */
    private void exitThread() {
        timeFlag = false;
    }
}
