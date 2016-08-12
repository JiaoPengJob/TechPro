package cn.hongjitech.vehicle.javaBean;

import java.io.Serializable;

/**
 *
 */
public class User implements Serializable {

    private int id;
    private String user_img;
    private String user_truename;
    private String user_sex;
    private String subject_1;
    private String subject_2;
    private String subject_3;
    private String subject_4;
    private String user_id_card;
    private String user_telphone;
    private int old_province_id;
    private int old_city_id;
    private int old_area_id;
    private int new_province_id;
    private int new_city_id;
    private int new_area_id;
    private String user_address;
    private String user_email;
    private int school_id;
    private int licence_type_id;
    private int class_id;
    private String status;
    private int is_healthy;
    private String apply_date;
    private String created_at;
    private String updated_at;
    private String hk_address;
    private String xz_address;


    public User() {
    }

    public User(int id, String user_img, String user_truename, String user_sex, String subject_1, String subject_2, String subject_3, String subject_4, String user_id_card, String user_telphone, int old_province_id, int old_city_id, int old_area_id, int new_province_id, int new_city_id, int new_area_id, String user_address, String user_email, int school_id, int licence_type_id, int class_id, String status, int is_healthy, String apply_date, String created_at, String updated_at, String hk_address, String xz_address) {
        this.id = id;
        this.user_img = user_img;
        this.user_truename = user_truename;
        this.user_sex = user_sex;
        this.subject_1 = subject_1;
        this.subject_2 = subject_2;
        this.subject_3 = subject_3;
        this.subject_4 = subject_4;
        this.user_id_card = user_id_card;
        this.user_telphone = user_telphone;
        this.old_province_id = old_province_id;
        this.old_city_id = old_city_id;
        this.old_area_id = old_area_id;
        this.new_province_id = new_province_id;
        this.new_city_id = new_city_id;
        this.new_area_id = new_area_id;
        this.user_address = user_address;
        this.user_email = user_email;
        this.school_id = school_id;
        this.licence_type_id = licence_type_id;
        this.class_id = class_id;
        this.status = status;
        this.is_healthy = is_healthy;
        this.apply_date = apply_date;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.hk_address = hk_address;
        this.xz_address = xz_address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public String getUser_truename() {
        return user_truename;
    }

    public void setUser_truename(String user_truename) {
        this.user_truename = user_truename;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getSubject_1() {
        return subject_1;
    }

    public void setSubject_1(String subject_1) {
        this.subject_1 = subject_1;
    }

    public String getSubject_2() {
        return subject_2;
    }

    public void setSubject_2(String subject_2) {
        this.subject_2 = subject_2;
    }

    public String getSubject_3() {
        return subject_3;
    }

    public void setSubject_3(String subject_3) {
        this.subject_3 = subject_3;
    }

    public String getSubject_4() {
        return subject_4;
    }

    public void setSubject_4(String subject_4) {
        this.subject_4 = subject_4;
    }

    public String getUser_id_card() {
        return user_id_card;
    }

    public void setUser_id_card(String user_id_card) {
        this.user_id_card = user_id_card;
    }

    public String getUser_telphone() {
        return user_telphone;
    }

    public void setUser_telphone(String user_telphone) {
        this.user_telphone = user_telphone;
    }

    public int getOld_province_id() {
        return old_province_id;
    }

    public void setOld_province_id(int old_province_id) {
        this.old_province_id = old_province_id;
    }

    public int getOld_city_id() {
        return old_city_id;
    }

    public void setOld_city_id(int old_city_id) {
        this.old_city_id = old_city_id;
    }

    public int getOld_area_id() {
        return old_area_id;
    }

    public void setOld_area_id(int old_area_id) {
        this.old_area_id = old_area_id;
    }

    public int getNew_province_id() {
        return new_province_id;
    }

    public void setNew_province_id(int new_province_id) {
        this.new_province_id = new_province_id;
    }

    public int getNew_city_id() {
        return new_city_id;
    }

    public void setNew_city_id(int new_city_id) {
        this.new_city_id = new_city_id;
    }

    public int getNew_area_id() {
        return new_area_id;
    }

    public void setNew_area_id(int new_area_id) {
        this.new_area_id = new_area_id;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public int getLicence_type_id() {
        return licence_type_id;
    }

    public void setLicence_type_id(int licence_type_id) {
        this.licence_type_id = licence_type_id;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIs_healthy() {
        return is_healthy;
    }

    public void setIs_healthy(int is_healthy) {
        this.is_healthy = is_healthy;
    }

    public String getApply_date() {
        return apply_date;
    }

    public void setApply_date(String apply_date) {
        this.apply_date = apply_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getHk_address() {
        return hk_address;
    }

    public void setHk_address(String hk_address) {
        this.hk_address = hk_address;
    }

    public String getXz_address() {
        return xz_address;
    }

    public void setXz_address(String xz_address) {
        this.xz_address = xz_address;
    }
}
