package cn.hongjitech.vehicle.javaBean;

/**
 * 考官信息根
 */
public class ExamAdminRoot {

    private String result;

    private String err_message;

    private ExamAdminInfo data;

    public ExamAdminRoot() {
    }

    public ExamAdminRoot(String result, String err_message, ExamAdminInfo data) {
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

    public ExamAdminInfo getData() {
        return data;
    }

    public void setData(ExamAdminInfo data) {
        this.data = data;
    }
}
