package cn.hongjitech.vehicle.javaBean;

/**
 * 存放本地存储的学员操作信息
 */
public class UploadUserInfo {

    private String examTime;//考核时间
    private String userTime;//用时
    private String markNumber;//扣分次数
    private String markProject;//扣分项目
    private String markFraction;//扣掉的分数
    private String markReason;//扣分原因
    private String trainProject;//训练项目名称
    private String trainTestNumber;//训练练习次数
    private String trainPassNumber;//训练及格次数
    private String trainPassing;//训练及格率
    private String trainSpeed;//训练平均速度
    private String headPortrait;//头像名称
    private String studentSignature;//学员签名图片的名称
    private String adminSignature;//教练签名图片的名称

    public UploadUserInfo() {
    }

    public UploadUserInfo(String examTime, String userTime, String markNumber, String markProject, String markFraction, String markReason, String trainProject, String trainTestNumber, String trainPassNumber, String trainPassing, String trainSpeed, String headPortrait, String studentSignature, String adminSignature) {
        this.examTime = examTime;
        this.userTime = userTime;
        this.markNumber = markNumber;
        this.markProject = markProject;
        this.markFraction = markFraction;
        this.markReason = markReason;
        this.trainProject = trainProject;
        this.trainTestNumber = trainTestNumber;
        this.trainPassNumber = trainPassNumber;
        this.trainPassing = trainPassing;
        this.trainSpeed = trainSpeed;
        this.headPortrait = headPortrait;
        this.studentSignature = studentSignature;
        this.adminSignature = adminSignature;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public String getMarkNumber() {
        return markNumber;
    }

    public void setMarkNumber(String markNumber) {
        this.markNumber = markNumber;
    }

    public String getMarkProject() {
        return markProject;
    }

    public void setMarkProject(String markProject) {
        this.markProject = markProject;
    }

    public String getMarkFraction() {
        return markFraction;
    }

    public void setMarkFraction(String markFraction) {
        this.markFraction = markFraction;
    }

    public String getMarkReason() {
        return markReason;
    }

    public void setMarkReason(String markReason) {
        this.markReason = markReason;
    }

    public String getTrainProject() {
        return trainProject;
    }

    public void setTrainProject(String trainProject) {
        this.trainProject = trainProject;
    }

    public String getTrainTestNumber() {
        return trainTestNumber;
    }

    public void setTrainTestNumber(String trainTestNumber) {
        this.trainTestNumber = trainTestNumber;
    }

    public String getTrainPassNumber() {
        return trainPassNumber;
    }

    public void setTrainPassNumber(String trainPassNumber) {
        this.trainPassNumber = trainPassNumber;
    }

    public String getTrainPassing() {
        return trainPassing;
    }

    public void setTrainPassing(String trainPassing) {
        this.trainPassing = trainPassing;
    }

    public String getTrainSpeed() {
        return trainSpeed;
    }

    public void setTrainSpeed(String trainSpeed) {
        this.trainSpeed = trainSpeed;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getStudentSignature() {
        return studentSignature;
    }

    public void setStudentSignature(String studentSignature) {
        this.studentSignature = studentSignature;
    }

    public String getAdminSignature() {
        return adminSignature;
    }

    public void setAdminSignature(String adminSignature) {
        this.adminSignature = adminSignature;
    }
}
