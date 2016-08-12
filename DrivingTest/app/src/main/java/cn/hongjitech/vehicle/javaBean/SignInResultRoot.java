package cn.hongjitech.vehicle.javaBean;

import java.util.List;

/**
 * Created by jiaop on 2016/8/8.
 */
public class SignInResultRoot {

    private int status;
    private String message;
    private List<String> data;

    public SignInResultRoot() {
    }

    public SignInResultRoot(int status, String message, List<String> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
