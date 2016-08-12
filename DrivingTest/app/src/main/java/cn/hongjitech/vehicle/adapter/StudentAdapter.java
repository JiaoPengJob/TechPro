package cn.hongjitech.vehicle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.bean.Student;
import cn.hongjitech.vehicle.javaBean.StudentInfo;

/**
 *
 */
public class StudentAdapter extends BaseAdapter {

    private List<StudentInfo> list;
    private LayoutInflater layoutInflater;
    private int selectedPosition = -1;

    public StudentAdapter(Context context, List<StudentInfo> list) {
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public StudentInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = layoutInflater.inflate(R.layout.item_train_noclick, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_item_noclick_train_name.setText(list.get(position).getXm());
        viewHolder.tv_item_noclick_train_sex.setText(list.get(position).getSex());
        viewHolder.tv_item_noclick_train_id.setText(list.get(position).getSfzmhm());
        if (selectedPosition == position) {
            convertView.setBackgroundColor(Color.parseColor("#00CCFF"));
            viewHolder.tv_item_noclick_train_name.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_item_noclick_train_sex.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_item_noclick_train_id.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_item_noclick_train_name.setTextColor(Color.parseColor("#4A4A4A"));
            viewHolder.tv_item_noclick_train_sex.setTextColor(Color.parseColor("#4A4A4A"));
            viewHolder.tv_item_noclick_train_id.setTextColor(Color.parseColor("#4A4A4A"));
        }
        return convertView;
    }

    public final class ViewHolder {
        @InjectView(R.id.tv_item_noclick_train_name)
        public TextView tv_item_noclick_train_name;//姓名
        @InjectView(R.id.tv_item_noclick_train_sex)
        public TextView tv_item_noclick_train_sex;//性别
        @InjectView(R.id.tv_item_noclick_train_id)
        public TextView tv_item_noclick_train_id;//身份证号

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
