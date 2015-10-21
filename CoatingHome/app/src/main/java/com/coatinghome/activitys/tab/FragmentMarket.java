package com.coatinghome.activitys.tab;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.coatinghome.R;

/**
 * Created by wuyajun on 15/10/19.
 * Detail:供求 界面
 */
public class FragmentMarket extends Fragment {

    private final String TAG = "MyFragment";

    //获得activity的传递的值
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "--MyFragment->>onAttach");
    }

    //实例化成员变量
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "--MyFragment->>onCreate");
    }

    //给当前的fragment绘制UI布局，可以使用线程更新UI
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "--MyFragment->>onCreateView");
        View view = inflater.inflate(R.layout.fragment_market, null);
        return view;

    }

    //表示activity执行oncreate方法完成了的时候会调用此方法
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "--MyFragment->>onActivityCreated");
    }

    //和activity一致
    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "--MyFragment->>onStart");
    }

    //和activity一致
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "--MyFragment->>onResume");
    }

    //和activity一致
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "--MyFragment->>onPause");
    }

    //和activity一致
    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "--MyFragment->>onStop");
    }

    //表示fragment销毁相关联的UI布局
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "--MyFragment->>onDestroyView");
    }

    //销毁fragment对象
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "--MyFragment->>onDestroy");
    }

    //脱离activity
    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "--MyFragment->>onDetach");
    }
}
