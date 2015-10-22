package com.coatinghome.activitys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coatinghome.R;
import com.coatinghome.activitys.tab.FragmentFind;
import com.coatinghome.activitys.tab.FragmentMarket;
import com.coatinghome.activitys.tab.FragmentMy;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class CHMainActivity extends RoboActivity implements OnClickListener {

    private final int INDEX_FIND = 0;
    private final int INDEX_MARKET = 1;
    private final int INDEX_MY = 2;

    @InjectView(R.id.tab_find_layout)
    private LinearLayout mTabFind;
    @InjectView(R.id.tab_market_layout)
    private LinearLayout mTabMarket;
    @InjectView(R.id.tab_my_layout)
    private LinearLayout mTabMy;

    @InjectView(R.id.tab_find_btn)
    private ImageButton mTabBtnFind;
    @InjectView(R.id.tab_market_btn)
    private ImageButton mTabBtnMarket;
    @InjectView(R.id.tab_my_btn)
    private ImageButton mTabBtnMy;

    @InjectView(R.id.tab_find_text)
    private TextView mTabTextFind;
    @InjectView(R.id.tab_market_text)
    private TextView mTabTextMarket;
    @InjectView(R.id.tab_my_text)
    private TextView mTabTextMy;

    @InjectView(R.id.find_search_layout)
    private LinearLayout mFindSearchLayout;
    @InjectView(R.id.find_message)
    private ImageView mFindMessage;

    /* 用于对Fragment进行管理 */
    private FragmentManager fragmentManager;

    private FragmentFind fragmentFind;
    private FragmentMarket fragmentMarket;
    private FragmentMy fragmentMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        fragmentManager = getFragmentManager();

        mTabFind.setOnClickListener(this);
        mTabMarket.setOnClickListener(this);
        mTabMy.setOnClickListener(this);

        setTabSelection(INDEX_FIND);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_find_layout:
                setTabSelection(INDEX_FIND);
                break;
            case R.id.tab_market_layout:
                setTabSelection(INDEX_MARKET);
                break;
            case R.id.tab_my_layout:
                setTabSelection(INDEX_MY);
                break;
            default:
                break;
        }
    }

    /* 根据传入的index参数来设置选中的tab页 */
    private void setTabSelection(int index) {
        // 重置按钮
        resetTabBtn();
        // 重置文本颜色
        resetTextColor();
        // 重置头部布局
        resetTitleView();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case INDEX_FIND:
                mTabBtnFind.setImageResource(R.drawable.tab_find_down);
                mTabTextFind.setTextColor(getResources().getColor(R.color.colorGray1));
                mFindSearchLayout.setVisibility(View.VISIBLE);
                mFindMessage.setVisibility(View.VISIBLE);
                if (fragmentFind == null) {
                    fragmentFind = new FragmentFind();
                    transaction.add(R.id.id_content, fragmentFind);
                } else {
                    transaction.show(fragmentFind);
                }
                break;
            case INDEX_MARKET:
                mTabBtnMarket.setImageResource(R.drawable.tab_market_down);
                mTabTextMarket.setTextColor(getResources().getColor(R.color.colorGray1));
                if (fragmentMarket == null) {
                    fragmentMarket = new FragmentMarket();
                    transaction.add(R.id.id_content, fragmentMarket);
                } else {
                    transaction.show(fragmentMarket);
                }
                break;
            case INDEX_MY:
                mTabBtnMy.setImageResource(R.drawable.tab_my_down);
                mTabTextMy.setTextColor(getResources().getColor(R.color.colorGray1));
                if (fragmentMy == null) {
                    fragmentMy = new FragmentMy();
                    transaction.add(R.id.id_content, fragmentMy);
                } else {
                    transaction.show(fragmentMy);
                }
                break;
        }
        transaction.commit();
    }

    /* 重置头部布局 */
    private void resetTitleView() {
        mFindSearchLayout.setVisibility(View.GONE);
        mFindMessage.setVisibility(View.GONE);
    }

    /* 清除掉所有的btn选中状态 */
    private void resetTabBtn() {
        mTabBtnFind.setImageResource(R.drawable.tab_find_up);
        mTabBtnMarket.setImageResource(R.drawable.tab_market_up);
        mTabBtnMy.setImageResource(R.drawable.tab_my_up);
    }

    /* 清除掉所有的TextColor */
    private void resetTextColor() {
        mTabTextFind.setTextColor(getResources().getColor(R.color.colorGray2));
        mTabTextMarket.setTextColor(getResources().getColor(R.color.colorGray2));
        mTabTextMy.setTextColor(getResources().getColor(R.color.colorGray2));
    }

    /* 将所有的Fragment都置为隐藏状态 */
    private void hideFragments(FragmentTransaction transaction) {
        if (fragmentFind != null) {
            transaction.hide(fragmentFind);
        }
        if (fragmentMarket != null) {
            transaction.hide(fragmentMarket);
        }
        if (fragmentMy != null) {
            transaction.hide(fragmentMy);
        }
    }

}
