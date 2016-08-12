package cn.hongjitech.vehicle.javaBean;

/**
 * 服务器初始化信息
 */
public class InitializationData {

    private String examination_room_no;

    private int time;

    private String token;

    private int evaluation_mode;

    private String expires_in;

    private String vehicle_no;

    private int type;

    private String vehicle_condition;

    public InitializationData() {
    }

    public InitializationData(String examination_room_no, int time, String token, int evaluation_mode, String expires_in, String vehicle_no, int type, String vehicle_condition) {
        this.examination_room_no = examination_room_no;
        this.time = time;
        this.token = token;
        this.evaluation_mode = evaluation_mode;
        this.expires_in = expires_in;
        this.vehicle_no = vehicle_no;
        this.type = type;
        this.vehicle_condition = vehicle_condition;
    }

    public String getExamination_room_no() {
        return examination_room_no;
    }

    public void setExamination_room_no(String examination_room_no) {
        this.examination_room_no = examination_room_no;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getEvaluation_mode() {
        return evaluation_mode;
    }

    public void setEvaluation_mode(int evaluation_mode) {
        this.evaluation_mode = evaluation_mode;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVehicle_condition() {
        return vehicle_condition;
    }

    public void setVehicle_condition(String vehicle_condition) {
        this.vehicle_condition = vehicle_condition;
    }
}
