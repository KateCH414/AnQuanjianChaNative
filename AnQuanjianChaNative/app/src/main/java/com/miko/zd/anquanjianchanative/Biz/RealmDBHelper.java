package com.miko.zd.anquanjianchanative.Biz;
/*
 * Created by zd on 2016/11/13.
 */

import com.miko.zd.anquanjianchanative.Bean.RealmBean.HistoryCheckedBean;
import com.miko.zd.anquanjianchanative.Bean.RealmBean.ItemBean1;

import io.realm.Realm;
import io.realm.Sort;

public class RealmDBHelper implements IItemDBHelper, IHistoryDBHelper {

    @Override
    public boolean hadAddedItems() {
        boolean hadAdded = false;
        Realm realm = Realm.getDefaultInstance();
        if (realm.where(ItemBean1.class).findFirst() != null) {
            hadAdded = true;
        }
        realm.close();
        return hadAdded;
    }

    @Override
    public int getLastOrder() {
        Realm realm = Realm.getDefaultInstance();
        /**表中尚无数据，返回-1*/
        if (realm.where(HistoryCheckedBean.class)
                .findAllSorted("order", Sort.DESCENDING).size() == 0) {
            return -1;
        } else
            return realm.where(HistoryCheckedBean.class)
                    .findAllSorted("order", Sort.DESCENDING).first().getOrder();
    }
}
