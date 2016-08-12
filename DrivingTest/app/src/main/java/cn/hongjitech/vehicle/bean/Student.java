package cn.hongjitech.vehicle.bean;

import android.graphics.Bitmap;

/**
 * 学员类
 */
public class Student {

    private String name;//学员姓名
    private String sex;//性别
    private String IDnumber;//身份证号
    private String ticketNumber;//准考证号
    private String reserveTeacher;//预约教练
    private String reserveCar;//预约车型
    private String reserveCount;//预约次数
    private Bitmap photo;//个人照片
    private String beginTime;

    public Student() {
    }

    public Student(String name, String sex, String IDnumber) {
        this.name = name;
        this.sex = sex;
        this.IDnumber = IDnumber;
    }

    public Student(String name, String sex, String IDnumber, String ticketNumber, String reserveTeacher, String reserveCar, String reserveCount, Bitmap photo) {
        this.name = name;
        this.sex = sex;
        this.IDnumber = IDnumber;
        this.ticketNumber = ticketNumber;
        this.reserveTeacher = reserveTeacher;
        this.reserveCar = reserveCar;
        this.reserveCount = reserveCount;
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIDnumber() {
        return IDnumber;
    }

    public void setIDnumber(String IDnumber) {
        this.IDnumber = IDnumber;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getReserveTeacher() {
        return reserveTeacher;
    }

    public void setReserveTeacher(String reserveTeacher) {
        this.reserveTeacher = reserveTeacher;
    }

    public String getReserveCar() {
        return reserveCar;
    }

    public void setReserveCar(String reserveCar) {
        this.reserveCar = reserveCar;
    }

    public String getReserveCount() {
        return reserveCount;
    }

    public void setReserveCount(String reserveCount) {
        this.reserveCount = reserveCount;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
