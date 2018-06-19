package com.example.realmwithmvpjava.PostList;


import android.app.Activity;
import android.app.Fragment;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.realmwithmvpjava.DB.PostDataModel;
import com.example.realmwithmvpjava.Main.MainActivity;
import com.example.realmwithmvpjava.PostList.Adapter.PostListAdapter;
import com.example.realmwithmvpjava.R;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class PostListFragment extends Fragment implements ListFragmentView {

    private ProgressBar listProgressbar;
    private RecyclerView listRecyclerView;
    private PostListPresenter postListPresenter;
    private Activity context;
    private final static String TAG = PostListFragment.class.getSimpleName();


    public PostListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listProgressbar = view.findViewById(R.id.listProgressbar);
        listProgressbar.setVisibility(View.GONE);
        listRecyclerView = view.findViewById(R.id.listRecyclerView);
        listRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        postListPresenter = new PostListPresenter(context, this, new LoadItemsInteractor(getActivity()));
        postListPresenter.onResume();
    }


    @Override
    public void onStart() {
        postListPresenter.onStartService();
        super.onStart();
    }

    @Override
    public void onStop() {
        //    postListPresenter.onStopService();
        super.onStop();
    }

    @Override
    public void showProgress() {
        listProgressbar.setVisibility(View.VISIBLE);
        listRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        listProgressbar.setVisibility(View.GONE);
        listRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItems(List<PostDataModel> listModels) {
        if (listModels.size() > 0) {
            listRecyclerView.setAdapter(new PostListAdapter(context, listModels, postListPresenter));
        } else {
            Toast.makeText(context, "Something went wrong,Please try again!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHandler() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        postListPresenter.onDestroy();
    }


}
