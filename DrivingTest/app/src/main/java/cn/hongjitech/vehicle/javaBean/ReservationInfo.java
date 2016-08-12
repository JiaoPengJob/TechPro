package cn.hongjitech.vehicle.javaBean;

import java.util.List;

/**
 * 预约信息
 */
public class ReservationInfo {

    private int id;

    private int school_id;

    private String start_time;

    private String finish_time;

    private int normal_fee;

    private int holiday_fee;

    private int time_length;

    private String created_at;

    private String updated_at;

    private Appointment appointment;

    public ReservationInfo() {
    }

    public ReservationInfo(int id, int school_id, String start_time, String finish_time, int normal_fee, int holiday_fee, int time_length, String created_at, String updated_at, Appointment appointment) {
        this.id = id;
        this.school_id = school_id;
        this.start_time = start_time;
        this.finish_time = finish_time;
        this.normal_fee = normal_fee;
        this.holiday_fee = holiday_fee;
        this.time_length = time_length;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.appointment = appointment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public int getNormal_fee() {
        return normal_fee;
    }

    public void setNormal_fee(int normal_fee) {
        this.normal_fee = normal_fee;
    }

    public int getHoliday_fee() {
        return holiday_fee;
    }

    public void setHoliday_fee(int holiday_fee) {
        this.holiday_fee = holiday_fee;
    }

    public int getTime_length() {
        return time_length;
    }

    public void setTime_length(int time_length) {
        this.time_length = time_length;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
