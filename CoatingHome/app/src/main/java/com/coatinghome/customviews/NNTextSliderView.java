package com.coatinghome.customviews;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coatinghome.R;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;


/**
 * Created by wuyajun on 15/6/19.
 * Detail:重构广告banner 设置文字界面布局背景为透明色，擦，非得强暴你
 */
public class NNTextSliderView extends BaseSliderView {

    public NNTextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(this.getContext()).inflate(R.layout.render_type_text, (ViewGroup) null);
        ImageView target = (ImageView) v.findViewById(R.id.daimajia_slider_image);
        TextView description = (TextView) v.findViewById(R.id.description);
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.description_layout);
        layout.setBackgroundColor(Color.TRANSPARENT);
        description.setText(this.getDescription());
        this.bindEventAndShow(v, target);
        return v;
    }

}
