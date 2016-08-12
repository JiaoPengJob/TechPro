package cn.hongjitech.vehicle.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.hongjitech.vehicle.R;

/**
 * 扣分项目的适配器
 */
public class ArtificialProAdapter extends BaseAdapter {

    private List<String> list;
    private LayoutInflater layoutInflater;
    private int selectedPosition = -1;

    public ArtificialProAdapter(Context context, List<String> list) {
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
            convertView = layoutInflater.inflate(R.layout.item_artificial_mark_pro, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_item_art_pro_content.setText(list.get(position));
        if (selectedPosition == position) {
            viewHolder.rl_item_art_pro_parent.setBackgroundColor(Color.parseColor("#ddf4ff"));
            viewHolder.ib_item_art_pro_correct.setVisibility(View.VISIBLE);
        } else {
            viewHolder.rl_item_art_pro_parent.setBackgroundColor(Color.parseColor("#FFFFFF"));
            viewHolder.ib_item_art_pro_correct.setVisibility(View.GONE);
        }
        return convertView;
    }

    public final class ViewHolder {
        @InjectView(R.id.tv_item_art_pro_content)
        public TextView tv_item_art_pro_content;//扣分项目内容
        @InjectView(R.id.ib_item_art_pro_correct)
        public ImageButton ib_item_art_pro_correct;//选中的符号
        @InjectView(R.id.rl_item_art_pro_parent)
        RelativeLayout rl_item_art_pro_parent;//整个item布局

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
