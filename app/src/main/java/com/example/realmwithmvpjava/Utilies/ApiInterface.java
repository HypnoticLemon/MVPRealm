package com.example.realmwithmvpjava.Utilies;

import com.example.realmwithmvpjava.DB.PostDataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("posts")
    Call<List<PostDataModel>> getModelData();
}
