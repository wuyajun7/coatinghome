package com.coatinghome.activitys.tab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.coatinghome.R;
import com.coatinghome.activitys.CHWebViewActivity;
import com.coatinghome.adapters.TabFindAdapter;
import com.coatinghome.customviews.NNTextSliderView;
import com.coatinghome.models.CHBanner;
import com.coatinghome.providers.CHContrat;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import roboguice.inject.InjectView;

/**
 * Created by wuyajun on 15/10/19.
 * Detail:发现界面
 */
public class FragmentFind extends BaseFragment {

    @InjectView(R.id.find_list_view)
    private PullToRefreshListView mPullToRefreshListView;

    private TabFindAdapter mTabFindAdapter;

    private static final String BANNER_JUMP_URL = "banner_jump_url";
    private static final String BANNER_TITLE = "banner_title";
    private Context mContext;

    private SliderLayout mBanner = null;
    private View bannerLayout = null;
    private LayoutInflater mLayoutInflater = null;

    //获得activity的传递的值
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    //实例化成员变量
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    //给当前的fragment绘制UI布局，可以使用线程更新UI
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, null);
        return view;

    }

    //表示activity执行oncreate方法完成了的时候会调用此方法
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void initViews() {

        mTabFindAdapter = new TabFindAdapter(mContext,
                new ArrayList<String>(),
                -1);

        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

                refreshView.getLoadingLayoutProxy().setPullLabel("下拉可以刷新");
                refreshView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新数据中");
                refreshView.getLoadingLayoutProxy().setReleaseLabel("松开立即刷新");

                requestFindData();

//                //下拉刷新时，如果banner数据为空，则进行数据拉取，否则不刷新数据
//                if (nnBanners.size() == 0) {
//                    requestBannerInfo();
//                }

            }
        });

        ListView listView = mPullToRefreshListView.getRefreshableView();
        listView.addHeaderView(getBannerView());
        listView.setAdapter(mTabFindAdapter);

        requestFindData();

    }

    /* 加载 banner view  */
    private View getBannerView() {
        if (mLayoutInflater == null) {
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        bannerLayout = mLayoutInflater.inflate(R.layout.view_pub_banner, null);
        mBanner = (SliderLayout) bannerLayout.findViewById(R.id.pub_slider);
        mBanner.setPresetTransformer(SliderLayout.Transformer.Default);//滚动方式
        mBanner.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);//指示器位置
        mBanner.setCustomAnimation(null);
        LinearLayout.LayoutParams wh = getLayoutParams(1, 3);//设置banner 宽高比
        mBanner.setLayoutParams(wh);

        requestBannerInfo();

        return bannerLayout;
    }

    private void requestBannerInfo() {
        List<CHBanner> response = new ArrayList<>();

        CHBanner nnBanner1 = new CHBanner();
        nnBanner1.id = 1;
        nnBanner1.image_url = "http://pic29.nipic.com/20130530/10893559_221900163000_2.jpg";
        nnBanner1.title = "xxx";
        nnBanner1.jump_url = "http://www.baidu.com";

        response.add(nnBanner1);
        response.add(nnBanner1);

        setBannerData(response);
    }

    /**
     * 设置view 宽高比
     *
     * @param w 宽比例
     * @param h 高比例
     * @return LayoutParams
     */
    public LinearLayout.LayoutParams getLayoutParams(int w, int h) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        return new LinearLayout.LayoutParams(width, width * w / h);
    }

    /* 设置banner数据 */
    private void setBannerData(List<CHBanner> response) {
        if (response != null && (response).size() != 0) {//有数据则加载正常数据，无数据加载默认数据
            for (CHBanner nnBanner : response) {
                NNTextSliderView textSliderView = new NNTextSliderView(mContext);
                textSliderView
                        .description("")
                        .image(nnBanner.image_url)
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(new NNOnSliderClickListener());

                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString(BANNER_TITLE, nnBanner.title);
                textSliderView.getBundle().putString(BANNER_JUMP_URL, nnBanner.jump_url);

                mBanner.addSlider(textSliderView);
            }
        }
    }

    /* banner item 点击事件 */
    private class NNOnSliderClickListener implements BaseSliderView.OnSliderClickListener {

        @Override
        public void onSliderClick(BaseSliderView baseSliderView) {
            Bundle bundle = baseSliderView.getBundle();
            Intent intent = new Intent(mContext, CHWebViewActivity.class);
            intent.putExtra(CHContrat.WEB_TITLE_TEXT, bundle.getString(BANNER_TITLE));
            intent.putExtra(CHContrat.WEB_URL_PATH, bundle.getString(BANNER_JUMP_URL));
            startActivity(intent);
        }
    }

    private void requestFindData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTabFindAdapter.notifyDataSetChanged();
                mPullToRefreshListView.onRefreshComplete();
            }
        }, 2000);
    }


    //和activity一致
    @Override
    public void onStart() {
        super.onStart();
    }

    //和activity一致
    @Override
    public void onResume() {
        super.onResume();
    }

    //和activity一致
    @Override
    public void onPause() {
        super.onPause();
    }

    //和activity一致
    @Override
    public void onStop() {
        super.onStop();
    }

    //表示fragment销毁相关联的UI布局
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    //销毁fragment对象
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //脱离activity
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
