package cn.hongjitech.vehicle.map;


/**
 * Created by kangcong on 2016/7/28.
 */
public class TcpPresenter {
    //接收数据
    static TCPClient _MyTCPClient;
    static Thread _TCPThread;

    static private TcpPresenter _instance;

    static synchronized public TcpPresenter getInstance() {
        if (_instance == null) {
            _instance = new TcpPresenter();
            iniTCPServer();
        }
        return _instance;
    }

    //初始化TCP服务器
    private static void iniTCPServer() {
        if (_TCPThread == null) {
            _MyTCPClient = new TCPClient();
            _TCPThread = new Thread(_MyTCPClient);
            _TCPThread.start();
        }
    }

    //断开TCP服务器
    public void closeTCPServer() {
        if (_TCPThread != null) {
            _TCPThread.interrupt();
            _TCPThread = null;
            _MyTCPClient = null;
        }
    }

    /**
     * 停止接收数据
     */
    public void startReciveData() {
        if (_MyTCPClient != null) {
            _MyTCPClient.bReciveData = true;
        }
    }

    /**
     * 停止接收数据
     */
    public void stopReciveData() {
        if (_MyTCPClient != null) {
            _MyTCPClient.bReciveData = false;
        }
    }

    /**
     * 向TCP发送消息
     */
    public boolean sendMsgToTcp(String strCmd) {
        if (_MyTCPClient != null && _MyTCPClient.bConnect) {
            _MyTCPClient.bReciveData = true;
            _MyTCPClient.sendMessage(strCmd);
            return true;
        } else {
            return false;
        }
    }
}
