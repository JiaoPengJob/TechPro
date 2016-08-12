package cn.hongjitech.vehicle.javaBean;

/**
 * 教练:根类
 */
public class AdminRoot {

    private String result;

    private Admin data;

    public AdminRoot() {
    }

    public AdminRoot(String result, Admin data) {
        this.result = result;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Admin getData() {
        return data;
    }

    public void setData(Admin data) {
        this.data = data;
    }
}
