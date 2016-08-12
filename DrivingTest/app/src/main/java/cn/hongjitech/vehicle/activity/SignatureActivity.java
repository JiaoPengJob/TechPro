package cn.hongjitech.vehicle.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.gcacace.signaturepad.views.SignaturePad;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.adapter.MarkingAdapter;
import cn.hongjitech.vehicle.adapter.TrainDetailAdapter;
import cn.hongjitech.vehicle.bean.MarkingBean;
import cn.hongjitech.vehicle.bean.TrainDetail;
import cn.hongjitech.vehicle.bean.UserInfo;
import cn.hongjitech.vehicle.javaBean.SignInResultRoot;
import cn.hongjitech.vehicle.javaBean.User;
import cn.hongjitech.vehicle.util.SharedPrefsUtils;
import cn.hongjitech.vehicle.util.StringUtils;
import cn.hongjitech.vehicle.volleyHttp.VolleyManager;

public class SignatureActivity extends BaseActivity {

    @InjectView(R.id.lv_signature_mark_info)
    ListView lv_signature_mark_info;//扣分信息列表
    @InjectView(R.id.lv_signature_result_info)
    ListView lv_signature_result_info;//训练详情信息列表
    @InjectView(R.id.iv_signature_over)
    ImageView iv_signature_over;//结束
    @InjectView(R.id.rl_signature_student)
    RelativeLayout rl_signature_student;//学员签字
    @InjectView(R.id.rl_signature_teacher)
    RelativeLayout rl_signature_teacher;//教练签字
    @InjectView(R.id.iv_signature_teacher)
    ImageView iv_signature_teacher;//学员签字后的显示图片
    @InjectView(R.id.iv_signature_student)
    ImageView iv_signature_student;//教练签字后的显示图片
    @InjectView(R.id.iv_signature_train)
    ImageView iv_signature_train;//继续考核
    @InjectView(R.id.iv_signature_test)
    ImageView iv_signature_test;//继续训练

    @InjectView(R.id.tv_signature_mark_user_name)
    TextView tv_signature_mark_user_name;//学员姓名
    @InjectView(R.id.tv_signature_mark_user_idCode)
    TextView tv_signature_mark_user_idCode;//身份证
    @InjectView(R.id.tv_signature_mark_user_time)
    TextView tv_signature_mark_user_time;//时间段
    @InjectView(R.id.tv_signature_mark_user_useTime)
    TextView tv_signature_mark_user_useTime;//用时
    @InjectView(R.id.tv_signature_mark_user_num)
    TextView tv_signature_mark_user_num;//扣分次数
    @InjectView(R.id.tv_signature_mark_user_length)
    TextView tv_signature_mark_user_length;//行驶距离

