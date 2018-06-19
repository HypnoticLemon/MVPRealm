package com.example.realmwithmvpjava.PostList;

interface MVPListPresenter {
    void onResume();

    void onStartService();

    void onStopService();

    void onItemClicked(int position);

    void onDestroy();
}
