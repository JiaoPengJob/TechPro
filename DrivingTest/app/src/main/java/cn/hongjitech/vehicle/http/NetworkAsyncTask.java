package cn.hongjitech.vehicle.http;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 获取服务器信息接口
 */
public class NetworkAsyncTask extends AsyncTask<String, Integer, String> {

    String authorization;
    String urlStr;
    String param;
    String method;
    Handler handler;
    int index;

    public NetworkAsyncTask(String urlStr, String method, String authorization, String params, Handler handler, int index) {
        this.urlStr = urlStr;
        this.authorization = authorization;
        this.param = params;
        this.method = method;
        this.handler = handler;
        this.index = index;
    }

    @Override
    protected String doInBackground(String... params) {
        String result = "";
        URL url = null;//请求的URL地址
        HttpURLConnection conn = null;
        byte[] responseBody = null;//响应体
        try {
            //用POST发送JSON数据
            url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            //通过setRequestMethod将conn设置成POST方法
            conn.setRequestMethod(method);
            //调用conn.setDoOutput()方法以显式开启请求体
            conn.setDoOutput(true);
            //用setRequestProperty方法设置一个自定义的请求头:action，由于后端判断
            conn.setRequestProperty("Authorization", "Bearer " + authorization);
            //获取请求头
//          requestHeader = getReqeustHeader(conn);
            //获取conn的输出流
            OutputStream os = conn.getOutputStream();
            //将请求体写入到conn的输出流中
            os.write(param.getBytes());
            //记得调用输出流的flush方法
            os.flush();
            //关闭输出流
            os.close();
            //当调用getInputStream方法时才真正将请求体数据上传至服务器
            InputStream is = conn.getInputStream();
            //获得响应体的字节数组
            responseBody = getBytesByInputStream(is);
            //获得响应头
//          responseHeader = getResponseHeader(conn);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //最后将conn断开连接
            if (conn != null) {
                conn.disconnect();
            }
        }
        try {
            result = new String(responseBody, "UTF-8");
        } catch (UnsupportedEncodingException e) {

        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Message msg = handler.obtainMessage();
        if (result != null) {
            msg.what = index;
            msg.obj = result;
        } else {
            msg.what = 0;
        }
        handler.sendMessage(msg);
    }

    /**
     * 从InputStream中读取数据，转换成byte数组，最后关闭InputStream
     *
     * @param is
     * @return
     */
    private byte[] getBytesByInputStream(InputStream is) {
        byte[] bytes = null;
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);
        byte[] buffer = new byte[1024 * 8];
        int length = 0;
        try {
            while ((length = bis.read(buffer)) > 0) {
                bos.write(buffer, 0, length);
            }
            bos.flush();
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

}
