
package com.example.realmwithmvpjava.PostList;

import com.example.realmwithmvpjava.DB.PostDataModel;

import java.util.List;

interface ListFragmentView {

    void showProgress();

    void hideProgress();

    void setItems(List<PostDataModel> listModels);

    void showMessage(String message);

    void showHandler();

}
