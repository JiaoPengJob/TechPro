package cn.hongjitech.vehicle.javaBean;

/**
 * 设备id信息
 */
public class DeviceRoot {

    private String result;
    private String data;

    public DeviceRoot() {
    }

    public DeviceRoot(String result, String data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
