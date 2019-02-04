package com.example.githubapi.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.githubapi.R;
import com.example.githubapi.model.ListUser;

import java.util.List;


public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<ListUser> mData;
    private OnRvItemClickListener mOnRvItemClickListener;

    public UsersRecyclerAdapter(List<ListUser> mData, OnRvItemClickListener mOnRvItemClickListener) {
        this.mData = mData;
        this.mOnRvItemClickListener = mOnRvItemClickListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UserViewHolder holder, int position) {
        ListUser user = mData.get(position);

        holder.fullName.setText(user.getLogin());
        Glide.with(holder.itemView)
                .load(user.getAvatar_url())
                .into(holder.avatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRvItemClickListener.onItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView fullName;
        private ImageView avatar;

        private UserViewHolder(final View userView) {
            super(userView);
            fullName = userView.findViewById(R.id.full_name_view_item);
            avatar = userView.findViewById(R.id.avatar_view_item);
        }
    }

    public interface OnRvItemClickListener {
        void onItemClicked(int pos);
    }
}