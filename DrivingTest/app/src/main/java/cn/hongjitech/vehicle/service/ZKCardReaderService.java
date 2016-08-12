package cn.hongjitech.vehicle.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.zkteco.android.biometric.core.device.ParameterHelper;
import com.zkteco.android.biometric.core.device.TransportType;
import com.zkteco.android.biometric.module.idcard.IDCardReader;
import com.zkteco.android.biometric.module.idcard.IDCardReaderFactory;
import com.zkteco.android.biometric.module.idcard.exception.IDCardReaderException;
import com.zkteco.android.biometric.module.idcard.meta.IDCardInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 指纹采集仪服务
 */
public class ZKCardReaderService extends Service {

    private ZKCardReaderBinder zkCardReaderBinder = new ZKCardReaderBinder();

    private String cardIdInfo;
    private static final int VID = 1024;
    private static final int PID = 50010;
    private IDCardReader idCardReader;

    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            beginIdCardDiscern();
            return false;
        }
    });

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
        if (handler != null) {
            handler.removeMessages(0);
            handler = null;
        }
        try {
            idCardReader.close(0);
        } catch (IDCardReaderException e) {

        }
    }

    @Override
    public IBinder onBind(Intent intent) {
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
        return zkCardReaderBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        if (timer != null) {
            timer.cancel();
        }
        if (handler != null) {
            handler.removeMessages(0);
            handler = null;
        }
        try {
            idCardReader.close(0);
        } catch (IDCardReaderException e) {

        }
        return super.onUnbind(intent);
    }


    private void beginIdCardDiscern() {
        Map idrparams = new HashMap();
        idrparams.put(ParameterHelper.PARAM_KEY_VID, VID);
        idrparams.put(ParameterHelper.PARAM_KEY_PID, PID);
        idCardReader = IDCardReaderFactory.createIDCardReader(this, TransportType.USB, idrparams);
        try {
            //打开身份证采集仪
            idCardReader.open(0);
            //获取SamId
            String samid = idCardReader.getSAMID(0);
//            Log.d("SamID:", samid);
            //寻卡
            idCardReader.findCard(0);
            //选卡
            idCardReader.selectCard(0);
            readCard();
        } catch (IDCardReaderException e) {
            Log.e("Error:", e.toString());
        }
    }

    /**
     * 读取身份证信息
     */
    public void readCard() {
        try {
            IDCardInfo idCardInfo = new IDCardInfo();
            boolean ret = idCardReader.readCard(0, 1, idCardInfo);
            if (ret) {
//                tvShowRead.setText("姓名:" + idCardInfo.getName() + ",民族:" + idCardInfo.getNation() + ",住址:" + idCardInfo.getAddress() + ",身份证号:" + idCardInfo.getId());
                if (idCardInfo.getId() != null) {
                    cardIdInfo = idCardInfo.getId();
                } else {
                    Log.e("Error:", "身份证号为空!");
                }

            }
        } catch (IDCardReaderException e) {
            Log.e("Error", "读取失败!" + e.toString());
        }
    }

    public class ZKCardReaderBinder extends Binder {

        public ZKCardReaderService getService() {
            return ZKCardReaderService.this;
        }

    }

    public String getCardIdInfo() {
        return cardIdInfo;
    }
}
