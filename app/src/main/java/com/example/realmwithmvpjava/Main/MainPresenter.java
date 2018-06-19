package com.example.realmwithmvpjava.Main;

import android.content.Context;

public class MainPresenter implements MvpMainPresenter {

    private MainView mainView;
    private Context context;


    public MainPresenter(Context context, MainView mainView) {
        this.mainView = mainView;
        this.context = context;
    }

    @Override
    public void onResume() {
        if (mainView != null) {
            mainView.loadFragment();
            mainView.permissionCheck();
        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }
}
