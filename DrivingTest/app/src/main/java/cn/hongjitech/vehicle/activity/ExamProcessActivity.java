package cn.hongjitech.vehicle.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lkmap.DrawView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.adapter.MarkingAdapter;
import cn.hongjitech.vehicle.adapter.ProjectAdapter;
import cn.hongjitech.vehicle.bean.MarkingBean;
import cn.hongjitech.vehicle.bean.UserInfo;
import cn.hongjitech.vehicle.http.NetworkAsyncTask;
import cn.hongjitech.vehicle.http.NetworkImgAsyncTask;
import cn.hongjitech.vehicle.initTestData.ProjectTestData;
import cn.hongjitech.vehicle.javaBean.InitializationRoot;
import cn.hongjitech.vehicle.javaBean.User;
import cn.hongjitech.vehicle.map.TCPClient;
import cn.hongjitech.vehicle.map.TcpMessage;
import cn.hongjitech.vehicle.map.TcpPresenter;
import cn.hongjitech.vehicle.map.tcpConnectState;
import cn.hongjitech.vehicle.service.SerialPortService;
import cn.hongjitech.vehicle.util.ExamDataUtil;
import cn.hongjitech.vehicle.util.FilesUtil;
import cn.hongjitech.vehicle.util.ParsetoXWCJ;
import cn.hongjitech.vehicle.util.SerialPortSendUtil;
import cn.hongjitech.vehicle.util.SharedPrefsUtils;
import cn.hongjitech.vehicle.util.StringUtils;
import okhttp3.internal.framed.Variant;

/**
 * 开始进行考核,考试等流程
 * <p/>
 * 当进行车辆操作时判断,分两种情况,一种是考试或考核,扣分大于二十则直接结束
 * 训练,一直到手动点击结束为止
 */
public class ExamProcessActivity extends BaseActivity {

