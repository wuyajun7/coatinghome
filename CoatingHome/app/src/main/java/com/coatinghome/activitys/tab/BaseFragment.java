package com.coatinghome.activitys.tab;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.coatinghome.R;

import roboguice.RoboGuice;
import roboguice.inject.InjectView;


/**
 * Created by wuyajun on 15/10/19.
 * Detail:Fragment 基类
 */
public abstract class BaseFragment extends Fragment {

    //获得activity的传递的值
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    //实例化成员变量
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoboGuice.getInjector(this.getActivity()).injectMembersWithoutViews(this);
    }

    //给当前的fragment绘制UI布局，可以使用线程更新UI
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //表示activity执行oncreate方法完成了的时候会调用此方法
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RoboGuice.getInjector(this.getActivity()).injectViewMembers(getActivity());

        initViews();
    }

    public abstract void initViews();

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
