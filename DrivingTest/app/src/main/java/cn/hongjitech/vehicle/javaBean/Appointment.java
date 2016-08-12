package cn.hongjitech.vehicle.javaBean;

/**
 * 已预约信息
 */
public class Appointment {

    private int id;
    private int school_id;
    private int user_id;
    private int admin_id;
    private int hour_id;
    private String status;
    private String start_time;
    private String end_time;
    private String date;
    private int type_id;
    private int bus_id;
    private String bus_time;
    private String detail;
    private String created_at;
    private String updated_at;
    private String handle;

    public Appointment() {
    }

    public Appointment(int id, int school_id, int user_id, int admin_id, int hour_id, String status, String start_time, String end_time, String date, int type_id, int bus_id, String bus_time, String detail, String created_at, String updated_at, String handle) {
        this.id = id;
        this.school_id = school_id;
        this.user_id = user_id;
        this.admin_id = admin_id;
        this.hour_id = hour_id;
        this.status = status;
        this.start_time = start_time;
        this.end_time = end_time;
        this.date = date;
        this.type_id = type_id;
        this.bus_id = bus_id;
        this.bus_time = bus_time;
        this.detail = detail;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.handle = handle;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public int getHour_id() {
        return hour_id;
    }

    public void setHour_id(int hour_id) {
        this.hour_id = hour_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getBus_id() {
        return bus_id;
    }

    public void setBus_id(int bus_id) {
        this.bus_id = bus_id;
    }

    public String getBus_time() {
        return bus_time;
    }

    public void setBus_time(String bus_time) {
        this.bus_time = bus_time;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

}
