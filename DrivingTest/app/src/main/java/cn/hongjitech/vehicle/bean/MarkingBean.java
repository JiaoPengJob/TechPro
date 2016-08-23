package cn.hongjitech.vehicle.bean;

import java.io.Serializable;

/**
 * 扣分列表信息
 */
public class MarkingBean implements Serializable {

    private String markProject;//扣分项目
    private String markFraction;//扣分
    private String markRes;//扣分原因
    private String media;

    public MarkingBean() {
    }

    public MarkingBean(String markProject, String markFraction, String markRes,String media) {
        this.markProject = markProject;
        this.markFraction = markFraction;
        this.markRes = markRes;
        this.media = media;
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

    public String getMarkRes() {
        return markRes;
    }

    public void setMarkRes(String markRes) {
        this.markRes = markRes;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
