package cn.hongjitech.vehicle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.adapter.ArtificialProAdapter;
import cn.hongjitech.vehicle.initTestData.ArtProTestData;

/**
 * 扣分项目界面
 */
public class ArtificialPointsProDialogActivity extends BaseActivity {

    @InjectView(R.id.ib_dialog_artPro_back)
    ImageButton ib_dialog_artPro_back;//后退
    @InjectView(R.id.bt_dialog_artPro_sure)
    Button bt_dialog_artPro_sure;//确定
    @InjectView(R.id.lv_dialog_artPro_info)
    ListView lv_dialog_artPro_info;//信息列表

    /*-----------------------------------*/
    private ArtificialProAdapter artificialProAdapter;//显示列表适配器
    private List<String> list;//保存项目信息
    private String proString;//选中的item的值
    private Intent intent;//跳转intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_activity_artificial_mark_pro);
        installWindowPara();
        ButterKnife.inject(ArtificialPointsProDialogActivity.this);
        setFinishOnTouchOutside(false);//设置dialog固定,点击其他区域不消失
        initListener();
        initData();
    }

    /**
     * 加载列表数据
     */
    private void initData() {
        ArtProTestData atd = new ArtProTestData();
        list = atd.setTestData();
        artificialProAdapter = new ArtificialProAdapter(ArtificialPointsProDialogActivity.this, list);
        lv_dialog_artPro_info.setAdapter(artificialProAdapter);
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
        ib_dialog_artPro_back.setOnClickListener(new ClickListener());
        bt_dialog_artPro_sure.setOnClickListener(new ClickListener());
        //设置listview的item点击监听事件
        lv_dialog_artPro_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //设置选中item的脚标
                artificialProAdapter.setSelectedPosition(position);
                artificialProAdapter.notifyDataSetChanged();
                //获取选中对象的信息
                proString = artificialProAdapter.getItem(position);
            }
        });
    }

    /**
     * 声明点击事件监听类
     */
    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ib_dialog_artPro_back://后退
                    proString = null;
                    intenttoParent();
                    break;
                case R.id.bt_dialog_artPro_sure://确定
                    intenttoParent();
                    break;
            }
        }
    }

    /**
     * 跳转
     */
    private void intenttoParent() {
        intent = new Intent(ArtificialPointsProDialogActivity.this, ArtificialPointsDialogActivity.class);
        intent.putExtra("proString", proString);
        setResult(1, intent);
        ArtificialPointsProDialogActivity.this.finish();
        ArtificialPointsProDialogActivity.this.overridePendingTransition(R.anim.dialogactivity_artpoi, R.anim.dialogactivity_artpoi_exit);
    }
}
