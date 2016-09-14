package cn.hongjitech.vehicle.parameter;

/**
 * 科目二相关参数配置
 * 长度单位:m
 * Created by jiaop on 2016/9/8.
 */
public class BParameter {

    /**
     * 判定延迟
     */
    public static int determinationDelay = 100;

    /**
     * 是否开始
     */
    public static boolean ifStart = false;

    /**
     * 触发的区域
     */
    public static String areaName;

    /**
     * 车辆后退距离
     */
    public static int retreatLength = 0;

    /**
     * 是否按规定路线行驶
     */
    public static boolean underTheExercise = false;

    /**
     * 停止后车身是否出线
     */
    public static boolean outLetForStop = false;

    /**
     * 行驶中车身是否出线
     */
    public static boolean outLetForTravel = false;

    /**
     * 是否倒库不入
     */
    public static boolean nothingDownLibraries = false;

    /**
     * 中途停车
     */
    public static boolean midwayParking = false;

    /**
     * 倒车入库是否超时
     */
    public static boolean DCRKTimeOut = false;

    /**
     * 车辆前保险杠距离桩杆线的长度
     */
    public static int headForLine = 0;

    /**
     * 起步时间超过规定时间
     */
    public static boolean whetherOvertimeSPQB = false;

    /**
     * 车身右侧距离边缘线长度
     */
    public static int pressRightEdgeLine = 0;

    /**
     * 车辆是否减速
     */
    public static boolean slowDown = false;

    /**
     * 是否行驶出隧道
     */
    public static boolean outSD = false;

    /**
     * 驶抵隧道出口的距离
     */
    public static int tailLength = 0;

    /**
     * 急加速
     */
    public static boolean rapidAcceleration = false;

    /**
     * 急减速
     */
    public static boolean rapidDeceleration = false;

}