    @InjectView(R.id.iv_exam_peoMark)
    ImageView iv_exam_peoMark;//人工扣分
    @InjectView(R.id.iv_exam_over)
    ImageView iv_exam_over;//结束
    @InjectView(R.id.tv_exam_current_time)
    TextView tv_exam_current_time;//当前时间
    @InjectView(R.id.lv_exam_pro_info)
    ListView lv_exam_pro_info;//项目列表信息
    @InjectView(R.id.lv_exam_mark_info)
    ListView lv_exam_mark_info;//扣分列表信息
    @InjectView(R.id.tv_exam_stu_name)
    TextView tv_exam_stu_name;//学员姓名
    @InjectView(R.id.tv_exam_stu_id)
    TextView tv_exam_stu_id;//学员身份证号
    @InjectView(R.id.tv_exam_stu_startTime)
    TextView tv_exam_stu_startTime;//开始时间
    @InjectView(R.id.tv_exam_stu_useTime)
    TextView tv_exam_stu_useTime;//用时
    @InjectView(R.id.tv_exam_stu_passNum)
    TextView tv_exam_stu_passNum;//扣分次数
    @InjectView(R.id.tv_exam_stu_driveLong)
    TextView tv_exam_stu_driveLong;//已经行驶
    @InjectView(R.id.tv_exam_mark_speed)
    TextView tv_exam_mark_speed;//车速
    @InjectView(R.id.tv_exam_mark_gear)
    TextView tv_exam_mark_gear;//档位
    @InjectView(R.id.iv_exam_right_map)
    ImageView iv_exam_right_map;//地图上地图按钮
    @InjectView(R.id.iv_exam_right_sigle)
    ImageView iv_exam_right_sigle;//地图上信号按钮
    @InjectView(R.id.ll_single_parent)
    LinearLayout ll_single_parent;//信号的布局
    @InjectView(R.id.root)
    LinearLayout my_layout;//地图父布局
    @InjectView(R.id.max)
    ImageView max;//地图的放大
    @InjectView(R.id.min)
    ImageView min;//地图的缩小
    /*----------------------------------*/
    //信号显示页面的组件
    @InjectView(R.id.iv_fire_one)
    ImageView iv_fire_one;//点火1
    @InjectView(R.id.iv_wiper)
    ImageView iv_wiper;//雨刷
    @InjectView(R.id.iv_fire_two)
    ImageView iv_fire_two;//点火二
    @InjectView(R.id.iv_speaker)
    ImageView iv_speaker;//喇叭
    @InjectView(R.id.iv_brake_status)
    ImageView iv_brake_status;//制动器
    @InjectView(R.id.iv_door)
    ImageView iv_door;//车门
    @InjectView(R.id.iv_belt_info)
    ImageView iv_belt_info;//安全带
    @InjectView(R.id.iv_clutch)
    ImageView iv_clutch;//离合
    @InjectView(R.id.iv_warning_light)
    ImageView iv_warning_light;//报警灯
    @InjectView(R.id.iv_hand_brake)
    ImageView iv_hand_brake;//手刹
    @InjectView(R.id.iv_position_light)
    ImageView iv_position_light;//视宽灯
    @InjectView(R.id.iv_assistant_brake_status)
    ImageView iv_assistant_brake_status;//副刹车
    @InjectView(R.id.iv_left_turn_light)
    ImageView iv_left_turn_light;//左转灯
    @InjectView(R.id.iv_right_turn_light)
    ImageView iv_right_turn_light;//右转灯
    @InjectView(R.id.iv_central_rearview_mirror)
    ImageView iv_central_rearview_mirror;//中央后视镜
    @InjectView(R.id.iv_dipped_headlight)
    ImageView iv_dipped_headlight;//近光灯
    @InjectView(R.id.iv_seat_adjustment)
    ImageView iv_seat_adjustment;//座椅调整
    @InjectView(R.id.iv_high_beam_light)
    ImageView iv_high_beam_light;//远光灯
    @InjectView(R.id.iv_foglight)
    ImageView iv_foglight;//雾灯
    @InjectView(R.id.tv_ultrasonic_one)
    TextView tv_ultrasonic_one;//超声波距离信息1
    @InjectView(R.id.tv_ultrasonic_two)
    TextView tv_ultrasonic_two;//超声波距离信息2
    @InjectView(R.id.tv_exam_mark_rpm)
    TextView tv_exam_mark_rpm;//转速
    @InjectView(R.id.waihoushijing)
    ImageView waihoushijing;//左后视镜
    /*----------------------------------*/
    private Intent intent;//跳转Intent
    private TimeThread timeThread;//时间的线程
    private boolean timeFlag = true;//判断时间线程是否继续进行
    private List<String> projects;//项目列表
    private List<MarkingBean> markingBeens;//扣分信息列表
    private Button bt_dialog_exam_exit;//dialog的取消
    private Button bt_dialog_exam_sure;//dialog的确定
    private AlertDialog alertDialog;//dialog对象,提示是否退出考试
    private String activityType = "";//接收并判断由哪个界面跳转过来
    private AlertDialog alertDialogForPass;//dialog对象,提示是否通过考试
    private ImageView iv_dialog_test_face;//dialog,表情图片
    private TextView tv_dialog_test_face;//dialog,提示文字的信息
    private ImageButton ib_dialog_test_returnExam;//dialog,补考
    private ImageButton ib_dialog_test_over;//dialog,结束考试
    private User user = new User();//用户对象
    private long startTime = 0;//开始时间
    private ProjectAdapter projectAdapter;//项目列表适配器
    private MarkingAdapter markingAdapter;//扣分情况适配器
    private UserInfo userInfo;//学员信息及考核信息
    private MarkingBean markingBean;//扣分信息
    private List<MarkingBean> list;//扣分信息集合
    private int fraction = 0;//纪录扣分的分数
    private ExecutorService executorService;//线程池对象
    private List<String> timeList;//纪录抓拍时间
    private String startTime_random;//随机开始时间
    private String endTime_random;//随机结束时间
    private SerialPortThread serialPortThread;//串口数据接收并显示
    private boolean serialPortThreadFlag = true;//判断串口接收是否开启或停止
    private String subject = "2";//科目
    private String markingSubjrct;//扣分项目
    private String proSubject = "123";//项目名称
    private String url_subject = "http://examination.91vh.com/api/start_examination";//上传项目
    private String url_examPro = "http://examination.91vh.com/api/end_examination";//上传单一项目完成的URL
    private int examIndex = 2;//纪录考试的次数
    /*---------------------地图----------------------------*/
    public static String ipString = "10.10.100.150";//链接地图的ip地址
    public static int portnum = 8899;//端口号
    private DrawView my_view;//地图显示的组件
    private static int showtag = 4;//默认地图大小
    //sd卡地图文件的地址
    String TuFile_Path = Environment.getExternalStorageDirectory().getPath() + "/tu.tu";
    String CheFile_Path = Environment.getExternalStorageDirectory().getPath() + "/che.che";
    private Gson gson;//Gson对象
    private Map<String, String> paramMap;//设置访问服务器参数
    private String TAG = "ExamProcessActivity";
    private NetworkAsyncTask networkAsyncTask;//服务器对象
    private int startExamC = 0;//判断刚刚进入准备工作
    private ParsetoXWCJ parsetoXWCJ;
    private int seatTag = 0;//纪录座椅是否调整过
    private int centralTag = 0;//纪录中央后视镜是否调整过
    private int leftTag = 0;//纪录左后视镜是否调整过
    private ExamDataUtil examDataUtil;//处理练一练数据工具类
    private String proStr = "倒车入库";//记录选中显示的项目名称
    private int goState = 0;//记录档位信息,判断是否显示起步状态
    private int index = 0;//线程进入的次数
    private TcpPresenter tp = TcpPresenter.getInstance();
    private TextView tv1, tv2;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exam_process);
        ButterKnife.inject(ExamProcessActivity.this);
        EventBus.getDefault().register(this);
        initListener();
        ll_single_parent.setVisibility(View.GONE);//隐藏信号的布局

        //判断传过来的参数是否为空
        if (getIntent().getStringExtra("activity") != null && getIntent().getSerializableExtra("user") != null && getIntent().getStringExtra("endTime") != null) {
            activityType = getIntent().getStringExtra("activity");
            endTime_random = getIntent().getStringExtra("endTime");
            user = (User) getIntent().getSerializableExtra("user");
            startTime_random = StringUtils.getCurrentTime("HH:mm:ss");
            initUserData();
        }
        //测试数据,现已隐藏
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
    }

    /**
     * 显示串口数据线程
     */
    class SerialPortThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (serialPortThreadFlag) {
                try {
                    //延迟500毫秒接收数据
                    Thread.sleep(500);
                    mHandler.sendEmptyMessage(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 开启串口数据接收
     */
    private void startSerialPortService() {
        //向串口发送开启命令
        SerialPortSendUtil serialPortSendUtil = new SerialPortSendUtil();
        serialPortSendUtil.sendMessage(new byte[]{0x55, (byte) 0xAA, 0x01, 0x01, 0x01});
        //打开串口数据接收服务
        intent = new Intent(ExamProcessActivity.this, SerialPortService.class);
        startService(intent);
    }

    /**
     * 加载用户数据
     */
    private void initUserData() {
        index = 0;
        seatTag = 0;
        centralTag = 0;
        leftTag = 0;
        goState = 0;
        timeFlag = true;
        serialPortThreadFlag = true;
        mp = new MediaPlayer();
        intMap();
        initTcp();
        examDataUtil = new ExamDataUtil(ExamProcessActivity.this);
        gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        tv_exam_stu_name.setText(user.getUser_truename());
        tv_exam_stu_id.setText(user.getUser_id_card());
        tv_exam_stu_startTime.setText(StringUtils.getCurrentTime("HH:mm:ss"));

        startTime = System.currentTimeMillis();
        timeThread = new TimeThread();
        serialPortThread = new SerialPortThread();

        try {
            parsetoXWCJ = new ParsetoXWCJ(ExamProcessActivity.this);
            startSerialPortService();
        } catch (UnsatisfiedLinkError u) {
            Log.e("Error", u.toString());
            Toast.makeText(ExamProcessActivity.this, "未找到相关设备数据连接,请重试!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }

        //创建线程池,将费时操作放进线程池中
        executorService = Executors.newFixedThreadPool(2);
        executorService.execute(timeThread);
        executorService.execute(serialPortThread);

        //加载项目列表信息(暂时临时数据)
        ProjectTestData p = new ProjectTestData();
        projectAdapter = new ProjectAdapter(ExamProcessActivity.this, p.setProjectTestData());
        lv_exam_pro_info.setAdapter(projectAdapter);
        lv_exam_pro_info.setSelection(0);
        projectAdapter.setSelectedPosition(0);

        //加载扣分情况的列表数据(模拟数据)
        list = new ArrayList<MarkingBean>();
//        for (int i = 0; i < 2; i++) {
//            list.add(new MarkingBean("起步", "2", "操作不当发动机熄火" + i));
//        }

        //添加适配器
        markingAdapter = new MarkingAdapter(ExamProcessActivity.this, list);
        lv_exam_mark_info.setAdapter(markingAdapter);
        tv_exam_stu_passNum.setText(String.valueOf(list.size()));

        //判断接收的结束时间是否为空,进行随机时间的生成
        if (endTime_random != null) {
            timeList = new ArrayList<String>();
            for (int i = 0; i < 5; i++) {
                if (StringUtils.randomDate(startTime_random, endTime_random, "HH:mm:ss") != null) {
                    timeList.add(StringUtils.randomDate(startTime_random, endTime_random, "HH:mm:ss"));
                    Log.d("TAG", timeList.get(i).toString());
                } else {
                    Log.e("Error", "获取随机时间失败!");
                }
            }
        }
    }

    /**
     * 时间计数器，最多只能到99小时
     */
    public String showTimeCount(long time) {
        if (time >= 360000000) {
            return "00:00:00";
        }
        String timeCount = "";
        long hourc = time / 3600000;
        String hour = "0" + hourc;
        hour = hour.substring(hour.length() - 2, hour.length());

        long minuec = (time - hourc * 3600000) / (60000);
        String minue = "0" + minuec;
        minue = minue.substring(minue.length() - 2, minue.length());

        long secc = (time - hourc * 3600000 - minuec * 60000) / 1000;
        String sec = "0" + secc;
        sec = sec.substring(sec.length() - 2, sec.length());
        timeCount = hour + ":" + minue + ":" + sec;
        return timeCount;
    }

    /**
     * 加载点击事件监听函数
     */
    private void initListener() {
        iv_exam_peoMark.setOnClickListener(new ClickListener());
        iv_exam_over.setOnClickListener(new ClickListener());
        iv_exam_right_map.setOnClickListener(new ClickListener());
        iv_exam_right_sigle.setOnClickListener(new ClickListener());
        max.setOnClickListener(new ClickListener());
        min.setOnClickListener(new ClickListener());
        //设置项目列表的item监听事件
//        lv_exam_pro_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                projectAdapter.setSelectedPosition(position);
//                projectAdapter.notifyDataSetChanged();
//                //设置正在进行项在最上面显示
//                lv_exam_pro_info.setSelection(position);
//                proSubject = projectAdapter.getItem(position);
//                postExamPro();
//            }
//        });
    }

    /**
     * 声明点击事件监听类
     */
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_exam_peoMark://人工扣分
                    intent = new Intent(ExamProcessActivity.this, ArtificialPointsDialogActivity.class);
                    startActivityForResult(intent, 0);
                    ExamProcessActivity.this.overridePendingTransition(R.anim.dialogactivity_artpoi, R.anim.dialogactivity_artpoi_exit);
                    break;
                case R.id.iv_exam_over://结束
                    if (activityType.equals("trainTest")) {//训练
                        setUserInfo();
                        intent = new Intent(ExamProcessActivity.this, SignatureActivity.class);
                        intent.putExtra("activity", activityType);
                        intent.putExtra("userInfo", userInfo);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        ExamProcessActivity.this.finish();
                        exitThread();
                    } else {    //考试或考核弹出dialog提示
                        showDialogForExamOver();
                    }
                    break;
                case R.id.bt_dialog_exam_exit://dialog的取消
                    alertDialog.cancel();
                    break;
                case R.id.bt_dialog_exam_sure://dialog的确定
                    alertDialog.cancel();
                    if (activityType.equals("stuApprove")) {//考试
                        showDialogForPass();
                    } else if (activityType.equals("training")) {//考核
                        setUserInfo();
                        intent = new Intent(ExamProcessActivity.this, AssessActivity.class);
                        intent.putExtra("userInfo", userInfo);
                        intent.putExtra("user", user);
                        startActivity(intent);
                        ExamProcessActivity.this.finish();
                    }
                    exitThread();
                    break;
                case R.id.ib_dialog_test_returnExam://考试完成dialog,补考
                    examIndex--;
                    alertDialogForPass.cancel();
                    initUserData();
                    break;
                case R.id.ib_dialog_test_over://考试完成dialog,结束考试
                    if (examIndex == 1) {
                        sendOverImgUrl();
                    }
                    alertDialogForPass.cancel();
                    intent = new Intent(ExamProcessActivity.this, StuApproveActivity.class);
                    startActivity(intent);
                    ExamProcessActivity.this.finish();
                    exitThread();
                    break;
                case R.id.iv_exam_right_map://地图上地图按钮
                    iv_exam_right_map.setImageResource(R.drawable.map_check);
                    iv_exam_right_sigle.setImageResource(R.drawable.single_no_check);
                    ll_single_parent.setVisibility(View.GONE);
                    my_layout.setVisibility(View.VISIBLE);
                    max.setVisibility(View.VISIBLE);
                    min.setVisibility(View.VISIBLE);
                    break;
                case R.id.iv_exam_right_sigle://地图上信号按钮
                    iv_exam_right_map.setImageResource(R.drawable.map_no_check);
                    iv_exam_right_sigle.setImageResource(R.drawable.single_check);
                    my_layout.setVisibility(View.GONE);
                    max.setVisibility(View.GONE);
                    min.setVisibility(View.GONE);
                    ll_single_parent.setVisibility(View.VISIBLE);
                    break;
                case R.id.max://地图放大
                    showtag--;
                    if (showtag == 0) {
                        showtag = 1;
                    }
                    changeSize(showtag);
                    break;
                case R.id.min://地图缩小
                    showtag++;
                    if (showtag == 7) {
                        showtag = 6;
                    }
                    changeSize(showtag);
                    break;
            }
        }
    }

    /**
     * 设置UserInfo的数据
     */
    private void setUserInfo() {
        if (user != null) {
            userInfo = new UserInfo();
            userInfo.setName(user.getUser_truename());
            userInfo.setIdCode(user.getUser_id_card());
            userInfo.setStartTime(tv_exam_stu_startTime.getText().toString().substring(0, 6));
            userInfo.setEndTime(StringUtils.getCurrentTime("HH:mm:ss"));
            userInfo.setLength(tv_exam_stu_driveLong.getText().toString());
            userInfo.setNum(tv_exam_stu_passNum.getText().toString());
            userInfo.setUserTime(tv_exam_stu_useTime.getText().toString());
            userInfo.setMarkingBean(list);
        } else {
            Log.e("Error", "user为null");
        }
    }

    /**
     * 设置结束显示的窗口提醒
     */
    private void showDialogForExamOver() {
        View view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_exam, null);
        alertDialog = new AlertDialog.Builder(this).setTitle(null).setMessage(null).setView(view).create();
        bt_dialog_exam_exit = (Button) view.findViewById(R.id.bt_dialog_exam_exit);
        bt_dialog_exam_sure = (Button) view.findViewById(R.id.bt_dialog_exam_sure);
        bt_dialog_exam_exit.setOnClickListener(new ClickListener());
        bt_dialog_exam_sure.setOnClickListener(new ClickListener());
        alertDialog.show();
    }

    /**
     * 设置结束后是否通过的窗口提醒
     */
    private void showDialogForPass() {
        View view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_exam_error, null);
        alertDialogForPass = new AlertDialog.Builder(this).setTitle(null).setMessage(null).setView(view).create();
        iv_dialog_test_face = (ImageView) view.findViewById(R.id.iv_dialog_test_face);
        tv_dialog_test_face = (TextView) view.findViewById(R.id.tv_dialog_test_face);
        ib_dialog_test_returnExam = (ImageButton) view.findViewById(R.id.ib_dialog_test_returnExam);
        ib_dialog_test_over = (ImageButton) view.findViewById(R.id.ib_dialog_test_over);
        ib_dialog_test_returnExam.setOnClickListener(new ClickListener());
        ib_dialog_test_over.setOnClickListener(new ClickListener());
        if (judgmentFraction()) {
            ib_dialog_test_returnExam.setClickable(true);
            iv_dialog_test_face.setImageResource(R.drawable.assess_fail);
            tv_dialog_test_face.setText("很抱歉!考试不合格!");
        } else {
            ib_dialog_test_returnExam.setClickable(false);
            iv_dialog_test_face.setImageResource(R.drawable.test_success);
            tv_dialog_test_face.setText("恭喜您!考试合格!");
        }
        if (examIndex > 1) {
            ib_dialog_test_returnExam.setClickable(true);
        } else {
            ib_dialog_test_returnExam.setClickable(false);
            Toast.makeText(ExamProcessActivity.this, "您今天已没有考试机会!", Toast.LENGTH_SHORT).show();
        }
        alertDialogForPass.show();
    }

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
                    tv_exam_current_time.setText(StringUtils.getCurrentTime("HH:mm:ss"));
                    tv_exam_stu_useTime.setText(showTimeCount(System.currentTimeMillis() - startTime));
