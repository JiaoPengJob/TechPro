package cn.hongjitech.vehicle.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lkmap.DrawView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.bean.MarkingBean;
import cn.hongjitech.vehicle.map.TcpMessage;
import cn.hongjitech.vehicle.map.TcpPresenter;
import cn.hongjitech.vehicle.map.tcpConnectState;
import cn.hongjitech.vehicle.service.SerialPortService;
import cn.hongjitech.vehicle.util.ExamDataUtil;
import cn.hongjitech.vehicle.util.ParsetoXWCJ;
import cn.hongjitech.vehicle.util.SerialPortSendUtil;
import cn.hongjitech.vehicle.util.SharedPrefsUtils;
import cn.hongjitech.vehicle.util.XGYDSerialPortSendUtil;

/**
 * 测试各项硬件状态
 * 用来打开身份证采集仪权限
 * <p/>
 * type_id :1-3 科目2
 * 4-6 科目3
 */
public class TestDrivesActivity extends BaseActivity {

    @InjectView(R.id.bt_testDrives_dataHandle)
    ImageView bt_testDrives_dataHandle;//数据处理
    @InjectView(R.id.iv_testDrives_gameOver)
    ImageView iv_testDrives_gameOver;//结束
    @InjectView(R.id.iv_test_drives_map)
    ImageView iv_test_drives_map;//地图小按钮
    @InjectView(R.id.iv_test_drives_single)
    ImageView iv_test_drives_single;//地图信号小按钮
    @InjectView(R.id.ll_single_parent)
    LinearLayout ll_single_parent;//信号的布局

    @InjectView(R.id.iv_fire_one)
    ImageView iv_fire_one;

    @InjectView(R.id.iv_wiper)
    ImageView iv_wiper;

    @InjectView(R.id.iv_fire_two)
    ImageView iv_fire_two;

    @InjectView(R.id.iv_speaker)
    ImageView iv_speaker;

    @InjectView(R.id.iv_brake_status)
    ImageView iv_brake_status;

    @InjectView(R.id.iv_door)
    ImageView iv_door;

    @InjectView(R.id.iv_belt_info)
    ImageView iv_belt_info;

    @InjectView(R.id.iv_clutch)
    ImageView iv_clutch;

    @InjectView(R.id.iv_warning_light)
    ImageView iv_warning_light;

    @InjectView(R.id.iv_hand_brake)
    ImageView iv_hand_brake;

    @InjectView(R.id.iv_position_light)
    ImageView iv_position_light;

    @InjectView(R.id.iv_assistant_brake_status)
    ImageView iv_assistant_brake_status;

    @InjectView(R.id.iv_left_turn_light)
    ImageView iv_left_turn_light;

    @InjectView(R.id.iv_right_turn_light)
    ImageView iv_right_turn_light;

    @InjectView(R.id.iv_central_rearview_mirror)
    ImageView iv_central_rearview_mirror;

    @InjectView(R.id.iv_dipped_headlight)
    ImageView iv_dipped_headlight;

    @InjectView(R.id.iv_seat_adjustment)
    ImageView iv_seat_adjustment;

    @InjectView(R.id.iv_high_beam_light)
    ImageView iv_high_beam_light;

    @InjectView(R.id.iv_foglight)
    ImageView iv_foglight;

    @InjectView(R.id.tv_ultrasonic_one)
    TextView tv_ultrasonic_one;

    @InjectView(R.id.tv_ultrasonic_two)
    TextView tv_ultrasonic_two;

    @InjectView(R.id.tv_car_speed)
    TextView tv_car_speed;

    @InjectView(R.id.tv_car_gear)
    TextView tv_car_gear;

    @InjectView(R.id.tv_fire)
    TextView tv_fire;

    @InjectView(R.id.tv_door)
    TextView tv_door;

    @InjectView(R.id.tv_bf_assistant_brake_status)
    TextView tv_bf_assistant_brake_status;

