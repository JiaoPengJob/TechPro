package cn.hongjitech.vehicle.parameter;

/**
 * 扣分判断记录对象类
 * Created by jiaop on 2016/9/8.
 */
public class Marking {

    /**
     * 扣分项目名称
     */
    private String item;

    /**
     * 扣分代码
     */
    private String code;

    /**
     * 扣的分数
     */
    private String fraction;

    /**
     * 扣分的内容
     */
    private String content;

    /**
     * 扣分项语音
     */
    private String music;

    /**
     * 扣分日期
     */
    private String date;

    /**
     * 扣分的时间
     */
    private String time;

    public Marking() {
    }

    public Marking(String item, String code, String fraction, String content, String music, String date, String time) {
        this.item = item;
        this.code = code;
        this.fraction = fraction;
        this.content = content;
        this.music = music;
        this.date = date;
        this.time = time;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
