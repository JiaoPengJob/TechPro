package cn.hongjitech.vehicle.adapter;

import android.content.Context;
import android.graphics.Color;
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

/**
 * 考试:项目列表的适配器
 */
public class ProjectAdapter extends BaseAdapter {

    private List<String> list;
    private LayoutInflater layoutInflater;
    private int selectedPosition = -1;

    public ProjectAdapter(Context context, List<String> list) {
        this.list = list;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
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
            convertView = layoutInflater.inflate(R.layout.item_project, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_item_project_content.setText(list.get(position));
        if (selectedPosition == position) {
            viewHolder.tv_item_project_content.setTextColor(Color.parseColor("#00CCFF"));
            viewHolder.iv_item_project_pointNo.setImageResource(R.drawable.point);
            viewHolder.iv_item_project_pointDown.setVisibility(View.VISIBLE);
            if (selectedPosition == list.size() - 1) {
                viewHolder.iv_item_project_pointDown.setVisibility(View.GONE);
            }
        } else {
            viewHolder.iv_item_project_pointDown.setVisibility(View.GONE);
        }
        return convertView;
    }

    public final class ViewHolder {
        @InjectView(R.id.tv_item_project_content)
        public TextView tv_item_project_content;//扣分项目内容
        @InjectView(R.id.iv_item_project_pointDown)
        public ImageView iv_item_project_pointDown;//选中的符号
        @InjectView(R.id.iv_item_project_pointNo)
        public ImageView iv_item_project_pointNo;//选中圆点显示

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