    @InjectView(R.id.tv_bf_central_rearview_mirror)
    TextView tv_bf_central_rearview_mirror;

    @InjectView(R.id.tv_bf_dipped_headlight)
    TextView tv_bf_dipped_headlight;

    @InjectView(R.id.tv_bf_seat_adjustment)
    TextView tv_bf_seat_adjustment;

    @InjectView(R.id.tv_bf_foglight)
    TextView tv_bf_foglight;

    @InjectView(R.id.waihoushijing)
    ImageView waihoushijing;

    @InjectView(R.id.max)
    ImageView max;//地图的放大
    @InjectView(R.id.min)
    ImageView min;//地图的缩小
    @InjectView(R.id.rpm)
    TextView rpm;

    @InjectView(R.id.tvPro)
    TextView tvPro;

    @InjectView(R.id.tvFuc)
    TextView tvFuc;

    @InjectView(R.id.tvCon)
    TextView tvCon;

    @InjectView(R.id.tvLa)
    TextView tvLa;

    @InjectView(R.id.tvLo)
    TextView tvLo;
    /*-----------------------------------------*/
    private Intent intent;//跳转
    private AlertDialog alertDialog;//弹出框输入口令
    EditText et_dialog_pwd;//口令输入框--dialog
    Button bt_dialog_pwd_exit;//取消--dialog
    Button bt_dialog_pwd_sure;//确定--dialog

    private ExecutorService executorService;//线程池对象
    private SerialPortThread serialPortThread;//串口数据接收并显示
    private boolean serialPortThreadFlag = true;//判断串口接收是否开启或停止

    private DrawView my_view;
    private LinearLayout my_layout;
    private static int showtag = 4;

    String TuFile_Path = Environment.getExternalStorageDirectory().getPath() + "/tu.tu";
    String CheFile_Path = Environment.getExternalStorageDirectory().getPath() + "/che.che";

