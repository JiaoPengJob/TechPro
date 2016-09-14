package cn.hongjitech.vehicle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;

public class TestActivity extends BaseActivity {

    @InjectView(R.id.bt_carInfoTest)
    Button bt_carInfoTest;
    @InjectView(R.id.bt_serialportTest)
    Button bt_serialportTest;
    @InjectView(R.id.bt_examInfoTest)
    Button bt_examInfoTest;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test);
        ButterKnife.inject(TestActivity.this);
        initListener();
    }

    /**
     * 加载点击事件监听函数
     */
    private void initListener() {
        bt_carInfoTest.setOnClickListener(new ClickListener());
        bt_serialportTest.setOnClickListener(new ClickListener());
        bt_examInfoTest.setOnClickListener(new ClickListener());
    }

    /**
     * 声明点击事件监听类
     */
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_carInfoTest:
                    intent = new Intent(TestActivity.this, CarDataActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bt_serialportTest:
                    intent = new Intent(TestActivity.this, TestDrivesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.bt_examInfoTest:

                    break;
            }
        }
    }
}
