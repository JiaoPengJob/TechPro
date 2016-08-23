package cn.hongjitech.vehicle.activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zkteco.android.biometric.core.device.ParameterHelper;
import com.zkteco.android.biometric.core.device.TransportType;
import com.zkteco.android.biometric.core.utils.LogHelper;
import com.zkteco.android.biometric.core.utils.ToolUtils;
import com.zkteco.android.biometric.module.fingerprintreader.FingerprintCaptureListener;
import com.zkteco.android.biometric.module.fingerprintreader.FingerprintSensor;
import com.zkteco.android.biometric.module.fingerprintreader.FingprintFactory;
import com.zkteco.android.biometric.module.fingerprintreader.ZKFingerService;
import com.zkteco.android.biometric.module.fingerprintreader.exception.FingerprintException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.adapter.ReservationStatusAdapter;
import cn.hongjitech.vehicle.bean.ReservationStatus;
import cn.hongjitech.vehicle.javaBean.AdminRoot;
import cn.hongjitech.vehicle.javaBean.ReservationInfo;
import cn.hongjitech.vehicle.javaBean.ReservationInfoRoot;
import cn.hongjitech.vehicle.javaBean.SignInResultRoot;
import cn.hongjitech.vehicle.javaBean.User;
import cn.hongjitech.vehicle.javaBean.UserRoot;
import cn.hongjitech.vehicle.service.ZKCardReaderService;
import cn.hongjitech.vehicle.util.FilesUtil;
import cn.hongjitech.vehicle.util.SharedPrefsUtils;
import cn.hongjitech.vehicle.util.StringUtils;
import cn.hongjitech.vehicle.volleyHttp.VolleyManager;

/**
 * 训练学员选择
 */
public class TrainingActivity extends BaseActivity {

    @InjectView(R.id.lv_tran_info)
    PullToRefreshListView lv_tran_info;//学员信息列表信息
    @InjectView(R.id.iv_tran_assess)
    ImageView iv_tran_assess;//考核
    @InjectView(R.id.iv_tran_test)
    ImageView iv_tran_test;//开始训练
    @InjectView(R.id.iv_tran_oil)
    ImageView iv_tran_oil;//申请加油
    @InjectView(R.id.iv_tran_back)
    ImageView iv_tran_back;//返回
    @InjectView(R.id.tv_tran_current_time)
    TextView tv_tran_current_time;//显示当前时间
    @InjectView(R.id.tv_train_student_name)
    TextView tv_train_student_name;//学员姓名
    @InjectView(R.id.tv_train_teacher_name)
    TextView tv_train_teacher_name;//预约教练
    @InjectView(R.id.tv_train_reservation_time)
    TextView tv_train_reservation_time;//预约时间
    @InjectView(R.id.tv_train_code)
    TextView tv_train_code;//证件号码
    @InjectView(R.id.iv_train_stu_photo_left)
    ImageView iv_train_stu_photo_left;//服务器头像
    @InjectView(R.id.iv_train_rcp_idCard)
    ImageView iv_train_rcp_idCard;//身份证验证标识
    @InjectView(R.id.iv_train_rcp_fingerprint)
    ImageView iv_train_rcp_fingerprint;//指纹验证标识