    private String resultStr;
    private ParsetoXWCJ parsetoXWCJ;
    private int tag = 0;
    private int seatTag = 0;//纪录座椅是否调整过
    private int centralTag = 0;//纪录中央后视镜是否调整过
    private int leftTag = 0;//纪录左后视镜是否调整过
    private MarkingBean markingBean;//扣分信息
    private ExamDataUtil examDataUtil;//处理练一练数据工具类




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test_drives);
        ButterKnife.inject(TestDrivesActivity.this);
        ll_single_parent.setVisibility(View.GONE);
        initListener();
        EventBus.getDefault().register(this);
        serialPortThread = new SerialPortThread();

        try {
            parsetoXWCJ = new ParsetoXWCJ(TestDrivesActivity.this);
            startSerialPortService();
        } catch (UnsatisfiedLinkError u) {
            Log.e("Error", u.toString());
            Toast.makeText(TestDrivesActivity.this, "未找到相关设备数据连接,请重试!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Error", e.toString());
        }

        //创建线程池,将费时操作放进线程池中
        executorService = Executors.newFixedThreadPool(1);
        executorService.execute(serialPortThread);

        intMap();
        initTcp();

    }

    /**
     * 开启串口数据接收
     */
    private void startSerialPortService() {
        //向串口发送开启命令
        SerialPortSendUtil serialPortSendUtil = new SerialPortSendUtil();
        serialPortSendUtil.sendMessage(new byte[]{0x55, (byte) 0xAA, 0x01, 0x01, 0x01});
        //打开串口数据接收服务
        intent = new Intent(TestDrivesActivity.this, SerialPortService.class);
        startService(intent);
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
     * 对组件进行绑定,在界面显示
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    tv_car_speed.setText(SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_speed", ""));//车速
                    tv_car_gear.setText(SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_gear_info", ""));//档位
                    rpm.setText(SharedPrefsUtils.getValue(TestDrivesActivity.this,"bf_rpm","")+"r/min");//转速
                    //点火1
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_engine_status", "").equals("01")) {
                        iv_fire_one.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_fire_one.setImageResource(R.drawable.oval_gray);
                    }

                    //雨刷
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_wiper", "").equals("87")) {
                        iv_wiper.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_wiper.setImageResource(R.drawable.oval_gray);
                    }

                    //点火2
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_engine_status", "").equals("02")) {
                        iv_fire_two.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_fire_two.setImageResource(R.drawable.oval_gray);
                    }

                    //喇叭
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_speaker", "").equals("C1")) {
                        iv_speaker.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_speaker.setImageResource(R.drawable.oval_gray);
                    }

                    //制动器
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_brake_status", "").equals("01")) {
                        iv_brake_status.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_brake_status.setImageResource(R.drawable.oval_gray);
                    }

                    //车门
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_door", "").equals("FB")) {
                        iv_door.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_door.setImageResource(R.drawable.oval_gray);
                    }

                    //安全带
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_belt_info", "").equals("40")) {
                        iv_belt_info.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_belt_info.setImageResource(R.drawable.oval_gray);
                    }

                    //离合
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_clutch", "").equals("01")) {
                        iv_clutch.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_clutch.setImageResource(R.drawable.oval_gray);
                    }

                    //报警灯
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_warning_light", "").equals("01")) {
                        iv_warning_light.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_warning_light.setImageResource(R.drawable.oval_gray);
                    }

                    //手刹
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_hand_brake", "").equals("01")) {
                        iv_hand_brake.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_hand_brake.setImageResource(R.drawable.oval_gray);
                    }

                    //视宽灯
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_position_light", "").equals("01")) {
                        iv_position_light.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_position_light.setImageResource(R.drawable.oval_gray);
                    }

                    //副刹车
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_assistant_brake_status", "").equals("01")) {
                        iv_assistant_brake_status.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_assistant_brake_status.setImageResource(R.drawable.oval_gray);
                    }

                    //左转灯
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_left_turn_light", "").equals("01")) {
                        iv_left_turn_light.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_left_turn_light.setImageResource(R.drawable.oval_gray);
                    }

                    //右转灯
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_right_turn_light", "").equals("01")) {
                        iv_right_turn_light.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_right_turn_light.setImageResource(R.drawable.oval_gray);
                    }

                    //中央后视镜
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_central_rearview_mirror", "").equals("01")) {
                        iv_central_rearview_mirror.setImageResource(R.drawable.oval_green);
                        centralTag = 1;
                    } else {
                        iv_central_rearview_mirror.setImageResource(R.drawable.oval_gray);
                    }

                    //近光灯
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_dipped_headlight", "").equals("01")) {
                        iv_dipped_headlight.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_dipped_headlight.setImageResource(R.drawable.oval_gray);
                    }

                    //座椅调整
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_seat_adjustment", "").equals("01")) {
                        iv_seat_adjustment.setImageResource(R.drawable.oval_green);
                        seatTag = 1;
                    } else {
                        iv_seat_adjustment.setImageResource(R.drawable.oval_gray);
                    }

                    //远光灯
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_high_beam_light", "").equals("01")) {
                        iv_high_beam_light.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_high_beam_light.setImageResource(R.drawable.oval_gray);
                    }

                    //雾灯
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_foglight", "").equals("01")) {
                        iv_foglight.setImageResource(R.drawable.oval_green);
                    } else {
                        iv_foglight.setImageResource(R.drawable.oval_gray);
                    }

                    //左后视镜
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_left_rearview_mirror", "").equals("01")) {
                        waihoushijing.setImageResource(R.drawable.oval_green);
                    } else {
                        waihoushijing.setImageResource(R.drawable.oval_gray);
                    }

                    //超声波距离信息1
                    tv_ultrasonic_one.setText(SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_ultrasonic_1", ""));

                    //超声波距离信息2
                    tv_ultrasonic_two.setText(SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_ultrasonic_2", ""));

                    if (tag == 1) {
                        parsetoXWCJ.getXWCJ();
                    }
//                    Log.d("TAG","点火---"+SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_engine_status", "")+"");
                    //判断是否在点火前
                    if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_engine_status", "").equals("02")) {
//                        Log.d("TAG","tag--"+tag+"");
//                      else {
                        if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_left_rearview_mirror", "").equals("01")) {
                            leftTag = 1;
//                            waihoushijing.setImageResource(R.drawable.oval_green);
                        }
                        if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_seat_adjustment", "").equals("01")) {
//                            iv_seat_adjustment.setImageResource(R.drawable.oval_green);
                            seatTag = 1;
                        }

                        if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_central_rearview_mirror", "").equals("01")) {
