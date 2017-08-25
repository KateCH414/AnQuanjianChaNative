package com.jd.yyc.ui.widget;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 *
 */

public class TabInfo {

    public View tabWidgetView;
    public String tabName;
    public Fragment fragment;

    public TabInfo(View tabWidgetView, Fragment fragment) {
        this.tabWidgetView = tabWidgetView;
        this.fragment = fragment;
    }

    public TabInfo(String tabName, Fragment fragment) {
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
