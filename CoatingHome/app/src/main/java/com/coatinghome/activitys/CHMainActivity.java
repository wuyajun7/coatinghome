package com.coatinghome.activitys;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.coatinghome.R;
import com.coatinghome.activitys.tab.FragmentFind;
import com.coatinghome.activitys.tab.FragmentMarket;
import com.coatinghome.activitys.tab.FragmentMy;
import com.coatinghome.models.CHUserInfo;
import com.coatinghome.models.CHUserUnread;
import com.coatinghome.providers.CHContrat;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;
import roboguice.inject.InjectView;

/**
 * Created by wuyajun on 15/10/22.
 * Detail: 主页
 */
public class CHMainActivity extends CHBaseActivity {

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

    @InjectView(R.id.user_icon)
    private RoundedImageView mUserIcon;
    @InjectView(R.id.find_search_layout)
    private LinearLayout mFindSearchLayout;
    @InjectView(R.id.find_search_tip)
    private TextView mFindSearchTip;
    @InjectView(R.id.find_message)
    private FrameLayout mFindMessage;
    @InjectView(R.id.find_message_tip_dot)
    private View mFindMessageTipDot;

    /* 用于对Fragment进行管理 */
    private FragmentManager fragmentManager;

    private FragmentFind fragmentFind;
    private FragmentMarket fragmentMarket;
    private FragmentMy fragmentMy;

    private DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .showImageOnLoading(R.drawable.icon_user_default) //设置图片在下载期间显示的图片
            .showImageForEmptyUri(R.drawable.icon_user_default)//设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.icon_user_default)  //设置图片加载/解码过程中错误时候显示的图片
            .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .build();

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
        requestUserInfo();
    }

    private Handler handler = new Handler();

    private void requestUserInfo() {

        CHUserInfo userInfo = BmobUser.getCurrentUser(this, CHUserInfo.class);
        if (userInfo != null) {
            // 允许用户使用应用
            setUserDataToViews(userInfo);
        } else {
            //缓存用户对象为空时， 可打开用户注册界面…

            List<String> tip1 = new ArrayList<>();
            tip1.add("高级防水涂料");
            tip1.add("纳米油漆");
            tip1.add("多乐士");
            tip1.add("建筑涂料");

            final CHUserInfo myUser = new CHUserInfo();
            myUser.setUsername("test1");
            myUser.setPassword("111111");
            myUser.userAge = 18;
            myUser.userId = 1;
            myUser.userAdd = "上海";
            myUser.userLevel = 3;
            myUser.userIcon = CHContrat.userIcon;
            myUser.userCompanyAdd = "北京";
            myUser.userSex = 0;
            myUser.userCompanyName = "北京油漆分销有限公司";
            myUser.userCompanyIntro = "北京油漆分销有限公司";
            myUser.searchTip = tip1;
            myUser.signUp(this, new SaveListener() {

                @Override
                public void onSuccess() {
                    Log.i("ioioippoi", "注册成功:" + myUser.getUsername() + "-"
                            + myUser.getObjectId() + "-" + myUser.getCreatedAt()
                            + "-" + myUser.getSessionToken() + ",是否验证：" + myUser.getEmailVerified());
                }

                @Override
                public void onFailure(int code, String msg) {
                    Log.i("ioioippoi", "注册失败:" + msg);
                }
            });
        }

    }

    private void setUserDataToViews(final CHUserInfo bmobUser) {
        if (!TextUtils.isEmpty(bmobUser.userIcon)) {
            ImageLoader.getInstance().displayImage(bmobUser.userIcon, mUserIcon, mOptions);
        }

        handler.post(new Runnable() {
            int i = 0;

            @Override
            public void run() {
                if (i == bmobUser.searchTip.size())
                    i = 0;

                mFindSearchTip.setText(bmobUser.searchTip.get(i++));
                handler.postDelayed(this, 15000);
            }
        });

        Log.i("iiooppooii",""+bmobUser.getSessionToken());

//        if (userInfo.unreadMsg > 0) {
//            CHContrat.showView(mFindMessageTipDot);
//        } else {
//            CHContrat.hideView(mFindMessageTipDot);
//        }
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
        CHContrat.hideView(mUserIcon, mFindSearchLayout, mFindMessage);
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case INDEX_FIND:
                mTabBtnFind.setImageResource(R.drawable.tab_find_down);
                mTabTextFind.setTextColor(getResources().getColor(R.color.colorGray1));
                CHContrat.showView(mUserIcon, mFindSearchLayout, mFindMessage);
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
