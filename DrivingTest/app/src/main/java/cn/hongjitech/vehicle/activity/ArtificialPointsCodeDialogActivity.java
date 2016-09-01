package cn.hongjitech.vehicle.activity;

import android.content.Intent;
import android.os.Bundle;
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
import cn.hongjitech.vehicle.adapter.ArtificialCodeAdapter;
import cn.hongjitech.vehicle.bean.ArtificialCode;
import cn.hongjitech.vehicle.initTestData.ArtificialCodeData;

/**
 * 扣分代码             //192.168.41.254
 */
public class ArtificialPointsCodeDialogActivity extends BaseActivity {

    @InjectView(R.id.ib_dialog_artCode_back)
    ImageButton ib_dialog_artCode_back;//后退
    @InjectView(R.id.bt_dialog_artCode_sure)
    Button bt_dialog_artCode_sure;//确定
    @InjectView(R.id.lv_dialog_artCode_info)
    ListView lv_dialog_artCode_info;//信息列表

    /*-----------------------------------------*/
    private ArtificialCodeAdapter artificialCodeAdapter;//扣分代码的信息显示适配器
    private String codeString;//扣分代码的内容
    private String fraction;//扣的分数
    private String code;//扣分代码
    private List<ArtificialCode> list;
    private Intent intent;//跳转intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_activity_artificial_mark_code);
        installWindowPara();
        ButterKnife.inject(ArtificialPointsCodeDialogActivity.this);
        setFinishOnTouchOutside(false);
        initListener();
        initData();
    }

    /**
     * 加载列表数据
     */
    private void initData() {
        list = new ArrayList<ArtificialCode>();
        ArtificialCodeData acd = new ArtificialCodeData();
        list = acd.getData();
        artificialCodeAdapter = new ArtificialCodeAdapter(ArtificialPointsCodeDialogActivity.this, list);
        lv_dialog_artCode_info.setAdapter(artificialCodeAdapter);
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
        ib_dialog_artCode_back.setOnClickListener(new ClickListener());
        bt_dialog_artCode_sure.setOnClickListener(new ClickListener());
        //listView的item点击事件监听
        lv_dialog_artCode_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //设置选中的脚标
                artificialCodeAdapter.setSelectedPosition(position);
                artificialCodeAdapter.notifyDataSetChanged();
                //获取选中对象的信息
                codeString = artificialCodeAdapter.getItem(position).getContent();
                fraction = artificialCodeAdapter.getItem(position).getFraction();
                code = artificialCodeAdapter.getItem(position).getCode();
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
                case R.id.ib_dialog_artCode_back://后退
                    codeString = null;
                    intentToParent();
                    break;
                case R.id.bt_dialog_artCode_sure://确定
                    intentToParent();
                    break;
            }
        }
    }

    /**
     * 跳转页面
     */
    private void intentToParent() {
        intent = new Intent(ArtificialPointsCodeDialogActivity.this, ArtificialPointsDialogActivity.class);
        intent.putExtra("codeString", codeString);
        intent.putExtra("fraction", fraction);
        intent.putExtra("code", code);
        setResult(0, intent);
        ArtificialPointsCodeDialogActivity.this.finish();
        ArtificialPointsCodeDialogActivity.this.overridePendingTransition(R.anim.dialogactivity_artpoi, R.anim.dialogactivity_artpoi_exit);
    }
}
