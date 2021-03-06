package cn.hongjitech.vehicle.bean;

import java.io.Serializable;

/**
 * 扣分列表信息
 */
public class MarkingBean implements Serializable {

    private String markProject;//扣分项目
    private String markFraction;//扣分
    private String markRes;//扣分原因
    private String media;//语音文件名
    private String code;//扣分代码

    public MarkingBean() {
    }

    public MarkingBean(String markProject, String markFraction, String markRes, String media, String code) {
        this.markProject = markProject;
        this.markFraction = markFraction;
        this.markRes = markRes;
        this.media = media;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
