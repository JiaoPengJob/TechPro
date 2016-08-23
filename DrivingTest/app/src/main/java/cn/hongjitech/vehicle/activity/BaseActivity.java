package cn.hongjitech.vehicle.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bugtags.library.Bugtags;

import cn.hongjitech.vehicle.receiver.ExitAppReceiver;

/**
 * activity的基类
 */
public class BaseActivity extends Activity {

    private ExitAppReceiver exitReceiver = new ExitAppReceiver();
    public static String EXIT_APP_ACTION = "cn.hongjitech.vehicle.activity";
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(0x80000000, 0x80000000);//关键代码
        window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        window.setAttributes(params);
        registerExitReceiver();
    }

    private void registerExitReceiver() {
        IntentFilter exitFilter = new IntentFilter();
        exitFilter.addAction(EXIT_APP_ACTION);
        registerReceiver(exitReceiver, exitFilter);
    }

    private void unRegisterExitReceiver() {
        unregisterReceiver(exitReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterExitReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    /**
     * 返回键监听事件
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            return false;
        }
        return false;
    }
}
