package com.example.githubapi.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.githubapi.R;
import com.example.githubapi.adapters.CustomScrollListener;
import com.example.githubapi.adapters.UsersRecyclerAdapter;
import com.example.githubapi.data.ApiManager;
import com.example.githubapi.model.ListUser;
import com.example.githubapi.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements UsersRecyclerAdapter.OnRvItemClickListener {

    private static final int LIMIT = 10;
    private UsersRecyclerAdapter mRecyclerAdapter;
    private ArrayList<ListUser> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsers = new ArrayList<>();
        initRecyclerView();
        loadDataFromApi(0);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rv_github_users);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        CustomScrollListener scrollListener = new CustomScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadDataFromApi(totalItemsCount);
            }
        };

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(scrollListener);
        mRecyclerAdapter = new UsersRecyclerAdapter(mUsers, this);
        recyclerView.setAdapter(mRecyclerAdapter);
    }

    private void loadDataFromApi(int page) {
        if (NetworkUtils.isNetworkAvailable(this)) {
            Call<List<ListUser>> call = ApiManager.getApiClient().getUsers(page, LIMIT);
            call.enqueue(new Callback<List<ListUser>>() {
                @Override
                public void onResponse(Call<List<ListUser>> call, Response<List<ListUser>> response) {
                    Log.v("TAG", "Success");
                    List<ListUser> users = response.body();
                    if (users != null) {
                        mUsers.addAll(users);
                        mRecyclerAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<List<ListUser>> call, Throwable t) {
                    Log.v("TAG", "Failure : " + t.toString());
                }
            });
        } else {
            Log.v("TAG", "No network connection");
        }
    }

    @Override
    public void onItemClicked(int pos) {
        Intent i = new Intent(MainActivity.this, SingleUserActivity.class);
        i.putExtra(SingleUserActivity.KEY_USER_NAME, mUsers.get(pos).getLogin());
        startActivity(i);
    }
}
