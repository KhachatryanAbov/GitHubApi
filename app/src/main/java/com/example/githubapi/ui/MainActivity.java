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

    private static final int LIMIT = 10;//ցույց ենք տալիս թե մաքսիմում քանի այթմ ենք ներբեռնելու ամեն այթմից
    private UsersRecyclerAdapter mRecyclerAdapter;//մեր յուզրների լիստի ադապտերն ա
    private ArrayList<ListUser> mUsers;//mUsers-ում հավաքելու ենք բոլոր յուզրներին

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUsers = new ArrayList<>();
        initRecyclerView();
        loadDataFromApi(0);//ամենասկզբից ներբեռնենք 0-րդ էջի յուզրներին
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rv_github_users);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        CustomScrollListener scrollListener = new CustomScrollListener(layoutManager) {//մեր հատուկ ScrollListener-ն ա որը օգնելու ա pagination-ի հարցում
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadDataFromApi(totalItemsCount);//ամեն անգամ երբ լիսթը հասնում ա վերջնակետին կանչվում  ա էս մեթոդը totalItemsCount ցույց ա տալիս թե էտ պահին քանի այթմ կա լիսթում
            }
        };

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(scrollListener);//մեր recyclerView-ին ասենք որ աշխատի մեր scrollListener-ի հետ ։դ
        mRecyclerAdapter = new UsersRecyclerAdapter(mUsers, this);
        recyclerView.setAdapter(mRecyclerAdapter);
    }

    private void loadDataFromApi(int page) {
        if (NetworkUtils.isNetworkAvailable(this)) {//եթե ինտերնետը հասանելի է
            Call<List<ListUser>> call = ApiManager.getApiClient().getUsers(page, LIMIT);//մեր GitHubService-ից ուզենք յուզրների լիսթը page-ից սկսած 10 հատ
            call.enqueue(new Callback<List<ListUser>>() {
                @Override
                public void onResponse(Call<List<ListUser>> call, Response<List<ListUser>> response) {
                    Log.v("TAG", "Success");
                    List<ListUser> users = response.body();//եկած պատասխանից վերցնենք յուզրների լիսթը
                    if (users != null) {
                        mUsers.addAll(users);//նոր ստացված յուզրները ավելացնենք մեր լիսթում
                        mRecyclerAdapter.notifyDataSetChanged();//թարմացնենք յուզրների ցուցադրվող լիսթը
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
    public void onItemClicked(int pos) {//եթե կլիկ ա արված ինչ որ այթմի վրա
        Intent i = new Intent(MainActivity.this, SingleUserActivity.class);//ստեղծենք SingleUserActivity-ին տանող ինթենթ
        i.putExtra(SingleUserActivity.KEY_USER_NAME, mUsers.get(pos).getLogin());//ինթենթին փոխանցենք ընտրված յուզրի անունը
        startActivity(i);//
    }
}
