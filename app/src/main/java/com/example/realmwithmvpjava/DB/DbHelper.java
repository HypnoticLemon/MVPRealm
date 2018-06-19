package com.example.realmwithmvpjava.DB;

import java.util.List;

import io.realm.RealmResults;

interface DbHelper {

    void refresh();

    void clearAll();

    void insertOrUpdateDatas(List<PostDataModel> postListModel);

    RealmResults<PostDataModel> getAllDatas();

    PostDataModel getDatas(int id);

}