    /*----------------------------------*/
    private Intent intent;//跳转Intent对象
    private boolean timeFlag = true;//判断时间线程是否继续进行
    private TimeThread timeThread;//时间的线程
    private List<ReservationStatus> reservationStatus;//显示预约信息
    private List<Integer> user_ids;//纪录用户id
    private String appointmentsUrl = "http://backend.dev.hongjitech.cn/api/android/appointments?vehicle_id=";//访问预约信息的URL
    private ReservationStatusAdapter reservationStatusAdapter;//预约信息的适配器
    private String userUrl = "http://backend.dev.hongjitech.cn/api/android/user";//获取用户信息URL
    private ReservationInfo reservationInfo;//预约信息对象
    private User user;//用户对象
    private String adminUrl = "http://backend.dev.hongjitech.cn/api/android/admin/";//获取教练的URL
    private ZKCardReaderService zkCardReaderService;//身份证采集仪service
    private Timer timer;//身份证采集仪时间计时器
    private TimerTask timerTask;//身份证采集仪task
    private AlertDialog alertDialog;//dialog对象,提示是否在身份验证未全部通过情况下继续进行
    private Button bt_dialog_ifexam_exit;//dialog的取消
    private Button bt_dialog_ifexam_sure;//dialog的确定
    private String activityDis;//判断点击的是考核还是测试
    private int appointment_id;//预约信息ID
    private String carUrl = "backend";
    /*---------指纹仪参数对象----------------*/
    private FingerprintSensor fingerprintSensor = null;
    private static final int SVID = 6997;
    private static final int SPID = 288;
    private boolean bstart = false;
    private boolean isRegister = false;
    private int uid = 1;
    private byte[][] regtemparray = new byte[3][2048];  //register template buffer array
    private int enrollidx = 0;
    private byte[] lastRegTemp = new byte[2048];
    private Bitmap bitmapFinger;//获取到系统保存的指纹图像
    private String endTime;//预约结束的时间
    private String signInResult;//签到返回的结果
    /*-----------------------------------------------*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_training);
        ButterKnife.inject(TrainingActivity.this);
        initListener();
        timeThread = new TimeThread();
        timeThread.start();
        user_ids = new ArrayList<Integer>();
        if (StringUtils.isNetworkConnected(TrainingActivity.this)) {
            if (getCarId() != null) {
                refreshData();
                try{
                    getConnectForReservation();
                }catch (Exception e){
                    Log.e("Error",e.toString());
                }
                startService();
                startFingerprintSensor();
            } else {
                Log.e("TrainingActivity", "车辆id为空");
            }
        } else {
            Toast.makeText(TrainingActivity.this, "当前网络不佳!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 刷新list数据
     */
    private void refreshData() {
        lv_tran_info.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                new AsyncTask<Void, Void, List<ReservationInfo>>() {
                    @Override
                    protected List<ReservationInfo> doInBackground(Void... params) {
                        // 处理刷新任务
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }

                    @Override
                    protected void onPostExecute(List<ReservationInfo> reslst) {
                        if (getCarId() != null) {
                            try{
                                getConnectForReservation();
                            }catch (Exception e){
                                Log.e("Error",e.toString());
                            }

                        }
                        if (reservationStatusAdapter != null) {
                            reservationStatusAdapter.notifyDataSetChanged();
                            // 更行内容，通知 PullToRefresh 刷新结束
                            lv_tran_info.onRefreshComplete();
                        }
                    }
                }.execute();
            }
        });
    }

    /**
     * 加载点击事件监听函数
     */
    private void initListener() {
        iv_tran_assess.setOnClickListener(new ClickListener());
        iv_tran_test.setOnClickListener(new ClickListener());
        iv_tran_oil.setOnClickListener(new ClickListener());
        iv_tran_back.setOnClickListener(new ClickListener());
        //设置只下拉刷新
        lv_tran_info.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        //设置listview的item的点击事件
        lv_tran_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //设置选中的item
                reservationStatusAdapter.setSelectedPosition(position - 1);
                reservationStatusAdapter.notifyDataSetChanged();
                if (reservationStatusAdapter.getItem(position - 1).getAppointment() == null || !reservationStatusAdapter.getItem(position - 1).getAppointment().getStatus().equals("TAKEN")) {
                    Toast.makeText(TrainingActivity.this, "此段时间无人预约", Toast.LENGTH_SHORT).show();
                    tv_train_student_name.setText("");
                    tv_train_reservation_time.setText("");
                    tv_train_code.setText("");
                    tv_train_teacher_name.setText("");
                    iv_train_stu_photo_left.setImageResource(R.drawable.empty_user_photo);
                    user = null;
                } else {
//                    if (reservationStatusAdapter.getItem(position - 1).getAppointment().getStatus().equals("TAKEN")) {
                    appointment_id = reservationStatusAdapter.getItem(position - 1).getAppointment().getId();
                    SharedPrefsUtils.putValue(TrainingActivity.this, "appointment_id", String.valueOf(appointment_id));
                    String paramUserUrl = userUrl + "/" + reservationStatusAdapter.getItem(position - 1).getAppointment().getUser_id();
                    getUserInfo(paramUserUrl, reservationStatusAdapter.getItem(position - 1));
                    getAdminInfo(adminUrl + reservationStatusAdapter.getItem(position - 1).getAppointment().getAdmin_id());
                    endTime = reservationStatusAdapter.getItem(position - 1).getFinish_time();
                    /**
                     *  判断选中的学员是否应在当前时间开始进行学习
                     */
//                    if (StringUtils.betweenDate(StringUtils.str2Date(reservationStatusAdapter.getItem(position - 1).getStart_time(), "HH:mm:ss"),
//                            StringUtils.str2Date(reservationStatusAdapter.getItem(position - 1).getStart_time(), "HH:mm:ss"),
//                            StringUtils.getCurrentDate("HH:mm:ss"))) {
//                        iv_tran_assess.setClickable(true);
//                        iv_tran_test.setClickable(true);
//                    } else {
//                        Toast.makeText(TrainingActivity.this, "此学员预约时间不匹配,无法进行下一步", Toast.LENGTH_SHORT).show();
//                        iv_tran_assess.setClickable(false);
//                        iv_tran_test.setClickable(false);
//                    }
//                    }
                }
            }
        });
    }

    /**
     * 声明点击事件监听类
     */
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_tran_assess://考核
                    activityDis = "training";
                    ifAssess();
                    break;
                case R.id.iv_tran_test://开始训练
                    activityDis = "trainTest";
                    ifAssess();
                    break;
                case R.id.iv_tran_oil://申请加油

                    break;
                case R.id.iv_tran_back://返回
                    intent = new Intent(TrainingActivity.this, MainActivity.class);
                    startActivity(intent);
                    exitThread();
                    TrainingActivity.this.finish();
                    break;
                case R.id.bt_dialog_ifexam_exit://dialog取消
                    alertDialog.cancel();
                    break;
                case R.id.bt_dialog_ifexam_sure://dialog确定
                    alertDialog.cancel();
                    intentActivity();
                    break;
            }
        }
    }

    /**
     * 页面跳转
     */
    private void intentActivity() {
        if (getCarId() != null) {
            String signInUrl = "http://backend.dev.hongjitech.cn/api/android/signIn?appointment_id=" + appointment_id + "&vehicle_id=" + getCarId();//学员签到的URL
            signIn(signInUrl);
            exitThread();
            intent = new Intent(TrainingActivity.this, ExamProcessActivity.class);
            intent.putExtra("activity", activityDis);
            intent.putExtra("user", user);
            intent.putExtra("endTime", endTime);
            startActivity(intent);
            TrainingActivity.this.finish();
        } else {
            Log.e("TrainingActivity", "车辆id为空!");
        }
    }

    /**
     * 是否进行考核
     */
    private void ifAssess() {
        if (user != null) {
            if (iv_train_rcp_fingerprint.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.green_cirle).getConstantState()) &&
                    iv_train_rcp_idCard.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.green_cirle).getConstantState()) &&
                    signInResult.equals("success")) {
                Log.d("TAG", "验证全部通过");
                intentActivity();
            } else {
                showDialogForExam();
            }
        } else {
            Toast.makeText(TrainingActivity.this, "未找到预约学员!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 设置结束显示的窗口提醒
     */
    private void showDialogForExam() {
        View view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_ifexam, null);
        alertDialog = new AlertDialog.Builder(this).setTitle(null).setMessage(null).setView(view).create();
        bt_dialog_ifexam_exit = (Button) view.findViewById(R.id.bt_dialog_ifexam_exit);
        bt_dialog_ifexam_sure = (Button) view.findViewById(R.id.bt_dialog_ifexam_sure);
        bt_dialog_ifexam_exit.setOnClickListener(new ClickListener());
        bt_dialog_ifexam_sure.setOnClickListener(new ClickListener());
        alertDialog.show();
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
                    Message msg = new Message();
                    msg.what = 0;
                    mHandler.sendMessage(msg);
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
                case 0:
                    tv_tran_current_time.setText(StringUtils.getCurrentTime("HH:mm:ss"));
                    break;
            }
        }
    };

    /**
     * 获取服务器系统图片
     */
    private void getImg(String url,ImageView iv) {
        VolleyManager.newInstance().ImageLoaderRequest
                (iv, url, R.drawable.empty_user_photo, R.drawable.empty_user_photo, 125, 250);
    }

    /**
     * 访问服务器获取预约信息
     */
    private void getConnectForReservation() {
        VolleyManager.newInstance().GsonGetRequest("TAG", appointmentsUrl + getCarId(), ReservationInfoRoot.class,
                new Response.Listener<ReservationInfoRoot>() {
                    @Override
                    public void onResponse(ReservationInfoRoot reservationInfoRoot) {
                        if (reservationInfoRoot.getData() != null) {
                            reservationStatusAdapter = new ReservationStatusAdapter(TrainingActivity.this, reservationInfoRoot.getData());
                            if(reservationStatusAdapter != null){
                                lv_tran_info.setAdapter(reservationStatusAdapter);
                            }
                        } else {
                            Toast.makeText(TrainingActivity.this, "请重新加载!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        Toast.makeText(TrainingActivity.this, "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 访问服务器获取用户信息
     */
    private void getUserInfo(String url, final ReservationInfo reservationInfo) {
        VolleyManager.newInstance().GsonGetRequest("TAG", url, UserRoot.class,
                new Response.Listener<UserRoot>() {
                    @Override
                    public void onResponse(UserRoot userRoot) {
                        if (userRoot != null) {
                            user = userRoot.getData();
                            tv_train_student_name.setText(user.getUser_truename());
                            tv_train_reservation_time.setText(reservationInfo.getStart_time() + " - " + reservationInfo.getFinish_time());
                            tv_train_code.setText(user.getUser_id_card());
                            getImg(user.getUser_img(),iv_train_stu_photo_left);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                        Toast.makeText(TrainingActivity.this, "服务器繁忙,请稍后再试!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 访问服务器获取教练信息
     */
    private void getAdminInfo(String url) {
        VolleyManager.newInstance().GsonGetRequest("TAG", url, AdminRoot.class,
                new Response.Listener<AdminRoot>() {
                    @Override
                    public void onResponse(AdminRoot adminRoot) {
                        if (adminRoot != null) {
                            tv_train_teacher_name.setText(adminRoot.getData().getAdmin_name());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                });
    }

    /**
     * 访问服务器签到
     */
    private void signIn(String url) {
        VolleyManager.newInstance().GsonGetRequest("TAG", url, SignInResultRoot.class,
                new Response.Listener<SignInResultRoot>() {
                    @Override
                    public void onResponse(SignInResultRoot signInResultRoot) {
                        if (signInResultRoot != null) {
                            signInResult = signInResultRoot.getMessage();
                            Log.d("signInResult", signInResult);
                        } else {
                            signInResult = "";
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                });
    }

    /**
     * 读取保存的车牌id
     *
     * @return
     */
    private String getCarId() {
        try {
            return FilesUtil.readFiles(TrainingActivity.this, "carId");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 清空线程
     */
    private void exitThread() {
        timeFlag = false;
        if (timer != null) {
            timer.cancel();
        }
        if (handler != null) {
            handler.removeMessages(0);
        }
        if (timeThread != null) {
            timeThread = null;
        }
        stopService();
        try {
            OnBnStop();
        } catch (FingerprintException e) {
            Log.e("Error:", e.toString());
        }
    }

    /**
     * 开始身份证采集仪线程
     */
    private void startService() {
        intent = new Intent(TrainingActivity.this, ZKCardReaderService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    /**
     * 结束身份证采集仪线程
     */
    private void stopService() {
        if (serviceConnection != null) {
            try {
                unbindService(serviceConnection);
            }catch (IllegalArgumentException i){
                Log.e("Training",i.toString());
            }
        }
    }

    /**
     * 绑定身份证采集仪服务
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            // TODO Auto-generated method stub
            zkCardReaderService = null;
        }

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            //当建立连接时调用
            zkCardReaderService = ((ZKCardReaderService.ZKCardReaderBinder) service).getService();
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if (handler != null) {
                        handler.sendEmptyMessage(0);
                    }
                }
            };
            timer.schedule(timerTask, 0, 1000);
        }
    };
    /**
     * 开启线程,处理身份证号
     */
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            //获取到身份证采集仪中的身份证号,进行对比
            if (user != null && zkCardReaderService.getCardIdInfo() != null) {
                Log.d("Card", zkCardReaderService.getCardIdInfo());
                if (user.getUser_id_card().equals(zkCardReaderService.getCardIdInfo())) {
                    iv_train_rcp_idCard.setImageResource(R.drawable.green_cirle);
                } else {
                    iv_train_rcp_idCard.setImageResource(R.drawable.red_cirle);
                }
            } else {

            }
            return false;
        }
    });

    /**
     * 启动指纹仪
     */
    private void startFingerprintSensor() {
        // Start fingerprint sensor
        Map fingerprintParams = new HashMap();
        fingerprintParams.put(ParameterHelper.PARAM_KEY_VID, SVID);
        fingerprintParams.put(ParameterHelper.PARAM_KEY_PID, SPID);
        fingerprintSensor = FingprintFactory.createFingerprintSensor(this, TransportType.USB, fingerprintParams);
        fingerprintSensor.setFakeFunOn(1);
        try {
            OnBnBegin();
        } catch (FingerprintException e) {

        }
    }

    /**
     * 开始指纹仪采集
     *
     * @throws FingerprintException
     */
    public void OnBnBegin() throws FingerprintException {
        try {
            if (bstart) return;
            fingerprintSensor.open(0);
            final FingerprintCaptureListener listener = new FingerprintCaptureListener() {
                @Override
                public void captureOK(final byte[] fpImage) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (null != fpImage) {
                                ToolUtils.outputHexString(fpImage);
                                LogHelper.i("width=" + fingerprintSensor.getImageWidth() + "\nHeight=" + fingerprintSensor.getImageHeight());
                                //获取到指纹
                                Bitmap bitmapFp = ToolUtils.renderCroppedGreyScaleBitmap(fpImage, fingerprintSensor.getImageWidth(), fingerprintSensor.getImageHeight());
                                if (bitmapFp != null && bitmapFinger != null) {
                                    //将采集到的指纹与系统保存的指纹进行比对
                                    int verifyResult = ZKFingerService.verify(StringUtils.bitmapToByte(bitmapFp, Bitmap.CompressFormat.PNG), StringUtils.bitmapToByte(bitmapFinger, Bitmap.CompressFormat.PNG));
                                    if (verifyResult > 23) {
                                        iv_train_rcp_fingerprint.setImageResource(R.drawable.green_cirle);
                                    } else {
                                        iv_train_rcp_fingerprint.setImageResource(R.drawable.red_cirle);
                                    }
                                } else {
                                    Log.e("Error", "指纹图片校验失败!");
                                }
                            }
                        }
                    });
                }

                @Override
                public void captureError(FingerprintException e) {
                    final FingerprintException exp = e;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LogHelper.d("captureError  errno=" + exp.getErrorCode() +
                                    ",Internal error code: " + exp.getInternalErrorCode() + ",message=" + exp.getMessage());
                        }
                    });
                }

                @Override
                public void extractError(final int err) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("Fingerprint Message", "extract fail, errorcode:" + err);
                        }
                    });
                }

                @Override
                public void extractOK(final byte[] fpTemplate) {
                    final byte[] tmpBuffer = fpTemplate;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isRegister) {
                                byte[] bufids = new byte[256];
                                int ret = ZKFingerService.identify(tmpBuffer, bufids, 55, 1);
                                if (ret > 0) {
                                    String strRes[] = new String(bufids).split("\t");
                                    Log.d("Fingerprint Message", "the finger already enroll by " + strRes[0] + ",cancel enroll");
                                    isRegister = false;
                                    enrollidx = 0;
                                    return;
                                }
                                if (enrollidx > 0 && ZKFingerService.verify(regtemparray[enrollidx - 1], tmpBuffer) <= 0) {
                                    Log.d("Fingerprint Message", "please press the same finger 3 times for the enrollment");
                                    return;
                                }
                                System.arraycopy(tmpBuffer, 0, regtemparray[enrollidx], 0, 2048);
                                enrollidx++;
                                if (enrollidx == 3) {
                                    byte[] regTemp = new byte[2048];
                                    if (0 < (ret = ZKFingerService.merge(regtemparray[0], regtemparray[1], regtemparray[2], regTemp))) {
                                        ZKFingerService.save(regTemp, "test" + uid++);
                                        System.arraycopy(regTemp, 0, lastRegTemp, 0, ret);
                                        //Base64 Template
                                        String strBase64 = Base64.encodeToString(regTemp, 0, ret, Base64.NO_WRAP);
                                        Log.d("Fingerprint Message", "enroll succ");
                                    } else {
                                        Log.e("Fingerprint Message", "enroll fail");
                                    }
                                    isRegister = false;
                                } else {
                                    Log.e("Fingerprint Message", "You need to press the " + (3 - enrollidx) + "time fingerprint");
                                }
                            } else {
                                byte[] bufids = new byte[256];
                                int ret = ZKFingerService.identify(tmpBuffer, bufids, 55, 1);
                                if (ret > 0) {
                                    String strRes[] = new String(bufids).split("\t");
                                    Log.d("Fingerprint Message", "identify succ, userid:" + strRes[0] + ", score:" + strRes[1]);
                                } else {
                                    Log.e("Fingerprint Message", "identify fail");
                                }
                            }
                        }
                    });
                }
            };
            fingerprintSensor.setFingerprintCaptureListener(0, listener);
            fingerprintSensor.startCapture(0);
            bstart = true;
            Log.d("Fingerprint Message", "start capture succ");
        } catch (FingerprintException e) {
            Log.e("Fingerprint Message", "begin capture fail.errorcode:" + e.getErrorCode() + "err message:" + e.getMessage() + "inner code:" + e.getInternalErrorCode());
        }
    }

    /**
     * 停止指纹仪采集
     *
     * @throws FingerprintException
     */
    public void OnBnStop() throws FingerprintException {
        try {
            if (bstart) {
                //stop capture
                fingerprintSensor.stopCapture(0);
                bstart = false;
                fingerprintSensor.close(0);
                Log.e("Error:", "stop capture succ");
            } else {
                Log.e("Error:", "already stop");
            }
        } catch (FingerprintException e) {
            Log.e("Error:", "stop fail, errno=" + e.getErrorCode() + "\nmessage=" + e.getMessage());
        }
    }
}
