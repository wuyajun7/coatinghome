package com.coatinghome.activitys.tab;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coatinghome.R;
import com.coatinghome.activitys.CHWebViewActivity;
import com.coatinghome.adapters.TabFindAdapter;
import com.coatinghome.customviews.NNTextSliderView;
import com.coatinghome.models.CHBanner;
import com.coatinghome.models.find.CHFindItem;
import com.coatinghome.models.find.CHFindItems;
import com.coatinghome.providers.CHContrat;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import roboguice.inject.InjectView;

/**
 * Created by wuyajun on 15/10/19.
 * Detail:发现 界面
 */
public class FragmentFind extends BaseFragment {

    @InjectView(R.id.find_list_view)
    private PullToRefreshListView mPullToRefreshListView;

    private TabFindAdapter mTabFindAdapter;

    private static final String BANNER_JUMP_URL = "banner_jump_url";
    private static final String BANNER_TITLE = "banner_title";

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int width;

    private SliderLayout mBanner;
    private View bannerLayout;
    private List<CHBanner> chBanners;//banner 数据

    private View listItem;
    private View listItemTitle;

    private DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .showImageOnLoading(R.drawable.icon_product_default) //设置图片在下载期间显示的图片
            .showImageForEmptyUri(R.drawable.icon_product_default)//设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.drawable.icon_product_default)  //设置图片加载/解码过程中错误时候显示的图片
            .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .build();

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

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;

        List<String> empty = new ArrayList<String>();
        empty.add("");
        mTabFindAdapter = new TabFindAdapter(mContext, empty, R.layout.view_find_item0);

        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

                refreshView.getLoadingLayoutProxy().setPullLabel("下拉可以刷新");
                refreshView.getLoadingLayoutProxy().setRefreshingLabel("正在刷新数据中");
                refreshView.getLoadingLayoutProxy().setReleaseLabel("松开立即刷新");

                requestFindData();

