package cn.hongjitech.vehicle.bean;

/**
 * 扣分代码
 */
public class ArtificialCode {

    private String code;
    private String content;
    private String fraction;

    public ArtificialCode() {
    }

    public ArtificialCode(String code, String content, String fraction) {
        this.code = code;
        this.content = content;
        this.fraction = fraction;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFraction() {
        return fraction;
    }

    public void setFraction(String fraction) {
        this.fraction = fraction;
    }
}
