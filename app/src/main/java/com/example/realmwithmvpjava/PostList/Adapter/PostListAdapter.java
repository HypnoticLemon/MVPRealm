package com.example.realmwithmvpjava.PostList.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.realmwithmvpjava.DB.PostDataModel;
import com.example.realmwithmvpjava.PostList.PostListPresenter;
import com.example.realmwithmvpjava.R;

import java.util.List;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostRowHolder> {

    private Context context;
    private List<PostDataModel> postListModels;
    private PostListPresenter postListPresenter;

    public PostListAdapter(Context context, List<PostDataModel> listModelList, PostListPresenter postListPresenter) {
        this.postListModels = listModelList;
        this.context = context;
        this.postListPresenter = postListPresenter;
    }

    @NonNull
    @Override
    public PostRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostRowHolder(LayoutInflater.from(context).inflate(R.layout.post_single_row_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostRowHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txtBody.setText(postListModels.get(position).getBody());
        holder.txtTitle.setText(postListModels.get(position).getTitle());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postListPresenter.onItemClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return (postListModels != null ? postListModels.size() : 0);
    }

    public class PostRowHolder extends RecyclerView.ViewHolder {

        private TextView txtBody, txtTitle;
        private CardView cardView;


        public PostRowHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtBody = itemView.findViewById(R.id.txtBody);
        }
    }
}
