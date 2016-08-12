package cn.hongjitech.vehicle.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 学员信息,以及考核信息
 */
public class UserInfo implements Serializable {

    private String name;
    private String idCode;
    private String startTime;
    private String endTime;
    private String num;
    private String length;
    private String userTime;
    private List<MarkingBean> markingBean;

    public UserInfo() {
    }

    public UserInfo(String name, String idCode, String startTime, String endTime, String num, String length, String userTime, List<MarkingBean> markingBean) {
        this.name = name;
        this.idCode = idCode;
        this.startTime = startTime;
        this.endTime = endTime;
        this.num = num;
        this.length = length;
        this.userTime = userTime;
        this.markingBean = markingBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public List<MarkingBean> getMarkingBean() {
        return markingBean;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public void setMarkingBean(List<MarkingBean> markingBean) {
        this.markingBean = markingBean;
    }
}
