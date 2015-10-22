package com.coatinghome.adapters;

import android.content.Context;

import com.coatinghome.adapters.wnadapter.WNBaseAdapter;
import com.coatinghome.adapters.wnadapter.WNViewHolder;

import java.util.List;

/**
 * Created by wuyajun on 15/6/20.
 * <p/>
 * 发现页 适配器
 */
public class TabFindAdapter extends WNBaseAdapter<String> {

    public TabFindAdapter(Context context, List<String> datas, int itemId) {
        super(context, datas, itemId);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void convertView(WNViewHolder holder, String searchCarItem, int position) {
    }
}
