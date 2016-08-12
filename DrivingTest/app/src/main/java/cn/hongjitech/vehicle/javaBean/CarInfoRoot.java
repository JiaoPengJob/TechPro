package cn.hongjitech.vehicle.javaBean;

/**
 * 车辆信息根
 */
public class CarInfoRoot {

    private String result;
    private CarInfo data;

    public CarInfoRoot() {
    }

    public CarInfoRoot(String result, CarInfo data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public CarInfo getData() {
        return data;
    }

    public void setData(CarInfo data) {
        this.data = data;
    }
}
