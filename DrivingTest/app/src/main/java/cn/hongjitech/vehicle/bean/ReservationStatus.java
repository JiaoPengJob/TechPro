package cn.hongjitech.vehicle.bean;

/**
 *
 */
public class ReservationStatus {

    private String time;
    private String state;

    public ReservationStatus() {
    }

    public ReservationStatus(String time, String state) {
        this.time = time;
        this.state = state;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
