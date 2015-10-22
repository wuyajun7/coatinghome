package com.coatinghome.activitys.tab;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.coatinghome.R;
import com.coatinghome.adapters.TabFindAdapter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import roboguice.inject.InjectView;


/**
 * Created by wuyajun on 15/10/19.
 * Detail:发现界面
 */
public class FragmentFind extends BaseFragment {

    @InjectView(R.id.find_list_view)
    private PullToRefreshListView mPullToRefreshListView;

    private TabFindAdapter mTabFindAdapter;

    private Context mContext;

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

        TextView emptyText = new TextView(mContext);
        emptyText.setGravity(Gravity.CENTER);
        emptyText.setText("暂时没有数据");
        emptyText.setTextColor(mContext.getResources().getColor(R.color.colorGray1));

        listView.addHeaderView(emptyText);
        listView.setAdapter(mTabFindAdapter);

        requestFindData();

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
