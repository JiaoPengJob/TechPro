package cn.hongjitech.vehicle.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.javaBean.InitializationRoot;
import cn.hongjitech.vehicle.util.FilesUtil;
import cn.hongjitech.vehicle.util.SharedPrefsUtils;
import cn.hongjitech.vehicle.util.StringUtils;
import cn.hongjitech.vehicle.volleyHttp.VolleyManager;

/**
 * 启动;主页面
 */
public class MainActivity extends BaseActivity {

    @InjectView(R.id.iv_main_exam)
    ImageView iv_main_exam;//考试
    @InjectView(R.id.iv_main_train)
    ImageView iv_main_train;//训练
    @InjectView(R.id.iv_main_test)
    ImageView iv_main_test;//测试
    @InjectView(R.id.iv_main_exit)
    ImageView iv_main_exit;//退出

    /*--------------------------------*/
    private Intent intent;//跳转Intent
    private AlertDialog alertDialog;//dialog对象
    private ImageView iv_dialog_main_exit;
    private ImageView iv_dialog_main_sure;
    private EditText et_dialog_main_pwd;

    private String url = "http://examination.91vh.com/api/initialization";
    private InitializationRoot initializationRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //activity注入ButterKnife
        ButterKnife.inject(MainActivity.this);
        initListener();

    }

    /**
     * 加载点击事件监听函数
     */
    private void initListener() {
        iv_main_exam.setOnClickListener(new ClickListener());
        iv_main_train.setOnClickListener(new ClickListener());
        iv_main_test.setOnClickListener(new ClickListener());
        iv_main_exit.setOnClickListener(new ClickListener());
    }

    /**
     * 声明点击事件监听类
     */
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_main_exam://考试
                    if (getCarNum() != null && getDeviceID() != null) {
                        if (StringUtils.isNetworkConnected(MainActivity.this)) {
                            getInitialization();
                        } else {
                            Toast.makeText(MainActivity.this, "当前网络不佳!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "未查到车辆信息!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.iv_main_train://训练
                    intent = new Intent(MainActivity.this, TrainingActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                    break;
                case R.id.iv_main_test://测试
                    intent = new Intent(MainActivity.this, TestDrivesActivity.class);
                    startActivity(intent);
                    MainActivity.this.finish();
                    break;
                case R.id.iv_main_exit://退出
                    showDialogForMain();
                    break;
                case R.id.iv_dialog_main_exit://dialog的取消
                    alertDialog.cancel();
                    break;
                case R.id.iv_dialog_main_sure://dialog的确定
                    //65786974415050
                    if (et_dialog_main_pwd.getText().toString().equals("exit")) {
                        alertDialog.cancel();
                        SharedPrefsUtils.clear(MainActivity.this);
                        Intent intent = new Intent();
                        intent.setAction(BaseActivity.EXIT_APP_ACTION);
                        sendBroadcast(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "密码输入错误!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.et_dialog_main_pwd://dialog的输入框
                    et_dialog_main_pwd.setFocusable(true);
                    et_dialog_main_pwd.setFocusableInTouchMode(true);
                    break;
            }
        }
    }

    /**
     * 设置退出显示的窗口提醒
     */
    private void showDialogForMain() {
        View view = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_main, null);
        alertDialog = new AlertDialog.Builder(this).setTitle(null).setMessage(null).setView(view).create();
        et_dialog_main_pwd = (EditText) view.findViewById(R.id.et_dialog_main_pwd);
        iv_dialog_main_exit = (ImageView) view.findViewById(R.id.iv_dialog_main_exit);
        iv_dialog_main_sure = (ImageView) view.findViewById(R.id.iv_dialog_main_sure);
        et_dialog_main_pwd.setOnClickListener(new ClickListener());
        iv_dialog_main_exit.setOnClickListener(new ClickListener());
        iv_dialog_main_sure.setOnClickListener(new ClickListener());
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
    }

    /**
     * 访问服务器获取初始化信息
     */
    private void getInitialization() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("car_num", getCarNum());
            jsonObject.put("device_id", getDeviceID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        VolleyManager.newInstance().PostjsonRequest("TAG", url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if (jsonObject != null) {
                            Gson gson = new Gson();
                            initializationRoot = gson.fromJson(jsonObject.toString(), InitializationRoot.class);
                            if (initializationRoot.getResult().equals("succeed")) {
                                intent = new Intent(MainActivity.this, StuApproveActivity.class);
                                intent.putExtra("car_num", getCarNum());
//                                intent.putExtra("token",initializationRoot.getData().getToken());
                                SharedPrefsUtils.putValue(MainActivity.this, "token", initializationRoot.getData().getToken());
                                startActivity(intent);
                                MainActivity.this.finish();
                            } else {
                                Log.e("Main", initializationRoot.getErr_message());
                                Toast.makeText(MainActivity.this, initializationRoot.getErr_message(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage(), error);
                    }
                });
    }

    /**
     * 读取保存的车牌号
     *
     * @return
     */
    private String getCarNum() {
        try {
            return FilesUtil.readFiles(MainActivity.this, "carCode");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取保存的车牌id
     *
     * @return
     */
    private String getDeviceID() {
        try {
            return FilesUtil.readFiles(MainActivity.this, "deviceId");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
