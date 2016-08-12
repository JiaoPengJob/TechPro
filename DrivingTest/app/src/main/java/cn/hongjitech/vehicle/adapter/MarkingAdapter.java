package cn.hongjitech.vehicle.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.bean.MarkingBean;

/**
 * 扣分信息列表的适配器
 */
public class MarkingAdapter extends BaseAdapter {

    private List<MarkingBean> list;
    private LayoutInflater layoutInflater;

    public MarkingAdapter(Context context, List<MarkingBean> list) {
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MarkingBean getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_exam_process, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_item_exam_mark_pro.setText(list.get(position).getMarkProject());
        viewHolder.tv_item_exam_mark.setText(list.get(position).getMarkFraction());
        viewHolder.tv_item_exam_mark_res.setText(list.get(position).getMarkRes());
        return convertView;
    }

    public final class ViewHolder {
        @InjectView(R.id.tv_item_exam_mark_pro)
        public TextView tv_item_exam_mark_pro;//扣分项目
        @InjectView(R.id.tv_item_exam_mark)
        public TextView tv_item_exam_mark;//扣分
        @InjectView(R.id.tv_item_exam_mark_res)
        public TextView tv_item_exam_mark_res;//扣分原因

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
