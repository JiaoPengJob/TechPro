package cn.hongjitech.vehicle.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android_serialport_api.SerialPort;
import cn.hongjitech.vehicle.util.BufferHelper;
import cn.hongjitech.vehicle.util.SharedPrefsUtils;

/**
 * 串口接收信号服务
 */
public class SerialPortService extends Service {

    private SerialPort mSerialPort;
    private InputStream mInputStream;
    private boolean isPlaying = true;
    private SThread thread;
    public static Intent sIntent;

    @Override
    public void onCreate() {
        Log.d("service", "onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service", "onStartCommand");
        sIntent = intent;
        open();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("service", "onDestroy");
        super.onDestroy();
        isPlaying = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void open() {
        try {
            Log.d("service", "open");
            mSerialPort = new SerialPort(new File("/dev/ttyAMA2"), 9600, 0);
            mInputStream = mSerialPort.getInputStream();
            thread = new SThread();
            thread.start();
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
//                        SharedPrefsUtils.clear(SerialPortService.this);
                        BufferHelper.commitBuffer(SerialPortService.this, buffer);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
