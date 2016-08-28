package cn.hongjitech.vehicle.javaBean;

/**
 * GPS
 */
public class GPSBean {

    private String id;
    private String examiner_times;
    private String single_project;
    private String speed;
    private String logitude;
    private String latitude;

    public GPSBean() {
    }

    public GPSBean(String id, String examiner_times, String single_project, String speed, String logitude, String latitude) {
        this.id = id;
        this.examiner_times = examiner_times;
        this.single_project = single_project;
        this.speed = speed;
        this.logitude = logitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExaminer_times() {
        return examiner_times;
    }

    public void setExaminer_times(String examiner_times) {
        this.examiner_times = examiner_times;
    }

    public String getSingle_project() {
        return single_project;
    }

    public void setSingle_project(String single_project) {
        this.single_project = single_project;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getLogitude() {
        return logitude;
    }

    public void setLogitude(String logitude) {
        this.logitude = logitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
