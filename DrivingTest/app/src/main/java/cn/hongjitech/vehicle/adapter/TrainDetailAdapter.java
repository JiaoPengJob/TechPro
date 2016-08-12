package cn.hongjitech.vehicle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.bean.TrainDetail;

/**
 * 训练详细信息适配器
 */
public class TrainDetailAdapter extends BaseAdapter {

    private List<TrainDetail> list;
    private LayoutInflater layoutInflater;

    public TrainDetailAdapter(Context context, List<TrainDetail> list) {
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public TrainDetail getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = layoutInflater.inflate(R.layout.item_train_detail, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_item_traindetail_pro.setText(list.get(position).getProjectName());
        viewHolder.tv_item_traindetail_testcount.setText(list.get(position).getTestCount());
        viewHolder.tv_item_traindetail_passcount.setText(list.get(position).getPassCount());
        viewHolder.tv_item_traindetail_passnum.setText(list.get(position).getPassNum());
        viewHolder.tv_item_traindetail_averagespeed.setText(list.get(position).getAverageSpeed());
        return convertView;
    }

    public final class ViewHolder {
        @InjectView(R.id.tv_item_traindetail_pro)
        public TextView tv_item_traindetail_pro;//项目名称
        @InjectView(R.id.tv_item_traindetail_testcount)
        public TextView tv_item_traindetail_testcount;//练习次数
        @InjectView(R.id.tv_item_traindetail_passcount)
        public TextView tv_item_traindetail_passcount;//及格次数
        @InjectView(R.id.tv_item_traindetail_passnum)
        public TextView tv_item_traindetail_passnum;//及格率
        @InjectView(R.id.tv_item_traindetail_averagespeed)
        public TextView tv_item_traindetail_averagespeed;//平均速度

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
