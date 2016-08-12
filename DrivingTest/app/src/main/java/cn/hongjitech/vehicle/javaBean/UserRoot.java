package cn.hongjitech.vehicle.javaBean;

/**
 * 获取用户信息的Root信息
 */
public class UserRoot {

    private String result;

    private User data;

    public UserRoot() {
    }

    public UserRoot(String result, User data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
