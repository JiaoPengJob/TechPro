package cn.hongjitech.vehicle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.bean.MarkingBean;

/**
 * 人工扣分显示的dialog窗口
 */
public class ArtificialPointsDialogActivity extends BaseActivity {

    @InjectView(R.id.ib_dialog_artPoi_back)
    ImageButton ib_dialog_artPoi_back;//后退
    @InjectView(R.id.ib_dialog_artPoi_intoPro)
    ImageButton ib_dialog_artPoi_intoPro;//进入扣分项目
    @InjectView(R.id.ib_dialog_artPoi_intoCode)
    ImageButton ib_dialog_artPoi_intoCode;//进入扣分代码
    @InjectView(R.id.iv_dialog_artPoi_exit)
    ImageView iv_dialog_artPoi_exit;//取消
    @InjectView(R.id.iv_dialog_artPoi_sure)
    ImageView iv_dialog_artPoi_sure;//确定
    @InjectView(R.id.et_dialog_artPoi_markPro)
    EditText et_dialog_artPoi_markPro;//文本输入框
    @InjectView(R.id.rl_dialog_artPoi_intoPro)
    RelativeLayout rl_dialog_artPoi_intoPro;//整行选中项目
    @InjectView(R.id.rl_dialog_artPoi_intoCode)
    RelativeLayout rl_dialog_artPoi_intoCode;//整行选中代码
    @InjectView(R.id.tv_dialog_artPoi_pro)
    TextView tv_dialog_artPoi_pro;//显示扣分项目的文本
    @InjectView(R.id.tv_dialog_artPoi_code)
    TextView tv_dialog_artPoi_code;//显示扣分代码的文本
    @InjectView(R.id.tv_dialog_artPoi_point)
    TextView tv_dialog_artPoi_point;//显示扣分分值
    /*--------------------*/
    private Intent intent;//跳转
    private String proString;//扣分项目信息
    private String codeString;//扣分代码内容
    private String fraction;//扣分分数
    private String code;//扣分代码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_activity_artificial_points);
        installWindowPara();
        ButterKnife.inject(ArtificialPointsDialogActivity.this);
        initListener();
        setFinishOnTouchOutside(false);
    }


    /**
     * 设置窗口参数
     */
    private void installWindowPara() {
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        //设置窗口的大小及透明度
        layoutParams.width = 600;
        layoutParams.height = 700;
        layoutParams.alpha = 1.0f;
        window.setAttributes(layoutParams);
    }

    /**
     * 加载点击事件监听函数
     */
    private void initListener() {
        ib_dialog_artPoi_back.setOnClickListener(new ClickListener());
        ib_dialog_artPoi_intoPro.setOnClickListener(new ClickListener());
        ib_dialog_artPoi_intoCode.setOnClickListener(new ClickListener());
        iv_dialog_artPoi_exit.setOnClickListener(new ClickListener());
        iv_dialog_artPoi_sure.setOnClickListener(new ClickListener());
        et_dialog_artPoi_markPro.setOnClickListener(new ClickListener());
        rl_dialog_artPoi_intoCode.setOnClickListener(new ClickListener());
        rl_dialog_artPoi_intoPro.setOnClickListener(new ClickListener());
    }

    /**
     * 声明点击事件监听类
     */
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_dialog_artPoi_back://后退
                    ArtificialPointsDialogActivity.this.finish();
                    ArtificialPointsDialogActivity.this.overridePendingTransition(R.anim.dialogactivity_artpoi, R.anim.dialogactivity_artpoi_exit);
                    break;
                case R.id.ib_dialog_artPoi_intoPro://进入扣分项目
                    intoPro();
                    break;
                case R.id.ib_dialog_artPoi_intoCode://进入扣分代码
                    intoCode();
                    break;
                case R.id.iv_dialog_artPoi_exit://取消
                    ArtificialPointsDialogActivity.this.finish();
                    ArtificialPointsDialogActivity.this.overridePendingTransition(R.anim.dialogactivity_artpoi, R.anim.dialogactivity_artpoi_exit);
                    break;
                case R.id.iv_dialog_artPoi_sure://确定
                    MarkingBean markingBean = new MarkingBean(proString, fraction, codeString,null,null);
                    intent = new Intent(ArtificialPointsDialogActivity.this, ExamProcessActivity.class);
                    if (markingBean.getMarkProject() != null && markingBean.getMarkFraction() != null && markingBean.getMarkRes() != null) {
                        intent.putExtra("markingBean", markingBean);
                    }
                    setResult(0, intent);
                    ArtificialPointsDialogActivity.this.finish();
                    ArtificialPointsDialogActivity.this.overridePendingTransition(R.anim.dialogactivity_artpoi, R.anim.dialogactivity_artpoi_exit);
                    break;
                case R.id.et_dialog_artPoi_markPro://文本框
                    et_dialog_artPoi_markPro.setFocusable(true);
                    et_dialog_artPoi_markPro.setFocusableInTouchMode(true);
                    break;
                case R.id.rl_dialog_artPoi_intoPro://进入扣分项目
                    intoPro();
                    break;
                case R.id.rl_dialog_artPoi_intoCode://进入扣分代码
                    intoCode();
                    break;
            }
        }
    }

    /**
     * 进入扣分项目
     */
    private void intoPro() {
        intent = new Intent(ArtificialPointsDialogActivity.this, ArtificialPointsProDialogActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * 进入扣分代码
     */
    private void intoCode() {
        intent = new Intent(ArtificialPointsDialogActivity.this, ArtificialPointsCodeDialogActivity.class);
        startActivityForResult(intent, 0);
    }

    /**
     * 页面返回监听事件
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0://扣分代码
                codeString = data.getStringExtra("codeString");
                fraction = data.getStringExtra("fraction");
                code = data.getStringExtra("code");
                tv_dialog_artPoi_code.setText(code);
                et_dialog_artPoi_markPro.setText(codeString);
                tv_dialog_artPoi_point.setText(fraction);
                break;
            case 1://扣分项目
                proString = data.getStringExtra("proString");
                tv_dialog_artPoi_pro.setText(proString);
                break;
        }
    }

}
