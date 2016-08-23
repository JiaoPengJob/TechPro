package cn.hongjitech.vehicle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.adapter.MarkingAdapter;
import cn.hongjitech.vehicle.bean.MarkingBean;

public class TestActivity extends BaseActivity {

    @InjectView(R.id.lv_test_info)
    ListView lv_test_info;//扣分列表信息
    @InjectView(R.id.iv_test_face)
    ImageView iv_test_face;//成功与否的表情图片
    @InjectView(R.id.iv_test_makeup)
    ImageView iv_test_makeup;//补考
    @InjectView(R.id.iv_test_over)
    ImageView iv_test_over;//结束

    /*----------------------------------*/
    private List<MarkingBean> markingBeens;
    private Intent intent;//跳转Intent对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_test);
        ButterKnife.inject(TestActivity.this);
        initListener();
        initData();
    }

    /**
     * 加载列表数据
     */
    private void initData() {

        markingBeens = new ArrayList<MarkingBean>();
        for (int i = 0; i < 3; i++) {
            markingBeens.add(new MarkingBean("起步" + i, "1" + i, "操作不当发动机熄火" + i,null));
        }
        lv_test_info.setAdapter(new MarkingAdapter(TestActivity.this, markingBeens));

    }

    /**
     * 加载点击事件监听函数
     */
    private void initListener() {
        iv_test_makeup.setOnClickListener(new ClickListener());
        iv_test_over.setOnClickListener(new ClickListener());
    }

    /**
     * 声明点击事件监听类
     */
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_test_makeup://补考
                    intent = new Intent(TestActivity.this, ExamProcessActivity.class);
                    startActivity(intent);
                    TestActivity.this.finish();
                    break;
                case R.id.iv_test_over://结束
                    intent = new Intent(TestActivity.this, StuApproveActivity.class);
                    startActivity(intent);
                    TestActivity.this.finish();
                    break;
            }
        }
    }
}
