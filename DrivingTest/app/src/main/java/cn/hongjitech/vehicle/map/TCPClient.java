package cn.hongjitech.vehicle.map;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 15-9-18.
 */
public class TCPClient implements Runnable {
    //    public String SERVER_IP = "10.10.100.254";//192.168.41.254
    public String SERVER_IP = "192.168.41.254";
    private String SERVER_Name = "车载终端设备";
    public int SERVERPORT = 8899;
    Socket socket;
    public Handler mHandler;
    BufferedReader input;
    InetAddress serverAddr;
    public boolean bConnect = false;
    public boolean bReciveData = false;
    private int nCount = 0;

    private void readTxtFile(String filePath) {
        try {
            String encoding = "GBK";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    SERVER_IP = lineTxt;
                    System.out.println("readTxtFile:" + lineTxt);
                    break;
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    public void getTCPIP() {
        File sd = Environment.getExternalStorageDirectory();
        String filePath = sd.getPath() + "/Training/TCP.txt";
        readTxtFile(filePath);
    }

    private void iniTCPClient() {
        try {
            if (nConnectCount == -1 || nConnectCount > 0.2 * 60 * 1000 / 50) {
                this.tcpConnecting();
                nConnectCount = 0;
            }
//            getTCPIP();
            if (SERVER_IP == "") {
                SERVER_IP = "10.10.100.254";
            }
            serverAddr = InetAddress.getByName(SERVER_IP);
            socket = new Socket(serverAddr, SERVERPORT);
//            socket.setSoTimeout(300);
            input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            bConnect = true;
        } catch (UnknownHostException e1) {
            bConnect = false;
            e1.printStackTrace();
        } catch (IOException e1) {
            bConnect = false;
            e1.printStackTrace();
        }
        if (bConnect) {
            this.tcpConnected();
        }
    }

    public Boolean sendMessage(String strMsg) {
        Boolean bresult = false;
        try {
            if (socket == null) {
                return false;
            }
            OutputStream out = socket.getOutputStream();
            out.write(strMsg.getBytes());
            out.flush();
            bresult = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bresult;
    }

    //连接次数
    private int nConnectCount = -1;
    //间隔
    private int ndur = 5;

    public void run() {
        int n = 0;
        while (!Thread.currentThread().isInterrupted()) {
            if (true) {
                //ndur = 50;
                //自动连接
                if (bConnect == false) {
                    iniTCPClient();
                    nConnectCount++;
                }
                n = 0;
                //接受数据
                if (bConnect) {
                    nConnectCount = -1;
                    while (n < 5 && bConnect) {
                        readDatabyTCP();
                        n++;
                    }
                    //间隔1分钟检测服务器发送数据情况
                    if (nCount > 1 * 60 * 1000 / ndur && sendMessage("train") == false) {
                        nCount = 0;
                        bConnect = false;
                    }
                }

                nCount++;
            }
            try {
                Thread.currentThread().sleep(ndur);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void readDatabyTCP1() {
        try {
            if (input != null) {
                String read = input.readLine();
                if (read == null) {
                } else {
                    //updatetrack(read, 1);
                    bConnect = true;
                }
            }
        } catch (IOException e) {
            bConnect = false;
            Log.w("train", "TcpClient tcp close IOException");
            e.printStackTrace();
        }

        if (bConnect == false) {
            this.tcpDisConnect();
        }

    }

    private void readDatabyTCP() {
        try {
            if (input != null) {
                char[] buffer = new char[1024];
                int c = input.read(buffer, 0, 1024);
                if (c > 0) {
                    String strMsg = new String(buffer, 0, c);
                    if (strMsg == null) {
                    } else {
                        if (bReciveData) {
                            //updatetrack(read, 1);
                            postMsgToEventBus(strMsg, 1);
                        }
                        bConnect = true;
                    }
                }

            }
        } catch (IOException e) {
            bConnect = false;
            Log.w("train", "TcpClient tcp close IOException");
            e.printStackTrace();
        }
        if (bConnect == false) {
            this.tcpDisConnect();
        }
    }


    public void postMsgToEventBus(String strMsg, int nType) {
        TcpMessage msg = new TcpMessage();
        msg.nType = nType;
        msg.strMsg = strMsg;
        System.out.println("postMsgToEventBus" + strMsg);
        EventBus.getDefault().post(msg);
    }

    public void postTcpStateToEventBus(String strMsg, int nType) {
        tcpConnectState msg = new tcpConnectState();
        msg.nType = nType;
        msg.strMsg = strMsg;
        System.out.println("postTcpStateToEventBus" + strMsg);
        EventBus.getDefault().post(msg);
    }

    private void tcpConnected() {
        this.postTcpStateToEventBus(SERVER_Name + " 连接成功", 1);
    }

    private void tcpConnecting() {
        this.postTcpStateToEventBus("正在连接 " + SERVER_Name, 0);
    }

    private void tcpDisConnect() {
        this.postTcpStateToEventBus(SERVER_Name + " 连接断开", 1);
    }
}