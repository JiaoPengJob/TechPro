package cn.hongjitech.vehicle.bean;

/**
 * 训练详情
 */
public class TrainDetail {

    private String projectName;//项目名称
    private String testCount;//练习次数
    private String passCount;//及格次数
    private String passNum;//及格率
    private String averageSpeed;//平均速度

    public TrainDetail() {
    }

    public TrainDetail(String projectName, String testCount, String passCount, String passNum, String averageSpeed) {
        this.projectName = projectName;
        this.testCount = testCount;
        this.passCount = passCount;
        this.passNum = passNum;
        this.averageSpeed = averageSpeed;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTestCount() {
        return testCount;
    }

    public void setTestCount(String testCount) {
        this.testCount = testCount;
    }

    public String getPassCount() {
        return passCount;
    }

    public void setPassCount(String passCount) {
        this.passCount = passCount;
    }

    public String getPassNum() {
        return passNum;
    }

    public void setPassNum(String passNum) {
        this.passNum = passNum;
    }

    public String getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(String averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
