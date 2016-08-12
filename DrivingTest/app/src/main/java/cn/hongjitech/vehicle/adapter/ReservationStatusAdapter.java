package cn.hongjitech.vehicle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;
import cn.hongjitech.vehicle.bean.ReservationStatus;
import cn.hongjitech.vehicle.javaBean.ReservationInfo;

/**
 * 预约状态
 */
public class ReservationStatusAdapter extends BaseAdapter {

    private List<ReservationInfo> list;
    private LayoutInflater layoutInflater;
    private int selectedPosition = -1;// 选中的位置

    public ReservationStatusAdapter(Context context, List<ReservationInfo> list) {
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ReservationInfo getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_reservation_status, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_item_statu_time.setText(list.get(position).getStart_time() + " - " + list.get(position).getFinish_time());
        if (list.get(position).getAppointment() != null) {
            if (list.get(position).getAppointment().getStatus().equals("TAKEN")) {
                viewHolder.tv_item_statu_moudle.setText("已预约");
            }
            if (list.get(position).getAppointment().getStatus().equals("DONE")) {
                viewHolder.tv_item_statu_moudle.setText("已完成");
            }
            if (list.get(position).getAppointment().getStatus().equals("BROKEN")) {
                viewHolder.tv_item_statu_moudle.setText("违约");
            }
        } else {
            viewHolder.tv_item_statu_moudle.setText("未预约");
        }

        /**
         * item选中改变样式
         */
        if (selectedPosition == position) {
            convertView.setBackgroundColor(Color.parseColor("#00CCFF"));
            viewHolder.tv_item_statu_time.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_item_statu_moudle.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            convertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv_item_statu_time.setTextColor(Color.parseColor("#4A4A4A"));
            viewHolder.tv_item_statu_moudle.setTextColor(Color.parseColor("#4A4A4A"));
        }

        return convertView;
    }

    public final class ViewHolder {
        @InjectView(R.id.tv_item_statu_time)
        public TextView tv_item_statu_time;//预约时间段
        @InjectView(R.id.tv_item_statu_moudle)
        public TextView tv_item_statu_moudle;//预约状态

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
