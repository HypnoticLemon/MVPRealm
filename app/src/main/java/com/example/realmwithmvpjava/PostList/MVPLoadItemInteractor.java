package com.example.realmwithmvpjava.PostList;

import java.util.List;

interface MVPLoadItemInteractor {

    interface OnFinishedListener {
        void onFinished(Boolean isInternetAvailable);
    }

    void loadItems(OnFinishedListener listener);
}
