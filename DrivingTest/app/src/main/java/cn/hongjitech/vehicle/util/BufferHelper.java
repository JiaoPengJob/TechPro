package cn.hongjitech.vehicle.util;

import android.content.Context;

import cn.hongjitech.vehicle.javaBean.SerialPortBean;

/**
 * 处理串口buffer数据
 */
public class BufferHelper {

    public static SerialPortBean commitBuffer(Context context, byte[] bytes) {
        String stmp, newStr, rpm = "", six = "", sev = "", eig = "", nin = "", ultrasonic_1 = "", ultrasonic_2 = "", ultra_31 = "", ultra_32 = "", ultra_33 = "", ultra_34 = "";
        SerialPortBean spb = new SerialPortBean();
        for (int n = 0; n < bytes.length; n++) {
            stmp = Integer.toHexString(bytes[n] & 0xFF);
            newStr = (stmp.length() == 1) ? "0" + stmp : stmp;
            newStr = newStr.toUpperCase();
            switch (n) {
                //转速
                case 6:
                    six = newStr;
                    break;
                case 7:
                    sev = newStr;
                    break;
                case 8:
                    eig = newStr;
                    break;
                case 9:
                    nin = newStr;
                    break;
                //超声波距离信息
                case 31:
                    ultra_31 = newStr;
                    break;
                case 32:
                    ultra_32 = newStr;
                    break;
                case 33:
                    ultra_33 = newStr;
                    break;
                case 34:
                    ultra_34 = newStr;
                    break;
                //车身其他信息
                case 5:
                    //车速
                    spb.setBf_speed(String.valueOf(Integer.parseInt(newStr, 16)));
                    SharedPrefsUtils.putValue(context, "bf_speed", String.valueOf(Integer.parseInt(newStr, 16)));
                    break;
                case 10:
                    //制动器状态
                    spb.setBf_brake_status(newStr);
                    SharedPrefsUtils.putValue(context, "bf_brake_status", newStr);
                    break;
                case 11:
                    //档位
                    SharedPrefsUtils.putValue(context, "bf_gear_info", String.valueOf(Integer.parseInt(newStr, 16)));
                    spb.setBf_gear_info(String.valueOf(Integer.parseInt(newStr, 16)));
                    break;
                case 12:
                    //安全带
                    SharedPrefsUtils.putValue(context, "bf_belt_info", newStr);
                    spb.setBf_belt_info(newStr);
                    break;
                case 13:
                    //雨刮
                    SharedPrefsUtils.putValue(context, "bf_wiper", newStr);
                    spb.setBf_wiper(newStr);
                    break;
                case 14:
                    //喇叭
                    SharedPrefsUtils.putValue(context, "bf_speaker", newStr);
                    spb.setBf_speaker(newStr);
                    break;
                case 15:
                    //车门
                    SharedPrefsUtils.putValue(context, "bf_door", newStr);
                    spb.setBf_door(newStr);
                    break;
                case 16:
                    //报警灯
                    SharedPrefsUtils.putValue(context, "bf_warning_light", newStr);
                    spb.setBf_warning_light(newStr);
                    break;
                case 17:
                    //示宽灯
                    SharedPrefsUtils.putValue(context, "bf_position_light", newStr);
                    spb.setBf_position_light(newStr);
                    break;
                case 18:
                    //左转灯
                    SharedPrefsUtils.putValue(context, "bf_left_turn_light", newStr);
                    spb.setBf_left_turn_light(newStr);
                    break;
                case 19:
                    //右转灯
                    SharedPrefsUtils.putValue(context, "bf_right_turn_light", newStr);
                    spb.setBf_right_turn_light(newStr);
                    break;
                case 20:
                    //远光灯
                    SharedPrefsUtils.putValue(context, "bf_high_beam_light", newStr);
                    spb.setBf_high_beam_light(newStr);
                    break;
                case 21:
                    //离合
                    SharedPrefsUtils.putValue(context, "bf_clutch", newStr);
                    spb.setBf_clutch(newStr);
                    break;
                case 22:
                    //手刹
                    SharedPrefsUtils.putValue(context, "bf_hand_brake", newStr);
                    spb.setBf_hand_brake(newStr);
                    break;
                case 23:
                    //点火
                    SharedPrefsUtils.putValue(context, "bf_engine_status", newStr);
                    spb.setBf_engine_status(newStr);
                    break;
                case 24:
                    //副刹车
                    SharedPrefsUtils.putValue(context, "bf_assistant_brake_status", newStr);
                    spb.setBf_assistant_brake_status(newStr);
                    break;
                case 25:
                    //雾灯
                    SharedPrefsUtils.putValue(context, "bf_foglight", newStr);
                    spb.setBf_foglight(newStr);
                    break;
                case 26:
                    //近光灯
                    SharedPrefsUtils.putValue(context, "bf_dipped_headlight", newStr);
                    spb.setBf_dipped_headlight(newStr);
                    break;
                case 27:
                    //左后视镜
                    SharedPrefsUtils.putValue(context, "bf_left_rearview_mirror", newStr);
                    spb.setBf_left_rearview_mirror(newStr);
                    break;
                case 28:
                    //右后视镜
                    SharedPrefsUtils.putValue(context, "bf_right_rearview_mirror", newStr);
                    spb.setBf_right_rearview_mirror(newStr);
                    break;
                case 29:
                    //中央后视镜
                    SharedPrefsUtils.putValue(context, "bf_central_rearview_mirror", newStr);
                    spb.setBf_central_rearview_mirror(newStr);
                    break;
                case 30:
                    //座椅调整
                    SharedPrefsUtils.putValue(context, "bf_seat_adjustment", newStr);
                    spb.setBf_seat_adjustment(newStr);
                    break;
            }

            rpm = nin + eig + sev + six;
            if (rpm.length() >= 8) {
                SharedPrefsUtils.putValue(context, "bf_rpm", String.valueOf(Integer.parseInt(rpm, 16)));
                spb.setBf_rpm(String.valueOf(Integer.parseInt(rpm, 16)));
            }

            ultrasonic_1 = ultra_32 + ultra_31;
            if (ultrasonic_1.length() >= 4) {
                SharedPrefsUtils.putValue(context, "bf_ultrasonic_1", String.valueOf(Integer.parseInt(ultrasonic_1, 16)));
                spb.setBf_ultrasonic_1(String.valueOf(Integer.parseInt(ultrasonic_1, 16)));
            }
            ultrasonic_2 = ultra_34 + ultra_33;
            if (ultrasonic_2.length() >= 4) {
                SharedPrefsUtils.putValue(context, "bf_ultrasonic_2", String.valueOf(Integer.parseInt(ultrasonic_2, 16)));
                spb.setBf_ultrasonic_2(String.valueOf(Integer.parseInt(ultrasonic_2, 16)));
            }

        }
        return spb;
    }

}
