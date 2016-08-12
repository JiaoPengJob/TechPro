package cn.hongjitech.vehicle.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.javaBean.CarInfoRoot;
import cn.hongjitech.vehicle.javaBean.DeviceRoot;
import cn.hongjitech.vehicle.util.FilesUtil;
import cn.hongjitech.vehicle.util.StringUtils;
import cn.hongjitech.vehicle.volleyHttp.VolleyManager;

/**
 * 输入车牌号成功后,从服务器获取设备id,保存至文件中,每次考试或考核时先进行验证
 */
public class CarDataActivity extends AppCompatActivity {

    @InjectView(R.id.et_car_data_cardID)
    EditText et_car_data_cardID;//车牌输入框
    @InjectView(R.id.iv_car_data_exit)
    ImageView iv_car_data_exit;//退出
    @InjectView(R.id.iv_car_data_save)
    ImageView iv_car_data_save;//保存
    @InjectView(R.id.iv_car_data_test)
    ImageView iv_car_data_test;//测试
    @InjectView(R.id.iv_car_data_sure)
    ImageView iv_car_data_sure;//确定
    @InjectView(R.id.tv_car_data_content)
    TextView tv_car_data_content;//显示写入成功后的内容
    @InjectView(R.id.tv_car_device_id)
    TextView tv_car_device_id;//显示设备id
    @InjectView(R.id.tv_car_id)
    TextView tv_car_id;//显示车辆id

    private Intent intent;
    private String url = "http://backend.dev.hongjitech.cn/api/android/vehicleId/";
    private String carIdUrl = "http://backend.dev.hongjitech.cn/api/android/vehicle/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_car_data);
        ButterKnife.inject(CarDataActivity.this);
        initListener();
    }

    /**
     * 加载点击事件监听函数
     */
    private void initListener() {
        iv_car_data_exit.setOnClickListener(new ClickListener());
        iv_car_data_save.setOnClickListener(new ClickListener());
        iv_car_data_test.setOnClickListener(new ClickListener());
        iv_car_data_sure.setOnClickListener(new ClickListener());
    }

    /**
     * 声明点击事件监听类
     */
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_car_data_save://保存
                    if (!et_car_data_cardID.getText().toString().equals("")) {
                        int index = FilesUtil.writeFiles(CarDataActivity.this, "carCode", et_car_data_cardID.getText().toString(), Context.MODE_PRIVATE);
                        if (index == 1) {
                            Toast.makeText(CarDataActivity.this, "车牌号保存成功!", Toast.LENGTH_SHORT).show();
                            if (StringUtils.isNetworkConnected(CarDataActivity.this)) {
                                getAdviceID();
                                getCarID();
                            } else {
                                Toast.makeText(CarDataActivity.this, "当前网络不佳!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CarDataActivity.this, "车牌号保存失败!请重试!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case R.id.iv_car_data_exit://退出
                    CarDataActivity.this.finish();
                    break;
                case R.id.iv_car_data_test://测试
                    try {
                        tv_car_data_content.setText(FilesUtil.readFiles(CarDataActivity.this, "carCode"));
                        tv_car_device_id.setText(FilesUtil.readFiles(CarDataActivity.this, "deviceId"));
                        tv_car_id.setText(FilesUtil.readFiles(CarDataActivity.this, "carId"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (TextUtils.isEmpty(tv_car_data_content.getText()) || TextUtils.isEmpty(tv_car_device_id.getText())) {
                        Toast.makeText(CarDataActivity.this, "数据未保存成功!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.iv_car_data_sure://确定
                    intent = new Intent(CarDataActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    /**
     * 访问服务器获取设备id
     */
    private void getAdviceID() {
        VolleyManager.newInstance().GsonGetRequest("TAG", url + et_car_data_cardID.getText().toString(), DeviceRoot.class,
                new Response.Listener<DeviceRoot>() {
                    @Override
                    public void onResponse(DeviceRoot deviceRoot) {
                        if (deviceRoot.getData() != null) {
                            int index = FilesUtil.writeFiles(CarDataActivity.this, "deviceId", deviceRoot.getData(), Context.MODE_PRIVATE);
                            if (index == 1) {
                                Toast.makeText(CarDataActivity.this, "车辆标识码保存成功!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CarDataActivity.this, "车辆标识码保存失败!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CarDataActivity.this, "设备标识码获取失败!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                });
    }

    /**
     * 访问服务器获取车辆id
     */
    private void getCarID() {
        VolleyManager.newInstance().GsonGetRequest("TAG", carIdUrl + et_car_data_cardID.getText().toString(), CarInfoRoot.class,
                new Response.Listener<CarInfoRoot>() {
                    @Override
                    public void onResponse(CarInfoRoot carInfoRoot) {
                        if (carInfoRoot.getData() != null) {
                            int index = FilesUtil.writeFiles(CarDataActivity.this, "carId", String.valueOf(carInfoRoot.getData().getId()), Context.MODE_PRIVATE);
                            if (index == 1) {
                                Toast.makeText(CarDataActivity.this, "车辆id保存成功!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CarDataActivity.this, "车辆id保存失败!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CarDataActivity.this, "车辆id获取失败!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                });
    }

}
