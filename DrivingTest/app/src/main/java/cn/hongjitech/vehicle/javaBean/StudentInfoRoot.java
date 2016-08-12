package cn.hongjitech.vehicle.javaBean;

import java.util.List;

/**
 * 考生信息根
 */
public class StudentInfoRoot {

    private String result;

    private String err_message;

    private List<StudentInfo> data;

    public StudentInfoRoot() {
    }

    public StudentInfoRoot(String result, String err_message, List<StudentInfo> data) {
        this.result = result;
        this.err_message = err_message;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getErr_message() {
        return err_message;
    }

    public void setErr_message(String err_message) {
        this.err_message = err_message;
    }

    public List<StudentInfo> getData() {
        return data;
    }

    public void setData(List<StudentInfo> data) {
        this.data = data;
    }
}
