package cn.hongjitech.vehicle.javaBean;

/**
 * GPS根
 */
public class GPSBeanRoot {

    private String car_num;
    private String car_status;
    private String type;
    private GPSBean data;

    public GPSBeanRoot() {
    }

    public GPSBeanRoot(String car_num, String car_status, String type, GPSBean data) {
        this.car_num = car_num;
        this.car_status = car_status;
        this.type = type;
        this.data = data;
    }

    public String getCar_num() {
        return car_num;
    }

    public void setCar_num(String car_num) {
        this.car_num = car_num;
    }

    public String getCar_status() {
        return car_status;
    }

    public void setCar_status(String car_status) {
        this.car_status = car_status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GPSBean getData() {
        return data;
    }

    public void setData(GPSBean data) {
        this.data = data;
    }
}
