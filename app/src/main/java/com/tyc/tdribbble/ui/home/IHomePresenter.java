package com.tyc.tdribbble.ui.home;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.Map;

/**
 * 作者：tangyc on 2017/6/21
 * 邮箱：874500641@qq.com
 */
public interface IHomePresenter {
    void loadShots(RxAppCompatActivity rxAppCompatActivity, Map<String, String> map, int type);

    void loadUser(RxAppCompatActivity rxAppCompatActivity);
}
