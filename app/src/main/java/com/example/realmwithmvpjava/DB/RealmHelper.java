package com.example.realmwithmvpjava.DB;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmHelper implements DbHelper {

    private static RealmHelper instance;
    private final Realm realm;
    private final static String TAG = RealmHelper.class.getSimpleName();

    public RealmHelper(Application application) {
        realm = Realm.getDefaultInstance();
    }


  /*  public static RealmHelper with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmHelper(fragment.getActivity().getApplication());
        }
        return instance;
    }*/

    public static RealmHelper with(Activity activity) {

        if (instance == null) {
            instance = new RealmHelper(activity.getApplication());
        }
        return instance;
    }

   /* public static RealmHelper with(Application application) {

        if (instance == null) {
            instance = new RealmHelper(application);
        }
        return instance;
    }*/

    public static RealmHelper getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    @Override
    public void refresh() {
        if (!realm.isAutoRefresh())
            realm.setAutoRefresh(true);
    }

    @Override
    public void clearAll() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }


    @Override
    public void insertOrUpdateDatas(List<PostDataModel> postListModel) {
        try {
            realm.beginTransaction();
            for (PostDataModel realmPostDataModel : postListModel) {
                realm.insertOrUpdate(realmPostDataModel);
            }
            realm.commitTransaction();
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    @Override
    public RealmResults<PostDataModel> getAllDatas() {
        return realm.where(PostDataModel.class).findAll();
    }

    @Override
    public PostDataModel getDatas(int id) {
        return realm.where(PostDataModel.class).equalTo("id", id).findFirst();
    }
}
