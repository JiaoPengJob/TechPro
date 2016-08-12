package cn.hongjitech.vehicle.util;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;

/**
 * 发送信号至串口
 */
public class SerialPortSendUtil {

    private SerialPort mSerialPort;
    private OutputStream mOutputStream;

    public SerialPortSendUtil() {
        try {
            mSerialPort = new SerialPort(new File("/dev/ttyAMA2"), 9600, 0);
            mOutputStream = mSerialPort.getOutputStream();
        } catch (IOException i) {
            Log.e("Error", i.toString());
        }
    }

    public void sendMessage(byte[] bytes) {
        try {
            mOutputStream.write(bytes);
        } catch (SecurityException s) {
            Log.e("Error", s.toString());
        } catch (IOException i) {
            Log.e("Error", i.toString());
        } catch (InvalidParameterException e) {
            Log.e("Error", e.toString());
        }
    }

}
