package com.aliyun.vodplayerview.fragment;

import android.support.v4.app.Fragment;

public abstract class BaseFragment extends Fragment {

    /**
     * 默认配置
     */
    public abstract void defaultPlayInfo();

    /**
     * 使用此配置
     */
    public abstract void confirmPlayInfo();
}