//                            iv_central_rearview_mirror.setImageResource(R.drawable.oval_green);
                            centralTag = 1;
                        }
//                            Log.d("TAG", seatTag + "--" + centralTag + "--" + leftTag);
                        //判断是否进行了考试前准备
                        if (SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_door", "").equals("FB") &&
                                SharedPrefsUtils.getValue(TestDrivesActivity.this, "bf_belt_info", "").equals("40") &&
                                seatTag == 1 && centralTag == 1 && leftTag == 1) {
                            tag = 1;
//                                Log.d("TAG=====","进入");
//                                Toast.makeText(TestDrivesActivity.this, "进入...", Toast.LENGTH_SHORT).show();
                            parsetoXWCJ.getXWCJ();
                        } else {
//                            Toast.makeText(TestDrivesActivity.this, "进行扣分...", Toast.LENGTH_SHORT).show();
//                            fraction = 10;
//                            showDialogForPass();
                        }
//                    }
                    } else {
//                        Toast.makeText(TestDrivesActivity.this, "进行扣分...", Toast.LENGTH_SHORT).show();
//                        fraction = 10;
//                        showDialogForPass();
                    }
                    break;
            }
        }
    };

    /**
     * 加载点击事件监听函数
     */
    private void initListener() {
        iv_testDrives_gameOver.setOnClickListener(new ClickListener());
        bt_testDrives_dataHandle.setOnClickListener(new ClickListener());
        iv_test_drives_map.setOnClickListener(new ClickListener());
        iv_test_drives_single.setOnClickListener(new ClickListener());
        max.setOnClickListener(new ClickListener());
        min.setOnClickListener(new ClickListener());
    }

    /**
     * 声明点击事件监听类
     */
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_testDrives_dataHandle://数据处理
                    showDialogForExamOver();
                    break;
                case R.id.iv_testDrives_gameOver://结束
                    intent = new Intent(TestDrivesActivity.this, MainActivity.class);
                    startActivity(intent);
                    TestDrivesActivity.this.finish();
                    break;
                case R.id.iv_test_drives_map://地图小按钮
                    iv_test_drives_map.setImageResource(R.drawable.map_check);
                    iv_test_drives_single.setImageResource(R.drawable.single_no_check);
                    my_layout.setVisibility(View.VISIBLE);
                    ll_single_parent.setVisibility(View.GONE);
                    break;
                case R.id.iv_test_drives_single://地图信号小按钮
                    iv_test_drives_map.setImageResource(R.drawable.map_no_check);
                    iv_test_drives_single.setImageResource(R.drawable.single_check);
                    my_layout.setVisibility(View.GONE);
                    ll_single_parent.setVisibility(View.VISIBLE);
                    break;
                case R.id.et_dialog_pwd://口令输入框--dialog
                    et_dialog_pwd.setFocusable(true);
                    et_dialog_pwd.setFocusableInTouchMode(true);
                    break;
                case R.id.bt_dialog_pwd_exit://取消--dialog
                    alertDialog.cancel();
                    break;
                case R.id.bt_dialog_pwd_sure://确定--dialog
                    if (!et_dialog_pwd.getText().toString().equals("")) {
                        //c660ed3c10e00b0f
                        if (et_dialog_pwd.getText().toString().equals("hongjitech")) {
//                            Log.d("TAG", "口令正确");
                            alertDialog.cancel();
                            intent = new Intent(TestDrivesActivity.this, CarDataActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d("TAG", "口令错误");
                        }
                    }
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
     * 设置输入口令的窗口
     */
    private void showDialogForExamOver() {
        View view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_data_pwd, null);
        alertDialog = new AlertDialog.Builder(this).setTitle(null).setMessage(null).setView(view).create();
        alertDialog.setCanceledOnTouchOutside(false);
        et_dialog_pwd = (EditText) view.findViewById(R.id.et_dialog_pwd);
        bt_dialog_pwd_exit = (Button) view.findViewById(R.id.bt_dialog_pwd_exit);
        bt_dialog_pwd_sure = (Button) view.findViewById(R.id.bt_dialog_pwd_sure);
        et_dialog_pwd.setOnClickListener(new ClickListener());
        bt_dialog_pwd_exit.setOnClickListener(new ClickListener());
        bt_dialog_pwd_sure.setOnClickListener(new ClickListener());
        alertDialog.show();
    }

    private void intMap() {
        getFilePath();

        my_layout = (LinearLayout) findViewById(R.id.root);
        my_view = new DrawView(TestDrivesActivity.this);
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

    private void initTcp() {
        TcpPresenter.getInstance().startReciveData();
    }

    private void getFilePath() {
        File sd = Environment.getExternalStorageDirectory();
        String strPath = sd.getPath();
        TuFile_Path = strPath + "/tu.tu";
        File file = new File(TuFile_Path);
        if (file.exists() == false) {
            Log.e("TAG", "tu文件不存在");
        }
        CheFile_Path = strPath + "/che.che";
        File file1 = new File(CheFile_Path);
        if (file1.exists() == false) {
            Log.e("TAG", "che文件不存在");
        }
    }

    /**
     * TcpMessage
     *
     * @param obj
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TcpMessage obj) {
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(tcpConnectState obj) {
        Message message = new Message();
        message.what = 0;
        message.obj = obj.strMsg.toString();
        handelr.sendMessage(message);
    }

    Handler handelr = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            String result = msg.obj.toString();
            getXWYD2(result);
            getEXAM(result);
            getGPHPD(result);
//            getXWYD1(result);
//            getXWCJ(result);
            return false;
        }
    });

    /**
     * 获取考试中扣分信号
     *
     * @param strMsg
     */
    private void getEXAM(String strMsg) {
        if (!TextUtils.isEmpty(strMsg) && strMsg.length() > 7) {
            String str = strMsg.substring(0, 6);
            if (str.equals("$EXAM")) {
                markingBean = examDataUtil.getExamData(strMsg);
                if (markingBean != null) {
                    if(markingBean.getMarkProject() != null){
                        tvPro.setText("PRO--"+markingBean.getMarkProject());
                    }
                    if(markingBean.getMarkFraction() != null){
                        tvFuc.setText("fAC"+markingBean.getMarkFraction());
                    }
                    if(markingBean.getMarkRes() != null){
                        tvCon.setText("RES"+markingBean.getMarkRes());
                    }
                } else {
                    Log.e("TAG", "扣分对象为空!");
                }
            } else {
                resultStr = null;
            }
        } else {

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
                String latitude = strMsg.substring(strMsg.indexOf(",", 6) + 1, strMsg.indexOf(",", 7));
                String longitude = strMsg.substring(strMsg.indexOf(",", 7) + 1, strMsg.indexOf(",", 8));
                tvLa.setText("La--"+latitude);
                tvLo.setText("Lo--"+longitude);
            } else {
                Log.e("TAG", "GPS为空!");
            }
        } else {
            Log.e("TAG", "GPS为空!");
        }
    }


    float hfloat, pfloat, rfloat, xfloat, yfloat, zfloat;
    String hString, pString, rString, xString, yString, zString;

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

    private void updateMap() {
        my_view.InputData(hfloat, pfloat, rfloat, xfloat, yfloat, zfloat);
        my_view.invalidate();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

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
}