//                    if (timeList.size() != 0) {
//                        for (int i = 0; i < timeList.size(); i++) {
//                            if (StringUtils.getCurrentTime("HH:mm:ss").equals(timeList.get(i))) {
//                                //进行拍照
//                                Log.d("TAG", "进入随机拍照");
//                                sendImgUrl();
//                            }
//                        }
//                    }
                    break;
                case 1:
                    tv_exam_mark_speed.setText(SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_speed", "0") + "km/h");//车速
                    tv_exam_mark_gear.setText(SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_gear_info", "0"));//档位
                    tv_exam_mark_rpm.setText(SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_rpm", "0") + "r/min");

                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_engine_status", "").equals("03")) {
                        if (goState == 0) {
                            projectAdapter.setSelectedPosition(1);
                            projectAdapter.notifyDataSetChanged();
                            lv_exam_pro_info.setSelection(1);
                        }
                    }

                    //点火1
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_engine_status", "").equals("01")) {
                        iv_fire_one.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_fire_one.setImageResource(R.drawable.oval_gray);
                    }

                    //雨刷
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_wiper", "").equals("87")) {
                        iv_wiper.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_wiper.setImageResource(R.drawable.oval_gray);
                    }

                    //点火2
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_engine_status", "").equals("02")) {
                        iv_fire_two.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_fire_two.setImageResource(R.drawable.oval_gray);
                    }

                    //喇叭
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_speaker", "").equals("C1")) {
                        iv_speaker.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_speaker.setImageResource(R.drawable.oval_gray);
                    }

                    //制动器
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_brake_status", "").equals("01")) {
                        iv_brake_status.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_brake_status.setImageResource(R.drawable.oval_gray);
                    }

                    //车门
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_door", "").equals("FB")) {
                        iv_door.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_door.setImageResource(R.drawable.oval_gray);
                    }

                    //安全带
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_belt_info", "").equals("40")) {
                        iv_belt_info.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_belt_info.setImageResource(R.drawable.oval_gray);
                    }

                    //离合
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_clutch", "").equals("01")) {
                        iv_clutch.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_clutch.setImageResource(R.drawable.oval_gray);
                    }

                    //报警灯
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_warning_light", "").equals("01")) {
                        iv_warning_light.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_warning_light.setImageResource(R.drawable.oval_gray);
                    }

                    //手刹
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_hand_brake", "").equals("01")) {
                        iv_hand_brake.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_hand_brake.setImageResource(R.drawable.oval_gray);
                    }

                    //视宽灯
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_position_light", "").equals("01")) {
                        iv_position_light.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_position_light.setImageResource(R.drawable.oval_gray);
                    }

                    //副刹车
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_assistant_brake_status", "").equals("01")) {
                        iv_assistant_brake_status.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_assistant_brake_status.setImageResource(R.drawable.oval_gray);
                    }

                    //左转灯
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_left_turn_light", "").equals("01")) {
                        iv_left_turn_light.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_left_turn_light.setImageResource(R.drawable.oval_gray);
                    }

                    //右转灯
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_right_turn_light", "").equals("01")) {
                        iv_right_turn_light.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_right_turn_light.setImageResource(R.drawable.oval_gray);
                    }

                    //中央后视镜
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_central_rearview_mirror", "").equals("01")) {
                        iv_central_rearview_mirror.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_central_rearview_mirror.setImageResource(R.drawable.oval_gray);
                    }

                    //近光灯
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_dipped_headlight", "").equals("01")) {
                        iv_dipped_headlight.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_dipped_headlight.setImageResource(R.drawable.oval_gray);
                    }

                    //座椅调整
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_seat_adjustment", "").equals("01")) {
                        iv_seat_adjustment.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_seat_adjustment.setImageResource(R.drawable.oval_gray);
                    }

                    //远光灯
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_high_beam_light", "").equals("01")) {
                        iv_high_beam_light.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_high_beam_light.setImageResource(R.drawable.oval_gray);
                    }

                    //左后视镜
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_left_rearview_mirror", "").equals("01")) {
                        waihoushijing.setImageResource(R.drawable.oval_green);
                    } else {
                        waihoushijing.setImageResource(R.drawable.oval_gray);
                    }

                    //雾灯
                    if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_foglight", "").equals("01")) {
                        iv_foglight.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_foglight.setImageResource(R.drawable.oval_gray);
                    }

                    //超声波距离信息1
                    tv_ultrasonic_one.setText(SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_ultrasonic_1", ""));

                    //超声波距离信息2
                    tv_ultrasonic_two.setText(SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_ultrasonic_2", ""));

//                    if (startExamC == 1) {
                    parsetoXWCJ.getXWCJ();
//                    }

                    if (!SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_engine_status", "").equals("")) {
//                        Log.e("TAG",SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_engine_status", ""));
                        //判断是否在点火前
                        if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_engine_status", "").equals("02")) {

//                            projectAdapter.setSelectedPosition(0);
//                            projectAdapter.notifyDataSetChanged();
//                            lv_exam_pro_info.setSelection(0);

                            //判断是否在熄火状态下调整左后视镜
                            if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_left_rearview_mirror", "").equals("01")) {
                                leftTag = 1;
                            }
                            //判断是否在熄火状态下调整座椅
                            if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_seat_adjustment", "").equals("01")) {
                                seatTag = 1;
                            }
                            //判断是否在熄火状态下调整中央后视镜
                            if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_central_rearview_mirror", "").equals("01")) {
                                centralTag = 1;
                            }
                            //判断是否进行了考试前准备
                            if (SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_door", "").equals("FB") &&
                                    SharedPrefsUtils.getValue(ExamProcessActivity.this, "bf_belt_info", "").equals("40") &&
                                    seatTag == 1 && centralTag == 1 && leftTag == 1) {
                                startExamC = 1;
                                parsetoXWCJ.getXWCJ();
                            } else {
                                index++;
                                if (index == 1) {
                                    if (!activityType.equals("trainTest")) {
                                        fraction = 100;
                                        list.add(new MarkingBean("上车准备", "100", "准备不充分!", ""));
                                        markingAdapter.notifyDataSetChanged();
                                        tv_exam_stu_passNum.setText(String.valueOf(list.size()));
                                        lv_exam_mark_info.setSelection(markingAdapter.getCount());
                                        showDialogForPass();
                                        exitThread();
                                    }
                                }
                            }
                        } else {
//                            index++;
//                            if (index == 1) {
//                                if (!activityType.equals("trainTest")) {
//                                    fraction = 100;
//                                    list.add(new MarkingBean("上车准备", "100", "准备不充分!"));
//                                    markingAdapter.notifyDataSetChanged();
//                                    tv_exam_stu_passNum.setText(String.valueOf(list.size()));
//                                    lv_exam_mark_info.setSelection(markingAdapter.getCount());
//                                    showDialogForPass();
//                                    exitThread();
//                                }
//                            }
                        }
                    } else {
                        Log.e(TAG, "点火状态为空!");
                    }
                    break;
            }
        }
    };

    /**
     * 线程的销毁
     */
    private void exitThread() {
        timeFlag = false;
        serialPortThreadFlag = false;
        if (timeThread != null) {
            timeThread = null;
        }
        try {
            stopSerialPort();
        } catch (SecurityException s) {
            Log.e(TAG, s.toString());
        }
        EventBus.getDefault().unregister(this);
        TcpPresenter.getInstance().stopReciveData();
    }

    /**
     * activity返回监听事件
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0:
                if (data != null && data.getSerializableExtra("markingBean") != null) {
                    markingBean = (MarkingBean) data.getSerializableExtra("markingBean");
                    markingSubjrct = markingBean.getMarkProject();
                    getStartExamination();
                    list.add(markingBean);
                    tv_exam_stu_passNum.setText(String.valueOf(list.size()));
                    markingAdapter.notifyDataSetChanged();
                    lv_exam_mark_info.setSelection(markingAdapter.getCount());
                    if (judgmentFraction()) {
                        switch (activityType) {
                            case "stuApprove"://考试
                                showDialogForPass();
                                break;
                            case "training"://考核
                                setUserInfo();
                                intent = new Intent(ExamProcessActivity.this, AssessActivity.class);
                                intent.putExtra("userInfo", userInfo);
                                intent.putExtra("user", user);
                                startActivity(intent);
                                ExamProcessActivity.this.finish();
                                exitThread();
                                break;
                        }
                    }
                }
                break;
        }
    }

    /**
     * 上传随机图片
     */
