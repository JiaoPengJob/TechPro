package cn.hongjitech.vehicle.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.hongjitech.vehicle.activity.MainActivity;

/**
 * 开机自启动广播
 */
public class StartUpReceiver extends BroadcastReceiver {

    public StartUpReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent2 = new Intent(context, MainActivity.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent2);
    }

}
