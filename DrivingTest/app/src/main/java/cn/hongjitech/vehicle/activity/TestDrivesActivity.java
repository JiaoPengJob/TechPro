package cn.hongjitech.vehicle.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.nfc.Tag;
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
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android_serialport_api.SerialPort;
import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.bean.MarkingBean;
import cn.hongjitech.vehicle.javaBean.SerialPortBean;
import cn.hongjitech.vehicle.map.TcpMessage;
import cn.hongjitech.vehicle.map.TcpPresenter;
import cn.hongjitech.vehicle.map.tcpConnectState;
import cn.hongjitech.vehicle.service.SerialPortService;
import cn.hongjitech.vehicle.util.BufferHelper;
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
    @InjectView(R.id.waihoushijing)
    ImageView waihoushijing;//左后视镜
    /****************************************************************/
    @InjectView(R.id.tv_car_speed)
    TextView tv_car_speed;
    @InjectView(R.id.tv_baojingdeng)
    TextView tv_baojingdeng;
    @InjectView(R.id.rpm)
    TextView rpm;
    @InjectView(R.id.tv_shousha)
    TextView tv_shousha;
    @InjectView(R.id.tv_car_gear)
    TextView tv_car_gear;
    @InjectView(R.id.tv_shikuandeng)
    TextView tv_shikuandeng;
    @InjectView(R.id.tv_fire)
    TextView tv_fire;
    @InjectView(R.id.tv_fushache)
    TextView tv_fushache;
    @InjectView(R.id.tv_yugua)
    TextView tv_yugua;
    @InjectView(R.id.tv_zuozhunadeng)
    TextView tv_zuozhunadeng;
    @InjectView(R.id.tv_laba)
    TextView tv_laba;
    @InjectView(R.id.tv_waihoushijing)
    TextView tv_waihoushijing;
    @InjectView(R.id.tv_zhidongqi)
    TextView tv_zhidongqi;
    @InjectView(R.id.youzhuandeng)
    TextView youzhuandeng;
    @InjectView(R.id.tv_door)
    TextView tv_door;
    @InjectView(R.id.tv_zhongyang)
    TextView tv_zhongyang;
    @InjectView(R.id.tv_anquandai)
    TextView tv_anquandai;
    @InjectView(R.id.tv_jinguangdeng)
    TextView tv_jinguangdeng;
    @InjectView(R.id.tv_lihe)
    TextView tv_lihe;
    @InjectView(R.id.tv_yuanguangdeng)
    TextView tv_yuanguangdeng;
    @InjectView(R.id.tv_wudeng)
    TextView tv_wudeng;
    @InjectView(R.id.tv_zuoyi)
    TextView tv_zuoyi;
    /**************************************************/
    private boolean isPlaying = true;
    private SerialPort mSerialPort;
    private InputStream mInputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test_drives);
        ButterKnife.inject(TestDrivesActivity.this);
        open();
        SThread sThread = new SThread();
        sThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isPlaying = false;
        SerialPortSendUtil serialPortSendUtil = new SerialPortSendUtil();
        serialPortSendUtil.sendMessage(new byte[]{0x55, (byte) 0xAA, 0x01, 0x00, 0x00});
    }

    private void open() {
        //向串口发送开启命令
        SerialPortSendUtil serialPortSendUtil = new SerialPortSendUtil();
        serialPortSendUtil.sendMessage(new byte[]{0x55, (byte) 0xAA, 0x01, 0x01, 0x01});
        try {
            mSerialPort = new SerialPort(new File("/dev/ttyAMA2"), 9600, 0);
            mInputStream = mSerialPort.getInputStream();
        } catch (SecurityException s) {
            Log.e("Error", s + "");
        } catch (IOException i) {
            Log.e("Error", i + "");
        } catch (InvalidParameterException e) {
            Log.e("Error", e + "");
        }
    }

    class SThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (isPlaying) {
                try {
                    if (mInputStream == null) return;
                    //对串口数据进行处理
                    int i = 0;
                    if (mInputStream.available() >= 50) {
                        byte[] buffer = new byte[50];
                        int size = mInputStream.read(buffer, 0, 50);
                        for (i = 0; i < 49; i++) {
                            if (buffer[i] == (byte) 0xAA && buffer[i + 1] == (byte) 0x55) {
                                break;
                            }
                        }
                        if (i != 0) {
                            mInputStream.read(buffer, 0, i);
                            continue;
                        }

                        SerialPortBean spb = BufferHelper.commitBuffer(TestDrivesActivity.this, buffer);
                        Message message = new Message();
                        message.what = 0;
                        message.obj = spb;
                        handler.sendMessage(message);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            SerialPortBean spb = (SerialPortBean) msg.obj;
            if (spb != null) {

                tv_car_speed.setText(spb.getBf_speed() + "km/h");//车速
                tv_car_gear.setText(spb.getBf_gear_info());//档位
                rpm.setText(spb.getBf_rpm() + "r/min");//转速
                tv_fire.setText(spb.getBf_engine_status());
                tv_yugua.setText(spb.getBf_wiper());
                tv_laba.setText(spb.getBf_speaker());
                tv_zhidongqi.setText(spb.getBf_brake_status());
                tv_door.setText(spb.getBf_door());
                tv_anquandai.setText(spb.getBf_belt_info());
                tv_lihe.setText(spb.getBf_clutch());
                tv_wudeng.setText(spb.getBf_foglight());
                tv_baojingdeng.setText(spb.getBf_warning_light());
                tv_shousha.setText(spb.getBf_hand_brake());
                tv_shikuandeng.setText(spb.getBf_position_light());
                tv_fushache.setText(spb.getBf_assistant_brake_status());
                tv_zuozhunadeng.setText(spb.getBf_left_turn_light());
                tv_waihoushijing.setText(spb.getBf_left_rearview_mirror());
                youzhuandeng.setText(spb.getBf_right_turn_light());
                tv_zhongyang.setText(spb.getBf_central_rearview_mirror());
                tv_jinguangdeng.setText(spb.getBf_dipped_headlight());
                tv_yuanguangdeng.setText(spb.getBf_high_beam_light());
                tv_zuoyi.setText(spb.getBf_seat_adjustment());

                /***********************************************************/

                //点火1
                if (spb.getBf_engine_status().equals("01")) {
                    iv_fire_one.setImageResource(R.drawable.oval_green);
                } else {
                    iv_fire_one.setImageResource(R.drawable.oval_gray);
                }
                //雨刷
                if (spb.getBf_wiper().equals("87")) {
                    iv_wiper.setImageResource(R.drawable.oval_green);
                } else {
                    iv_wiper.setImageResource(R.drawable.oval_gray);
                }
                //点火2
                if (spb.getBf_engine_status().equals("02")) {
                    iv_fire_two.setImageResource(R.drawable.oval_green);
                } else {
                    iv_fire_two.setImageResource(R.drawable.oval_gray);
                }
                //喇叭
                if (spb.getBf_speaker().equals("C1")) {
                    iv_speaker.setImageResource(R.drawable.oval_green);
                } else {
                    iv_speaker.setImageResource(R.drawable.oval_gray);
                }
                //制动器
                if (spb.getBf_brake_status().equals("01")) {
                    iv_brake_status.setImageResource(R.drawable.oval_green);
                } else {
                    iv_brake_status.setImageResource(R.drawable.oval_gray);
                }
                //车门
                if (spb.getBf_door().equals("FB")) {
                    iv_door.setImageResource(R.drawable.oval_green);
                } else {
                    iv_door.setImageResource(R.drawable.oval_gray);
                }
                //安全带
                if (spb.getBf_belt_info().equals("40")) {
                    iv_belt_info.setImageResource(R.drawable.oval_green);
                } else {
                    iv_belt_info.setImageResource(R.drawable.oval_gray);
                }
                //离合
                if (spb.getBf_clutch().equals("01")) {
                    iv_clutch.setImageResource(R.drawable.oval_green);
                } else {
                    iv_clutch.setImageResource(R.drawable.oval_gray);
                }
                //报警灯
                if (spb.getBf_warning_light().equals("01")) {
                    iv_warning_light.setImageResource(R.drawable.oval_green);
                } else {
                    iv_warning_light.setImageResource(R.drawable.oval_gray);
                }
                //手刹
                if (spb.getBf_hand_brake().equals("01")) {
                    iv_hand_brake.setImageResource(R.drawable.oval_green);
                } else {
                    iv_hand_brake.setImageResource(R.drawable.oval_gray);
                }
                //视宽灯
                if (spb.getBf_position_light().equals("01")) {
                    iv_position_light.setImageResource(R.drawable.oval_green);
                } else {
                    iv_position_light.setImageResource(R.drawable.oval_gray);
                }
                //副刹车
                if (spb.getBf_assistant_brake_status().equals("01")) {
                    iv_assistant_brake_status.setImageResource(R.drawable.oval_green);
                } else {
                    iv_assistant_brake_status.setImageResource(R.drawable.oval_gray);
                }
                //左转灯
                if (spb.getBf_left_turn_light().equals("01")) {
                    iv_left_turn_light.setImageResource(R.drawable.oval_green);
                } else {
                    iv_left_turn_light.setImageResource(R.drawable.oval_gray);
                }
                //右转灯
                if (spb.getBf_right_turn_light().equals("01")) {
                    iv_right_turn_light.setImageResource(R.drawable.oval_green);
                } else {
                    iv_right_turn_light.setImageResource(R.drawable.oval_gray);
                }
                //中央后视镜
                if (spb.getBf_central_rearview_mirror().equals("01")) {
                    iv_central_rearview_mirror.setImageResource(R.drawable.oval_green);
                } else {
                    iv_central_rearview_mirror.setImageResource(R.drawable.oval_gray);
                }
                //近光灯
                if (spb.getBf_dipped_headlight().equals("01")) {
                    iv_dipped_headlight.setImageResource(R.drawable.oval_green);
                } else {
                    iv_dipped_headlight.setImageResource(R.drawable.oval_gray);
                }
                //座椅调整
                if (spb.getBf_seat_adjustment().equals("01")) {
                    iv_seat_adjustment.setImageResource(R.drawable.oval_green);
                } else {
                    iv_seat_adjustment.setImageResource(R.drawable.oval_gray);
                }
                //远光灯
                if (spb.getBf_high_beam_light().equals("01")) {
                    iv_high_beam_light.setImageResource(R.drawable.oval_green);
                } else {
                    iv_high_beam_light.setImageResource(R.drawable.oval_gray);
                }
                //左后视镜
                if (spb.getBf_left_rearview_mirror().equals("01")) {
                    waihoushijing.setImageResource(R.drawable.oval_green);
                } else {
                    waihoushijing.setImageResource(R.drawable.oval_gray);
                }
                //雾灯
                if (spb.getBf_foglight().equals("01")) {
                    iv_foglight.setImageResource(R.drawable.oval_green);
                } else {
                    iv_foglight.setImageResource(R.drawable.oval_gray);
                }
                //超声波距离信息1
                tv_ultrasonic_one.setText(spb.getBf_ultrasonic_1());
                //超声波距离信息2
                tv_ultrasonic_two.setText(spb.getBf_ultrasonic_2());
            }
            return false;
        }
    });


}
