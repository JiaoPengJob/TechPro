package cn.hongjitech.vehicle.util;

import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;

/**
 * 向判分仪器发送信号
 */
public class XGYDSerialPortSendUtil {

    private SerialPort mSerialPort;
    private OutputStream mOutputStream;

    public XGYDSerialPortSendUtil() {
        try {
            mSerialPort = new SerialPort(new File("/dev/ttyAMA3"), 38400, 0);
            mOutputStream = mSerialPort.getOutputStream();
        } catch (IOException i) {
            Log.e("Error", i.toString());
        } catch (SecurityException s){
            Log.e("Error", s.toString());
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