    /*-------------------------------------*/
    ImageButton ib_dialog_signature_back;//后退
    ImageButton ib_dialog_signature_clear;//清除
    ImageButton ib_dialog_signature_sure;//确定
    SignaturePad signature_pad;//手写板
    /*-------------------------------------*/
    private List<MarkingBean> markingBeens;//扣分信息集合
    private List<TrainDetail> trainDetails;//训练详情信息集合
    private Intent intent;//跳转Intent对象
    private int sigType = -1;//判断由谁来进行签名
    private AlertDialog alertDialog;//dialog对象
    private UserInfo userInfo;//学员考核信息
    private User user = new User();//用户对象
    private String appointment_id;//预约ID
    private String vehicle_id = "1";//车辆id
    private String subject_id = "3";//项目id
    private String activityType;//考试还是考核

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signature);
        ButterKnife.inject(SignatureActivity.this);
        initListener();
        initData();
        appointment_id = SharedPrefsUtils.getValue(SignatureActivity.this, "appointment_id", "");
    }

    /**
     * 设置结束显示的窗口提醒
     */
    private void showDialogForExamOver() {
        View view = (RelativeLayout) getLayoutInflater().inflate(R.layout.dialog_activity_art, null);
        alertDialog = new AlertDialog.Builder(this).setTitle(null).setMessage(null).setView(view).create();
        ib_dialog_signature_back = (ImageButton) view.findViewById(R.id.ib_dialog_signature_back);
        ib_dialog_signature_clear = (ImageButton) view.findViewById(R.id.ib_dialog_signature_clear);
        ib_dialog_signature_sure = (ImageButton) view.findViewById(R.id.ib_dialog_signature_sure);
        signature_pad = (SignaturePad) view.findViewById(R.id.signature_pad);
        ib_dialog_signature_back.setOnClickListener(new ClickListener());
        ib_dialog_signature_clear.setOnClickListener(new ClickListener());
        ib_dialog_signature_sure.setOnClickListener(new ClickListener());
        alertDialog.show();
        alertDialog.getWindow().setLayout(900, 650);
        alertDialog.setCanceledOnTouchOutside(false);

        /**
         * 手写板监听事件
         */
        signature_pad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            /**
             * 开始进行手写
             */
            @Override
            public void onStartSigning() {

            }

            /**
             * 手写过程中
             */
            @Override
            public void onSigned() {
                ib_dialog_signature_clear.setEnabled(true);
                ib_dialog_signature_sure.setEnabled(true);
            }

            /**
             * 手写板空白时
             */
            @Override
            public void onClear() {
                ib_dialog_signature_clear.setEnabled(false);
                ib_dialog_signature_sure.setEnabled(false);
            }
        });
    }

    /**
     * 加载列表数据
     */
    private void initData() {
        if (getIntent().getSerializableExtra("activity") != null && getIntent().getSerializableExtra("userInfo") != null && getIntent().getSerializableExtra("user") != null) {
            activityType = getIntent().getStringExtra("activity");

            if (activityType.equals("stuApprove")) {
                iv_signature_train.setClickable(false);
                iv_signature_test.setClickable(false);
            } else {
                iv_signature_train.setClickable(true);
                iv_signature_test.setClickable(true);
            }

            userInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");
            user = (User) getIntent().getSerializableExtra("user");
            tv_signature_mark_user_name.setText(userInfo.getName());
            tv_signature_mark_user_idCode.setText(userInfo.getIdCode());
            tv_signature_mark_user_time.setText(userInfo.getStartTime() + " - " + userInfo.getEndTime());
            tv_signature_mark_user_useTime.setText(userInfo.getUserTime());
            tv_signature_mark_user_num.setText(userInfo.getNum());
            tv_signature_mark_user_length.setText(userInfo.getLength());
            lv_signature_mark_info.setAdapter(new MarkingAdapter(SignatureActivity.this, userInfo.getMarkingBean()));
        }

        trainDetails = new ArrayList<TrainDetail>();
        for (int i = 0; i < 19; i++) {
            trainDetails.add(new TrainDetail("同车互动", "1" + i, "1" + i, "10%", "5.1km/h"));
        }
        lv_signature_result_info.setAdapter(new TrainDetailAdapter(SignatureActivity.this, trainDetails));

    }

    /**
     * 加载点击事件监听函数
     */
    private void initListener() {
        iv_signature_over.setOnClickListener(new ClickListener());
        rl_signature_student.setOnClickListener(new ClickListener());
        rl_signature_teacher.setOnClickListener(new ClickListener());
        iv_signature_train.setOnClickListener(new ClickListener());
        iv_signature_test.setOnClickListener(new ClickListener());
    }

    /**
     * 声明点击事件监听类
     */
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_signature_over://结束
                    if (!appointment_id.equals("") && !vehicle_id.equals("") && !subject_id.equals("")) {
                        String url = "http://backend.dev.hongjitech.cn/api/android/signOut?appointment_id=" + appointment_id + "&vehicle_id=" + vehicle_id + "&subject_id=" + subject_id;
                        signOut(url);
                    }
                    if (activityType.equals("stuApprove")) {
                        intent = new Intent(SignatureActivity.this, StuApproveActivity.class);
                        startActivity(intent);
                        SignatureActivity.this.finish();
                    } else {
                        intent = new Intent(SignatureActivity.this, TrainingActivity.class);
                        startActivity(intent);
                        SignatureActivity.this.finish();
                    }
                    break;
                case R.id.rl_signature_student://学员签字
                    sigType = 0;
                    showDialogForExamOver();
                    break;
                case R.id.rl_signature_teacher://教练签字
                    sigType = 1;
                    showDialogForExamOver();
                    break;
                case R.id.ib_dialog_signature_back://dialog 返回
                    alertDialog.cancel();
                    break;
                case R.id.ib_dialog_signature_clear://dialog 清除
                    //手写板清空
                    signature_pad.clear();
                    break;
                case R.id.ib_dialog_signature_sure://dialog 确定
                    alertDialog.cancel();
                    //获取手写板的图片
                    Bitmap signatureBitmap = signature_pad.getSignatureBitmap();
                    if (sigType == 0) {
                        iv_signature_student.setImageBitmap(StringUtils.ratio(signatureBitmap, 300, 100));
                    } else {
                        iv_signature_teacher.setImageBitmap(StringUtils.ratio(signatureBitmap, 300, 100));
                    }
                    break;
                case R.id.iv_signature_train://继续考核
                    intent = new Intent(SignatureActivity.this, ExamProcessActivity.class);
                    intent.putExtra("activity", "training");
                    intent.putExtra("user", user);
                    startActivity(intent);
                    SignatureActivity.this.finish();
                    break;
                case R.id.iv_signature_test://继续训练
                    intent = new Intent(SignatureActivity.this, ExamProcessActivity.class);
                    intent.putExtra("activity", "trainTest");
                    intent.putExtra("user", user);
                    startActivity(intent);
                    SignatureActivity.this.finish();
                    break;
            }
        }
    }

    /**
     * 访问服务器签到
     */
    private void signOut(String url) {
        VolleyManager.newInstance().GsonGetRequest("TAG", url, SignInResultRoot.class,
                new Response.Listener<SignInResultRoot>() {
                    @Override
                    public void onResponse(SignInResultRoot signInResultRoot) {
                        if (signInResultRoot != null) {
                            Log.d("TAG", "Result:" + signInResultRoot.getMessage());
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
