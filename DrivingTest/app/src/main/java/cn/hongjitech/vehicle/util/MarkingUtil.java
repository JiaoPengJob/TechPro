package cn.hongjitech.vehicle.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

import cn.hongjitech.vehicle.bean.MarkingBean;

/**
 * 处理扣分情况类
 */
public class MarkingUtil {

    MarkingBean markingBean;
    String proType;

    public static MarkingBean markingFuction(String type) {
        switch (type) {
            case "":

                break;
        }
        return null;
    }

    /**
     * 科目2
     *
     * @param context
     * @return
     */
    public MarkingBean cunnectFuction(final Context context) {
        if (SharedPrefsUtils.getValue(context, "bf_belt_info", "").equals("00")) {
            return new MarkingBean("不合格情形", "100", "不按规定使用安全带或者戴安全头盔",null);
        }

        if (SharedPrefsUtils.getValue(context, "bf_engine_status", "").equals("00")) {
            return new MarkingBean("不合格情形", "10", "因操作不当造成发动机熄火一次",null);
        }

        if (SharedPrefsUtils.getValue(context, "bf_speed", "").equals("0")) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (SharedPrefsUtils.getValue(context, "bf_speed", "").equals("0")) {
                        switch (proType) {
                            case "倒车入库":
                                markingBean = new MarkingBean("倒车入库", "100", "中途停车",null);
                                break;

                        }

                    }
                }
            }, 0, 2000);
        }


        return null;
    }

}
