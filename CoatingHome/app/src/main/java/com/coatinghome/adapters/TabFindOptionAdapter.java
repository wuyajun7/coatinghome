package com.coatinghome.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.coatinghome.R;
import com.coatinghome.adapters.wnadapter.WNBaseAdapter;
import com.coatinghome.adapters.wnadapter.WNViewHolder;
import com.coatinghome.models.CHFindOption;

import java.util.List;

/**
 * Created by wuyajun on 15/6/19.
 * <p>
 * 发现界面宫格 adapter
 */
public class TabFindOptionAdapter extends WNBaseAdapter<CHFindOption> {

    public TabFindOptionAdapter(Context context, List<CHFindOption> datas, int itemId) {
        super(context, datas, itemId);
    }

    @Override
    public void convertView(WNViewHolder holder, CHFindOption homeOptionsBean, int position) {
        ((ImageView) holder.getView(R.id.option_item_img)).setImageResource(homeOptionsBean.item_img_id);
        ((TextView) holder.getView(R.id.option_item_text)).setText(homeOptionsBean.item_text);
        if (position == 1) holder.getView(R.id.new_tag).setVisibility(View.VISIBLE);
    }
}
