package com.jd.yyc.ui.widget;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 *
 */

public class Info {

    public View tabWidgetView;
    public String tabName;
    public Fragment fragment;

    public Info(View tabWidgetView, Fragment fragment) {
        this.tabWidgetView = tabWidgetView;
        this.fragment = fragment;
    }

    public Info(String tabName, Fragment fragment) {
        this.tabName = tabName;
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getTabName() {
        return tabName;
    }
}
