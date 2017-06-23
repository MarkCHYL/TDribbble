package com.tyc.tdribbble.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.tyc.tdribbble.ui.login.LoginActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：tangyc on 2017/6/20
 * 邮箱：874500641@qq.com
 */


public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        setContentView(layoutResID());

        EventBus.getDefault().register(this);


        bind = ButterKnife.bind(this);
        initData();
    }

    protected void initToolbar() {

    }

    ;

    protected abstract int layoutResID();

    protected abstract void initData();

    @Subscribe
    public void onMessageEvent(Object object) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();

        EventBus.getDefault().unregister(this);

    }
}
