package cn.hongjitech.vehicle.javaBean;

import java.util.List;

/**
 * 预约信息:根
 */
public class ReservationInfoRoot {

    private String result;

    private List<ReservationInfo> data;

    public ReservationInfoRoot() {
    }

    public ReservationInfoRoot(String result, List<ReservationInfo> data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ReservationInfo> getData() {
        return data;
    }

    public void setData(List<ReservationInfo> data) {
        this.data = data;
    }
}
