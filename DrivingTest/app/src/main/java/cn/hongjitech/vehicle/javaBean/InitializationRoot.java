package cn.hongjitech.vehicle.javaBean;

/**
 * 服务器初始化信息根
 */
public class InitializationRoot {

    private String result;

    private String err_message;

    private InitializationData data;

    public InitializationRoot() {
    }

    public InitializationRoot(String result, String err_message, InitializationData data) {
        this.result = result;
        this.err_message = err_message;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public InitializationData getData() {
        return data;
    }

    public void setData(InitializationData data) {
        this.data = data;
    }

    public String getErr_message() {
        return err_message;
    }

    public void setErr_message(String err_message) {
        this.err_message = err_message;
    }
}