//    private void sendImgUrl() {
//        paramMap = new HashMap<String, String>();
//        paramMap.put("ksxm", subject);
//        paramMap.put("sfzmhm", user.getUser_id_card());
//        paramMap.put("cs", "123");
//        paramMap.put("car_num", getCarNum());
//        paramMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
//
//        Map<String, File> fileMap = new HashMap<String, File>();
//        fileMap.put("zp", new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/assess_fail.png"));
//        NetworkImgAsyncTask networkImgAsyncTask = new NetworkImgAsyncTask("http://examination.91vh.com/api/examination_img",
//                SharedPrefsUtils.getValue(ExamProcessActivity.this, "token", ""), paramMap, fileMap, handler, 102);
//        networkImgAsyncTask.execute();
//    }

    /**
     * 上传整个考试结束
     */
    private void sendOverImgUrl() {
        paramMap = new HashMap<String, String>();
        paramMap.put("sfzmhm", user.getUser_id_card());
        paramMap.put("car_num", getCarNum());
        paramMap.put("timestamp", String.valueOf(System.currentTimeMillis()));

        Map<String, File> fileMap = new HashMap<String, File>();
        fileMap.put("zp", new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures/assess_fail.png"));
        NetworkImgAsyncTask networkImgAsyncTask = new NetworkImgAsyncTask("http://examination.91vh.com/api/exams_begin",
                SharedPrefsUtils.getValue(ExamProcessActivity.this, "token", ""), paramMap, fileMap, handler, 103);
        networkImgAsyncTask.execute();
    }

    /**
     * 上传项目
     */
    private void getStartExamination() {
        String param = "car_num=" + getCarNum() + "&sfzmhm=" + user.getUser_id_card() + "&timestamp=" + String.valueOf(System.currentTimeMillis())
                + "&ksxm=" + subject + "&kfxm=" + markingSubjrct + "&sbxh=" + getDeviceID();
        networkAsyncTask = new NetworkAsyncTask(url_subject,
                "POST",
                SharedPrefsUtils.getValue(ExamProcessActivity.this, "token", ""),
                param,
                handler,
                3);
        networkAsyncTask.execute();
    }

    /**
     * 上传单一完成的项目
     */
    private void postExamPro() {
        String param = "car_num=" + getCarNum() + "&sfzmhm=" + user.getUser_id_card() + "&timestamp=" + String.valueOf(System.currentTimeMillis())
                + "&ksxm=" + subject + "&sbxh=" + getDeviceID();
        networkAsyncTask = new NetworkAsyncTask(url_examPro,
                "POST",
                SharedPrefsUtils.getValue(ExamProcessActivity.this, "token", ""),
                param,
                handler,
                4);
        networkAsyncTask.execute();
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Log.e(TAG, "返回数据为空!");
                    break;
//                case 102:
//                    gson = new Gson();
//                    if (gson.fromJson(msg.obj.toString(), InitializationRoot.class).getResult().equals("succeed")) {
//                        Log.d("TAG", "图片上传成功!");
//                    } else {
//                        Log.e("TAG", "图片上传失败!");
//                    }
//                    break;
                case 103:
                    gson = new Gson();
                    if (gson.fromJson(msg.obj.toString(), InitializationRoot.class).getResult().equals("succeed")) {
                        Log.d("TAG", "信息上传成功!");
                    } else {
                        Log.e("TAG", "信息上传失败!");
                    }
                    break;
                case 3:
                    gson = new Gson();
                    if (gson.fromJson(msg.obj.toString(), InitializationRoot.class).getResult().equals("succeed")) {
                        Log.d("TAG Result", "项目上传成功");
                    }
                    break;
                case 4:
                    gson = new Gson();
                    if (gson.fromJson(msg.obj.toString(), InitializationRoot.class).getResult().equals("succeed")) {
                        Log.d("TAG Result", "单一项目上传成功");
                    } else {
                        Log.d("TAG Result", "单一项目上传失败");
                    }
                    break;
            }
            return false;
        }
    });

    /**
     * 读取保存的车牌号
     *
     * @return
     */
    private String getCarNum() {
        try {
            return FilesUtil.readFiles(ExamProcessActivity.this, "carCode");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取保存的车牌id
     *
     * @return
     */
    private String getDeviceID() {
        try {
            return FilesUtil.readFiles(ExamProcessActivity.this, "deviceId");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算扣分情况
     *
     * @return
     */
    private boolean judgmentFraction() {
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                fraction += Integer.parseInt(list.get(i).getMarkFraction());
            }
        }
        if (fraction < 20) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 停止串口
     */
    private void stopSerialPort() {
        SerialPortSendUtil serialPortSendUtil = new SerialPortSendUtil();
        serialPortSendUtil.sendMessage(new byte[]{0x55, (byte) 0xAA, 0x01, 0x00, 0x00});
        stopService(SerialPortService.sIntent);
    }

    /**
     * 打开地图显示
     */
    private void intMap() {
        getFilePath();

        my_view = new DrawView(ExamProcessActivity.this);
        my_view.invalidate();
        my_layout.addView(my_view);

        my_view.Load_TuData(TuFile_Path);
        my_view.Load_CheData(CheFile_Path);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                my_view.SetZoomSize(0.1f);
                my_view.invalidate();
            }
        }, 300);
    }


    /**
     * 查找sd卡中地图的tu.tu和che.che文件
     */
    private void getFilePath() {
        File sd = Environment.getExternalStorageDirectory();
        String strPath = sd.getPath();
        TuFile_Path = strPath + "/tu.tu";
        File file = new File(TuFile_Path);
        if (file.exists() == false) {
            Log.e(TAG, "tu文件不存在");
        }
        CheFile_Path = strPath + "/che.che";
        File file1 = new File(CheFile_Path);
        if (file1.exists() == false) {
            Log.e(TAG, "che文件不存在");
        }
    }

    /**
     * 改变地图大小
     *
     * @param number
     */
    private void changeSize(int number) {

        switch (number) {
            case 1:
                my_view.SetZoomSize(0.1f);
                my_view.invalidate();
                break;
            case 2:
                my_view.SetZoomSize(0.2f);
                my_view.invalidate();
                break;
            case 3:
                my_view.SetZoomSize(0.5f);
                my_view.invalidate();
                break;
            case 4:
                my_view.SetZoomSize(1);
                my_view.invalidate();
                break;
            case 5:
                my_view.SetZoomSize(2);
                my_view.invalidate();
                break;
            case 6:
                my_view.SetZoomSize(5);
                my_view.invalidate();
                break;
        }
    }

    private void initTcp() {
        TcpPresenter.getInstance().startReciveData();
        sendMessageToCar();
    }

