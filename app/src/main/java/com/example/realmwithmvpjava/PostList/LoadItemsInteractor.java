package com.example.realmwithmvpjava.PostList;

import android.app.Activity;
import android.util.Log;

import com.example.realmwithmvpjava.DB.PostDataModel;
import com.example.realmwithmvpjava.DB.RealmHelper;
import com.example.realmwithmvpjava.Utilies.APIClient;
import com.example.realmwithmvpjava.Utilies.ApiInterface;
import com.example.realmwithmvpjava.Utilies.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoadItemsInteractor implements MVPLoadItemInteractor {

    private final static String TAG = LoadItemsInteractor.class.getSimpleName();
    private Activity context;
    boolean successStatus = false;
    private OnFinishedListener finishedListener;

    public LoadItemsInteractor(Activity context) {
        this.context = context;
    }

    @Override
    public void loadItems(final OnFinishedListener listener) {
        finishedListener = listener;
        loadData();
    }

    private void loadData() {
        if (Utility.isNetworkAvailable(context)) {
            getDataFromAPI();
        } else {
            finishedListener.onFinished(false);
        }
    }


    public void getDataFromAPI() {
        ApiInterface client = APIClient.createService(ApiInterface.class);
        Call<List<PostDataModel>> call = client.getModelData();
        successStatus = false;
        call.enqueue(new Callback<List<PostDataModel>>() {
            @Override
            public void onResponse(Call<List<PostDataModel>> call, Response<List<PostDataModel>> response) {
                Log.i(TAG, "onAPIResponse: " + response);
                com.example.realmwithmvpjava.Utilies.Log.d(TAG, "Debug Custom Logger Class");
                com.example.realmwithmvpjava.Utilies.Log.e(TAG, "Error Custom Logger Class");
                com.example.realmwithmvpjava.Utilies.Log.i(TAG, "Info Custom Logger Class");
                Log.d(TAG, "Testing debug log");
                if (response.isSuccessful()) {
                    List<PostDataModel> postDataModelList = new ArrayList<>();
                    postDataModelList = response.body();
                    try {
                        RealmHelper.with(context).clearAll();
                        RealmHelper.with(context).insertOrUpdateDatas(postDataModelList);
                        finishedListener.onFinished(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        finishedListener.onFinished(false);
                        Log.e(TAG, "onAPIResponse Error: " + e.getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<PostDataModel>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                finishedListener.onFinished(false);
            }
        });
    }
}
