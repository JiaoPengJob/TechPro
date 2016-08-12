package cn.hongjitech.vehicle.util;

import android.util.Log;

/**
 * 处理接收到的串口协议,进行判断,返回相应状态
 */
public class JudgmentSerialPort {

    public static String returnJudgment(String parameter, int type) {
        String result = "";
        if (parameter != null && !parameter.equals("")) {

            switch (type) {
                case 10://制动器状态
                    if (parameter == "00") {
                        result = "关";
                    } else {
                        result = "开";
                    }
                    break;

                case 12://安全带
                    if (parameter == "00") {
                        result = "未扣上";
                    } else {
                        result = "已扣上";
                    }
                    break;

                case 13://雨刮
                    if (parameter == "88") {
                        result = "关";
                    } else {
                        result = "开";
                    }
                    break;

                case 14://喇叭
                    if (parameter == "A7") {
                        result = "关";
                    } else {
                        result = "开";
                    }
                    break;

                case 15://车门

                    break;

                case 16://报警灯
                    if (parameter == "00") {
                        result = "未激活";
                    } else {
                        result = "已激活";
                    }
                    break;

                case 17://视宽灯

                    break;

                case 18://左转灯
                    if (parameter == "00") {
                        result = "未激活";
                    } else {
                        result = "已激活";
                    }
                    break;

                case 19://右转灯
                    if (parameter == "00") {
                        result = "未激活";
                    } else {
                        result = "已激活";
                    }
                    break;

                case 20://远光灯
                    if (parameter == "00") {
                        result = "未激活";
                    } else {
                        result = "已激活";
                    }
                    break;

                case 21://离合
                    if (parameter == "00") {
                        result = "离合器未踩下";
                    } else {
                        result = "离合器已踩下";
                    }
                    break;

                case 22://手刹
                    if (parameter == "00") {
                        result = "手刹未拉起";
                    } else {
                        result = "手刹已拉起";
                    }
                    break;

                case 23://点火
                    if (parameter == "00") {
                        result = "熄火";
                    } else if (parameter == "01") {
                        result = "上电";
                    } else if (parameter == "02") {
                        result = "怠速";
                    } else {
                        result = "行驶";
                    }
                    break;

                case 24://副刹车

                    break;

                case 25://雾灯

                    break;

                case 26://近光灯

                    break;

                case 27://左后视灯

                    break;

                case 28://右后视灯

                    break;

                case 29://中央后视灯

                    break;

                case 30://座椅调整

                    break;
            }
            return result;
        } else {
            Log.e("Error", "returnJudgment()的参数为null");
            result = "Error for JudgmentSerialPort returnJudgment function";
            return result;
        }
    }

}