                //下拉刷新时，如果banner数据为空，则进行数据拉取，否则不刷新数据
                if (chBanners.size() == 0) {
                    requestBannerInfo();
                }

            }
        });

        ListView listView = mPullToRefreshListView.getRefreshableView();
        listView.setDividerHeight(0);

        listView.addHeaderView(getBannerView());
        listView.addHeaderView(getListItemViewType3());
        listView.addHeaderView(getListItemTitle("热门推荐", CHContrat.colours[0]));
        listView.addHeaderView(getListItemViewType1());
        listView.addHeaderView(getListItemTitle("好品质", CHContrat.colours[3]));
        listView.addHeaderView(getListItemViewType2());

        listView.setAdapter(mTabFindAdapter);

        requestFindData();

    }

    /* --------------------------------BANNER START--------------------------------- */
    private View getBannerView() {
        if (mLayoutInflater == null) {
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        bannerLayout = mLayoutInflater.inflate(R.layout.view_pub_banner, null);
        mBanner = (SliderLayout) bannerLayout.findViewById(R.id.pub_slider);
        mBanner.setPresetTransformer(SliderLayout.Transformer.Default);//滚动方式
        mBanner.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);//指示器位置
        mBanner.setCustomAnimation(null);
        LinearLayout.LayoutParams wh = getLayoutParams(width, 1, 3);//设置banner 宽高比
        mBanner.setLayoutParams(wh);

        requestBannerInfo();

        return bannerLayout;
    }

    private void requestBannerInfo() {

        CHBanner chBanner = new CHBanner();
        chBanner.id = 1;
        chBanner.image_url = "http://pic29.nipic.com/20130530/10893559_221900163000_2.jpg";
        chBanner.title = "xxx";
        chBanner.jump_url = "http://www.baidu.com";

        chBanners = new ArrayList<>();
        chBanners.add(chBanner);
        chBanners.add(chBanner);
        chBanners.add(chBanner);

        setBannerData(chBanners);
    }

    /**
     * 设置view 宽高比
     *
     * @param w 宽比例
     * @param h 高比例
     * @return LayoutParams
     */
    public LinearLayout.LayoutParams getLayoutParams(int mWidth, int w, int h) {
        return new LinearLayout.LayoutParams(mWidth, mWidth * w / h);
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
    /* --------------------------------BANNER END--------------------------------- */

    /* --------------------------------LIST TITLE START--------------------------------- */
    private View getListItemTitle(String title, int color) {
        if (mLayoutInflater == null) {
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        listItemTitle = mLayoutInflater.inflate(R.layout.view_find_item_title, null);
        TextView textView = (TextView) listItemTitle.findViewById(R.id.find_item_title);
        textView.setText(title);
        textView.setTextColor(getResources().getColor(color));

        return listItemTitle;
    }
    /* --------------------------------LIST TITLE END--------------------------------- */

    /* --------------------------------LIST ITEM START--------------------------------- */
    private int[] find_item1_layout_id = {R.id.find_item1_layout1,
            R.id.find_item1_layout2,
            R.id.find_item1_layout3
    };
    private int[] find_item1_title_id = {R.id.find_item1_title1,
            R.id.find_item1_title2,
            R.id.find_item1_title3
    };
    private int[] find_item1_content_id = {R.id.find_item1_content1,
            R.id.find_item1_content2,
            R.id.find_item1_content3
    };
    private int[] find_item1_iv_id = {R.id.find_item1_image1,
            R.id.find_item1_image2,
            R.id.find_item1_image3
    };

    private int find_item1_count = find_item1_layout_id.length;

    private LinearLayout[] find_item1_layout = new LinearLayout[find_item1_count];
    private TextView[] find_item1_title = new TextView[find_item1_count];
    private TextView[] find_item1_content = new TextView[find_item1_count];
    private ImageView[] find_item1_iv = new ImageView[find_item1_count];

    private View getListItemViewType1() {
        if (mLayoutInflater == null) {
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        listItem = mLayoutInflater.inflate(R.layout.view_find_item1, null);

        LinearLayout find_item = (LinearLayout) listItem.findViewById(R.id.find_item1);
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (width / 2));
        find_item.setLayoutParams(layoutParam);

        for (int i = 0; i < find_item1_count; i++) {
            find_item1_layout[i] = (LinearLayout) listItem.findViewById(find_item1_layout_id[i]);
            find_item1_layout[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CHFindItem chFindItem = (CHFindItem) v.getTag();
                    Toast.makeText(mContext, "" + chFindItem.title, Toast.LENGTH_SHORT).show();
                }
            });

            find_item1_title[i] = (TextView) listItem.findViewById(find_item1_title_id[i]);
            find_item1_content[i] = (TextView) listItem.findViewById(find_item1_content_id[i]);
            find_item1_content[i].setTextColor(getResources().getColor(CHContrat.colours[0]));
            find_item1_iv[i] = (ImageView) listItem.findViewById(find_item1_iv_id[i]);
            LinearLayout.LayoutParams ivLayoutParam;
            if (i == 0) {
                ivLayoutParam = getLayoutParams(width / 2, 2, 4);//设置 宽高比
            } else {
                ivLayoutParam = getLayoutParams(width / 4, 2, 4);//设置 宽高比
            }
            find_item1_iv[i].setLayoutParams(ivLayoutParam);

        }

        return listItem;
    }

    private int[] find_item2_layout_id = {R.id.find_item2_layout1,
            R.id.find_item2_layout2,
            R.id.find_item2_layout3,
            R.id.find_item2_layout4};

    private int[] find_item2_title_id = {R.id.find_item2_title1,
            R.id.find_item2_title2,
            R.id.find_item2_title3,
            R.id.find_item2_title4
    };
    private int[] find_item2_content_id = {R.id.find_item2_content1,
            R.id.find_item2_content2,
            R.id.find_item2_content3,
            R.id.find_item2_content4
    };
    private int[] find_item2_iv_id = {R.id.find_item2_image1,
            R.id.find_item2_image2,
            R.id.find_item2_image3,
            R.id.find_item2_image4
    };

    private int find_item2_count = find_item2_layout_id.length;

    private LinearLayout[] find_item2_layout = new LinearLayout[find_item2_count];
    private TextView[] find_item2_title = new TextView[find_item2_count];
    private TextView[] find_item2_content = new TextView[find_item2_count];
    private ImageView[] find_item2_iv = new ImageView[find_item2_count];

    private View getListItemViewType2() {
        if (mLayoutInflater == null) {
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        listItem = mLayoutInflater.inflate(R.layout.view_find_item2, null);

        LinearLayout find_item = (LinearLayout) listItem.findViewById(R.id.find_item2);
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (width / 4) / 3 + (width / 4));
        find_item.setLayoutParams(layoutParam);

        for (int i = 0; i < find_item2_count; i++) {
            find_item2_layout[i] = (LinearLayout) listItem.findViewById(find_item2_layout_id[i]);
            find_item2_layout[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CHFindItem chFindItem = (CHFindItem) v.getTag();
                    Toast.makeText(mContext, "" + chFindItem.title, Toast.LENGTH_SHORT).show();
                }
            });

            find_item2_title[i] = (TextView) listItem.findViewById(find_item2_title_id[i]);
            find_item2_title[i].setTextColor(getResources().getColor(CHContrat.colours[3]));
            find_item2_content[i] = (TextView) listItem.findViewById(find_item2_content_id[i]);
            find_item2_iv[i] = (ImageView) listItem.findViewById(find_item2_iv_id[i]);
            LinearLayout.LayoutParams ivLayoutParam = getLayoutParams(width / 4, 2, 4);//设置 宽高比
            find_item2_iv[i].setLayoutParams(ivLayoutParam);
        }

        return listItem;
    }

    private int[] find_item3_layout_id = {R.id.find_item3_layout1,
            R.id.find_item3_layout2,
            R.id.find_item3_layout3
    };
    private int[] find_item3_title_id = {R.id.find_item3_title1,
            R.id.find_item3_title2,
            R.id.find_item3_title3
    };
    private int[] find_item3_content_id = {R.id.find_item3_content1,
            R.id.find_item3_content2,
            R.id.find_item3_content3
    };
    private int[] find_item3_iv_id = {R.id.find_item3_image1,
            R.id.find_item3_image2,
            R.id.find_item3_image3
    };

    private int find_item3_count = find_item3_layout_id.length;

    private LinearLayout[] find_item3_layout = new LinearLayout[find_item3_count];
    private TextView[] find_item3_title = new TextView[find_item3_count];
    private TextView[] find_item3_content = new TextView[find_item3_count];
    private ImageView[] find_item3_iv = new ImageView[find_item3_count];

    private View getListItemViewType3() {
        if (mLayoutInflater == null) {
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        listItem = mLayoutInflater.inflate(R.layout.view_find_item3, null);

        LinearLayout find_item = (LinearLayout) listItem.findViewById(R.id.find_item3);
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (width / 3) / 3 + (width / 4));
        find_item.setLayoutParams(layoutParam);

        for (int i = 0; i < find_item3_count; i++) {
            find_item3_layout[i] = (LinearLayout) listItem.findViewById(find_item3_layout_id[i]);
            find_item3_layout[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CHFindItem chFindItem = (CHFindItem) v.getTag();
                    Toast.makeText(mContext, "" + chFindItem.title, Toast.LENGTH_SHORT).show();
                }
            });

            find_item3_title[i] = (TextView) listItem.findViewById(find_item3_title_id[i]);
            find_item3_content[i] = (TextView) listItem.findViewById(find_item3_content_id[i]);
            find_item3_iv[i] = (ImageView) listItem.findViewById(find_item3_iv_id[i]);
            LinearLayout.LayoutParams ivLayoutParam = getLayoutParams(width / 3, 3, 6);//设置 宽高比
            find_item3_iv[i].setLayoutParams(ivLayoutParam);
        }

        return listItem;
    }

    private void setDataToListItem(CHFindItems chFindItems) {
        List<CHFindItem> chFindItem1 = chFindItems.findItems1;
        for (int i = 0; i < find_item1_count; i++) {
            if (chFindItem1 != null) {
                find_item1_layout[i].setTag(chFindItem1.get(i));
                find_item1_title[i].setText(chFindItem1.get(i).title);
                find_item1_content[i].setText(chFindItem1.get(i).content);
                if (!TextUtils.isEmpty(chFindItem1.get(i).imageUrl)) {
                    ImageLoader.getInstance().displayImage(chFindItem1.get(i).imageUrl, find_item1_iv[i], mOptions);
                }
            }
        }
        List<CHFindItem> chFindItem2 = chFindItems.findItems2;
        for (int i = 0; i < find_item2_count; i++) {
            if (chFindItem2 != null) {
                find_item2_layout[i].setTag(chFindItem2.get(i));
                find_item2_title[i].setText(chFindItem2.get(i).title);
                find_item2_content[i].setText(chFindItem2.get(i).content);
                if (!TextUtils.isEmpty(chFindItem2.get(i).imageUrl)) {
                    ImageLoader.getInstance().displayImage(chFindItem2.get(i).imageUrl, find_item2_iv[i], mOptions);
                }
            }
        }
        List<CHFindItem> chFindItem3 = chFindItems.findItems3;
        for (int i = 0; i < find_item3_count; i++) {
            if (chFindItem3 != null) {
                find_item3_layout[i].setTag(chFindItem3.get(i));
                find_item3_title[i].setText(chFindItem3.get(i).title);
                find_item3_content[i].setText(chFindItem3.get(i).content);
                if (!TextUtils.isEmpty(chFindItem3.get(i).imageUrl)) {
                    ImageLoader.getInstance().displayImage(chFindItem3.get(i).imageUrl, find_item3_iv[i], mOptions);
                }
            }
        }
    }
    /* --------------------------------LIST ITEM END--------------------------------- */

    private CHFindItems mChFindItems;
    private List<CHFindItem> chFindItems = new ArrayList<>();

    int x = 0;

    private void requestFindData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                x += 1;
                mChFindItems = new CHFindItems();
                chFindItems.clear();
                CHFindItem chFindItem;
                for (int i = 0; i < CHContrat.testTitle.length; i++) {
                    chFindItem = new CHFindItem();
                    chFindItem.title = CHContrat.testTitle[i];
                    chFindItem.content = CHContrat.testContent[i];
                    chFindItem.imageUrl = CHContrat.testImageUrl[i];
                    chFindItems.add(chFindItem);
                    if (i < 5) {
                        mChFindItems.findItems1 = chFindItems;
                    } else if (i <= 10) {
                        mChFindItems.findItems2 = chFindItems;
                    } else {
                        mChFindItems.findItems3 = chFindItems;
                    }
                }

                setDataToListItem(mChFindItems);

                mTabFindAdapter.notifyDataSetChanged();
                mPullToRefreshListView.onRefreshComplete();
            }
        }, 200);
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