//    /**
//     * TcpMessage
//     *
//     * @param obj
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainThread(TcpMessage obj) {
//        Message message = new Message();
//        message.what = 0;
//        message.obj = obj.strMsg.toString();
//        handelr.sendMessage(message);
//    }
//
//    /**
//     * TcpMessage
//     *
//     * @param obj
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainThread(tcpConnectState obj) {
//        Message message = new Message();
//        message.what = 0;
//        message.obj = obj.strMsg.toString();
//        handelr.sendMessage(message);
//    }

    /**
     * TcpMessage
     *
     * @param obj
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventBackgroundThread(TcpMessage obj) {
        Message message = new Message();
        message.what = 0;
        message.obj = obj.strMsg.toString();
        handelr.sendMessage(message);
    }

    /**
     * TcpMessage
     *
     * @param obj
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventBackgroundThread(tcpConnectState obj) {
        Message message = new Message();
        message.what = 0;
        message.obj = obj.strMsg.toString();
        handelr.sendMessage(message);
    }

    /**
     * 接收练一练信号信息
     */
    Handler handelr = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String result = msg.obj.toString();
            xwtcp(result);
            res(result);
            xwcj(result);
            getXWYD2(result);
            getEXAM(result);
            getGPHPD(result);
            return false;
        }
    });

    private void res(String strMsg) {
        if (!TextUtils.isEmpty(strMsg) && strMsg.length() > 7) {
            if (strMsg.indexOf("$xwtcp,set,exam_startwork,ok*ff") > -1) {
                String index = strMsg.substring(strMsg.indexOf("$xwtcp", 0), strMsg.indexOf("$xwtcp") + 20);
//                tv1.setText(index);
            }
        }
    }

    private void xwtcp(String strMsg) {
        if (!TextUtils.isEmpty(strMsg) && strMsg.length() > 7) {
            if (strMsg.indexOf("$xwtcp,set,ok*ff") > -1) {
                String index = strMsg.substring(strMsg.indexOf("$xwtcp"), strMsg.indexOf("$xwtcp") + 16);
                if (index.equals("$xwtcp,set,ok*ff")) {
                    tp.sendMsgToTcp("$xwtcp,set,exam_startwork*ff");
                }
            }
        }
    }

    /**
     * 发送开始信号给练一练
     */
    private void sendMessageToCar() {
//        boolean bo = tp.sendMsgToTcp("$xwtcp,set,exam_mode_training*ff");
        tp.sendMsgToTcp("$xwtcp,set,exam_startwork*ff");
    }

    private void xwcj(String strMsg) {
        if (!TextUtils.isEmpty(strMsg) && strMsg.length() > 7) {
            if (strMsg.indexOf("$XWCJ") > -1) {
                tv1.setText(strMsg);
                Log.e(TAG, strMsg);
            }
        }
    }

    /**
     * 获取考试中扣分信号
     *
     * @param strMsg
     */
    private void getEXAM(String strMsg) {
        if (!TextUtils.isEmpty(strMsg) && strMsg.length() > 7) {
            if (strMsg.indexOf("$EXAM") > -1) {
                try {
                    String str = strMsg.substring(strMsg.indexOf("$EXAM"));
                    String index1 = str.substring(str.indexOf("$EXAM"), str.indexOf("*FF"));
                    String index2 = str.substring(str.lastIndexOf("$EXAM"), str.lastIndexOf("*FF"));
                    String[] s = index1.split(",");
//                    tv1.setText(index1 + "");
                    String exam = s[1];
                    if (!exam.equals("bencixunliankaishi")) {
                        playMedia(exam);
                    }
                    markingBean = examDataUtil.getExamData(exam);
                    if (markingBean != null) {
                        initMarkData(markingBean);
                    } else {
                        Log.e(TAG, "扣分对象为空!");
                    }
                } catch (StringIndexOutOfBoundsException s) {
                    Log.e(TAG, s.toString());
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }

            }
        }
    }

    /**
     * 获取GPS点
     *
     * @param strMsg
     */
    private void getGPHPD(String strMsg) {
        if (!TextUtils.isEmpty(strMsg) && strMsg.length() > 7) {
            String str = strMsg.substring(0, 6);
            if (str.equals("$GPHPD")) {
                String[] s = strMsg.split(",");
                String latitude = s[6];
                String longitude = s[7];
            }
        }
    }


    float hfloat, pfloat, rfloat, xfloat, yfloat, zfloat;
    String hString, pString, rString, xString, yString, zString;

    /**
     * 获取地图信息
     *
     * @param strMsg
     */
    private void getXWYD2(String strMsg) {
        if (!TextUtils.isEmpty(strMsg) && strMsg.length() > 7) {
            String str = strMsg.substring(0, 6);
            if (str.equals("$XWYD2")) {
                String[] s = strMsg.split(",");
                hString = s[4];
                pString = s[5];
                rString = s[6];
                xString = s[7];
                yString = s[8];
                zString = s[9];
                if (!TextUtils.isEmpty(hString) && !TextUtils.isEmpty(pString) && !TextUtils.isEmpty(rString) && !TextUtils.isEmpty(xString) && !TextUtils.isEmpty(yString) && !TextUtils.isEmpty(zString)) {
                    hfloat = Float.parseFloat(hString);
                    pfloat = Float.parseFloat(pString);
                    rfloat = Float.parseFloat(rString);
                    xfloat = Float.parseFloat(xString);
                    yfloat = Float.parseFloat(yString);
                    zfloat = Float.parseFloat(zString);
                    updateMap();
                }
            }
        }
    }

    /**
     * 根据获取的数据刷新地图
     */
    private void updateMap() {
        my_view.InputData(hfloat, pfloat, rfloat, xfloat, yfloat, zfloat);
        my_view.invalidate();
    }

    /**
     * 处理扣分数据,进行判断显示
     *
     * @param markingBean
     */
    private void initMarkData(MarkingBean markingBean) {
        //如果是开始信号,使项目列表更新
        if (!markingBean.getMarkProject().equals("") && markingBean.getMarkRes().equals("开始") && markingBean.getMarkFraction().equals("0")) {
            playMedia(markingBean.getMedia());
            for (int i = 0; i < projectAdapter.getCount(); i++) {
                if (projectAdapter.getItem(i).equals(markingBean.getMarkProject())) {
                    goState = 1;
                    projectAdapter.setSelectedPosition(i);
                    projectAdapter.notifyDataSetChanged();
                    //设置正在进行项在最上面显示
                    lv_exam_pro_info.setSelection(i);
                    proStr = markingBean.getMarkProject();
                }
            }
        }
        //通用扣分规则,先添加对应项目名称,更新扣分列表
        if (markingBean.getMarkProject().equals("") && !markingBean.getMarkFraction().equals("") && !markingBean.getMarkRes().equals("")) {
            if (proStr != null) {
                playMedia(markingBean.getMedia());
                markingBean.setMarkProject(proStr);
                list.add(markingBean);
                markingAdapter.notifyDataSetChanged();
                tv_exam_stu_passNum.setText(String.valueOf(list.size()));
                lv_exam_mark_info.setSelection(markingAdapter.getCount());
                judgmentFraction();
            }
        }//如果返回的对象全都有值,则直接显示到列表中
        else if (!markingBean.getMarkProject().equals("") && !markingBean.getMarkFraction().equals("") && !markingBean.getMarkRes().equals("") && !markingBean.getMarkRes().equals("开始")) {
            playMedia(markingBean.getMedia());
            list.add(markingBean);
            markingAdapter.notifyDataSetChanged();
            tv_exam_stu_passNum.setText(String.valueOf(list.size()));
            lv_exam_mark_info.setSelection(markingAdapter.getCount());
            judgmentFraction();
        }
    }

    public void playMedia(String name) {
        try {
            AssetFileDescriptor fileDescriptor = getResources().getAssets().openFd(name + ".wav");
            mp.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mp.prepare();
            mp.start();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                }
            });
        } catch (IOException io) {
            Log.e("Error", io.toString());
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }
    }
}
