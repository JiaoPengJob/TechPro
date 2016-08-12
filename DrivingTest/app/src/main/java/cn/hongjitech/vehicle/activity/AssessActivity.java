package cn.hongjitech.vehicle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.adapter.MarkingAdapter;
import cn.hongjitech.vehicle.bean.MarkingBean;
import cn.hongjitech.vehicle.bean.UserInfo;
import cn.hongjitech.vehicle.javaBean.User;

public class AssessActivity extends BaseActivity {

    @InjectView(R.id.lv_assess_info)
    ListView lv_assess_info;//扣分项目信息列表
    @InjectView(R.id.iv_assess_return)
    ImageView iv_assess_return;//重新考核
    @InjectView(R.id.iv_assess_commend)
    ImageView iv_assess_commend;//继续训练
    @InjectView(R.id.iv_assess_over)
    ImageView iv_assess_over;//结束
    @InjectView(R.id.tv_assess_user_name)
    TextView tv_assess_user_name;//学员姓名
    @InjectView(R.id.tv_assess_user_idCode)
    TextView tv_assess_user_idCode;//身份证
    @InjectView(R.id.tv_assess_user_examTime)
    TextView tv_assess_user_examTime;//时间段
    @InjectView(R.id.tv_assess_user_useTime)
    TextView tv_assess_user_useTime;//用时
    @InjectView(R.id.tv_assess_user_num)
    TextView tv_assess_user_num;//扣分次数
    @InjectView(R.id.tv_assess_user_length)
    TextView tv_assess_user_length;//行驶长度
    @InjectView(R.id.iv_assess_img_enjoy)
    ImageView iv_assess_img_enjoy;//表情图片
    @InjectView(R.id.tv_assess_text_enjoy)
    TextView tv_assess_text_enjoy;//考核是否通过信息提示

    /*--------------------------------*/
    private Intent intent;//跳转Intent对象
    private List<MarkingBean> markingBeens;//扣分信息列表
    private UserInfo userInfo;//学员考试的数据
    private User user = new User();//用户对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_assess);
        ButterKnife.inject(AssessActivity.this);
        initListener();
        initData();
    }

    /**
     * 加载列表数据
     */
    private void initData() {
        if (getIntent().getSerializableExtra("userInfo") != null && getIntent().getSerializableExtra("user") != null) {
            userInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");
            user = (User) getIntent().getSerializableExtra("user");
            tv_assess_user_name.setText(userInfo.getName());
            tv_assess_user_idCode.setText(userInfo.getIdCode());
            tv_assess_user_examTime.setText(userInfo.getStartTime() + " - " + userInfo.getEndTime());
            tv_assess_user_useTime.setText(userInfo.getUserTime());
            tv_assess_user_num.setText(userInfo.getNum());
            tv_assess_user_length.setText(userInfo.getLength());
            lv_assess_info.setAdapter(new MarkingAdapter(AssessActivity.this, userInfo.getMarkingBean()));
            judgmentFraction();
        }
    }

    /**
     * 判断分数
     */
    private void judgmentFraction() {
        int fraction = 0;
        for (MarkingBean markingBean : userInfo.getMarkingBean()) {
            fraction += Integer.parseInt(markingBean.getMarkFraction());
        }
        if (fraction < 20) {
            iv_assess_img_enjoy.setImageResource(R.drawable.test_success);
            tv_assess_text_enjoy.setText("恭喜您!考核合格!");
        } else {
            iv_assess_img_enjoy.setImageResource(R.drawable.assess_fail);
            tv_assess_text_enjoy.setText("很抱歉!考核不合格!");
        }
    }

    /**
     * 加载点击事件监听函数
     */
    private void initListener() {
        iv_assess_return.setOnClickListener(new ClickListener());
        iv_assess_commend.setOnClickListener(new ClickListener());
        iv_assess_over.setOnClickListener(new ClickListener());
    }

    /**
     * 声明点击事件监听类
     */
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_assess_return://重新考核
                    intent = new Intent(AssessActivity.this, ExamProcessActivity.class);
                    intent.putExtra("activity", "training");
                    intent.putExtra("user", user);
                    startActivity(intent);
                    AssessActivity.this.finish();
                    break;
                case R.id.iv_assess_commend://继续训练
                    intent = new Intent(AssessActivity.this, ExamProcessActivity.class);
                    intent.putExtra("activity", "trainTest");
                    intent.putExtra("user", user);
                    startActivity(intent);
                    AssessActivity.this.finish();
                    break;
                case R.id.iv_assess_over://结束
                    intent = new Intent(AssessActivity.this, SignatureActivity.class);
                    intent.putExtra("activity", "training");
                    intent.putExtra("userInfo", userInfo);
                    intent.putExtra("user", user);
                    startActivity(intent);
                    AssessActivity.this.finish();
                    break;
            }
        }
    }
}
