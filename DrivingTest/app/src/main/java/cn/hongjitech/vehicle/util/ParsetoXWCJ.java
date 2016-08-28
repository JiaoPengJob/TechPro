package cn.hongjitech.vehicle.util;

import android.content.Context;
import android.util.Log;

import cn.hongjitech.vehicle.map.TCPClient;
import cn.hongjitech.vehicle.map.TcpPresenter;

/**
 * 发送车身信号数据到练一练
 */
public class ParsetoXWCJ {

    //$XWCJ,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0*07安全带未系状态，系统会报语音提示。

    Context context;
    XGYDSerialPortSendUtil xgydSerialPortSendUtil;
//    private TcpPresenter tp = TcpPresenter.getInstance();

    String anquandai, menkong, mingdi, yushua, dangweiD1 = "0", dangweiD2 = "0", dangweiD3 = "0",
            dangweiD4 = "0", dangwei, beiyong1 = "0", beiyong2 = "0", beiyong3 = "0", beiyong4 = "0", beiyong5 = "0",
            dianhuo, zuozhuandeng, youzhuandeng, jiaosha, xiaodeng, yuanguang, jinguang, wudeng;

    public ParsetoXWCJ(Context context) {
        this.context = context;
//        xgydSerialPortSendUtil = new XGYDSerialPortSendUtil();
    }

    public String getXWCJ() {
        String xwcj = "";

//        dangwei = SharedPrefsUtils.getValue(context, "bf_gear_info", "0");
        //档位
        if (SharedPrefsUtils.getValue(context, "bf_gear_info", "0").equals("0")) {
            dangwei = "0";
        } else if (SharedPrefsUtils.getValue(context, "bf_gear_info", "0").equals("7")) {
            dangwei = "6";
        } else {
            dangwei = SharedPrefsUtils.getValue(context, "bf_gear_info", "0");
        }

        //雨刷
        if (SharedPrefsUtils.getValue(context, "bf_wiper", "").equals("87")) {
            yushua = "1";
        } else {
            yushua = "0";
        }

        //点火2
        if (SharedPrefsUtils.getValue(context, "bf_engine_status", "").equals("00")) {
            dianhuo = "1";
        } else {
            dianhuo = "0";
        }

        //喇叭
        if (SharedPrefsUtils.getValue(context, "bf_speaker", "").equals("C1")) {
            mingdi = "1";
        } else {
            mingdi = "0";
        }

        //制动器
        if (SharedPrefsUtils.getValue(context, "bf_brake_status", "").equals("01")) {
            jiaosha = "1";
        } else {
            jiaosha = "0";
        }

        //车门
        if (SharedPrefsUtils.getValue(context, "bf_door", "").equals("FB")) {
            menkong = "0";
        } else {
            menkong = "1";
        }

        //安全带
        if (SharedPrefsUtils.getValue(context, "bf_belt_info", "").equals("40")) {
            anquandai = "0";
        } else {
            anquandai = "1";
        }

        //视宽灯
        if (SharedPrefsUtils.getValue(context, "bf_position_light", "").equals("01")) {
            xiaodeng = "1";
        } else {
            xiaodeng = "0";
        }

        //左转灯
        if (SharedPrefsUtils.getValue(context, "bf_left_turn_light", "").equals("01")) {
            zuozhuandeng = "1";
        } else {
            zuozhuandeng = "0";
        }

        //右转灯
        if (SharedPrefsUtils.getValue(context, "bf_right_turn_light", "").equals("01")) {
            youzhuandeng = "1";
        } else {
            youzhuandeng = "0";
        }

        //近光灯
        if (SharedPrefsUtils.getValue(context, "bf_dipped_headlight", "").equals("01")) {
            jinguang = "1";
        } else {
            jinguang = "0";
        }

        //远光灯
        if (SharedPrefsUtils.getValue(context, "bf_high_beam_light", "").equals("01")) {
            yuanguang = "1";
        } else {
            yuanguang = "0";
        }

        //雾灯
        if (SharedPrefsUtils.getValue(context, "bf_foglight", "").equals("01")) {
            wudeng = "1";
        } else {
            wudeng = "0";
        }

        StringBuffer sb = new StringBuffer();
        sb.append("$XWCJ,")
                .append(anquandai + ",")
                .append(menkong + ",")
                .append(mingdi + ",")
                .append(yushua + ",")
                .append(dangweiD1 + ",")
                .append(dangweiD2 + ",")
                .append(dangweiD3 + ",")
                .append(dangweiD4 + ",")
                .append(dangwei + ",")
                .append(beiyong1 + ",")
                .append(beiyong2 + ",")
                .append(beiyong3 + ",")
                .append(beiyong4 + ",")
                .append(beiyong5 + ",")
                .append(dianhuo + ",")
                .append(zuozhuandeng + ",")
                .append(youzhuandeng + ",")
                .append(jiaosha + ",")
                .append(xiaodeng + ",")
                .append(yuanguang + ",")
                .append(jinguang + ",")
                .append(wudeng + "*FF");
//                .append(wudeng + "*" + getCode(sb.toString()));

//        Log.e("TAGUU", sb.toString());
        xgydSerialPortSendUtil = new XGYDSerialPortSendUtil();
        if (sb != null) {
//            tp.sendMsgToTcp(sb.toString());
            xgydSerialPortSendUtil.sendMessage(sb.toString().getBytes());
        }
        return null;
    }

    /**
     * 校验
     *
     * @param str
     * @return
     */
    private String getCode(String str) {
        int result = 0;
        String[] s = str.split(",");
        for (int i = 1; i < 22; i++) {
            result ^= Integer.parseInt(s[i]);
        }
        result = result ^ Integer.parseInt(wudeng);

        String res = String.valueOf(result);
        if (res.length() == 1) {
            res = "0" + res;
        }
        return res;
    }
}
