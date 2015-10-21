package com.coatinghome.activitys.tab;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coatinghome.R;

/**
 * Created by wuyajun on 15/10/19.
 * Detail:我de 界面
 */
public class FragmentMy extends BaseFragment {

    //获得activity的传递的值
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    //实例化成员变量
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //给当前的fragment绘制UI布局，可以使用线程更新UI
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);
        return view;

    }

    //表示activity执行oncreate方法完成了的时候会调用此方法
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void initViews() {

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
