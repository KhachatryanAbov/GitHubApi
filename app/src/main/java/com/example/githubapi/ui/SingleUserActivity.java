package com.example.githubapi.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.githubapi.R;
import com.example.githubapi.data.ApiManager;
import com.example.githubapi.model.SingleUser;
import com.example.githubapi.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleUserActivity extends AppCompatActivity {

    public static final String KEY_USER_NAME = "key_user_name";//էս էն քին  ա որով պիտի պահենք կամ վերցնենք MainActivity-ից եկող յուզրի նեյմը


    private ImageView mAvatarIv;//յուզրի նկարը կցուցադրենք էս ImageView -ում
    private TextView mTypeTxt;
    private TextView mNameTxt;
    private TextView mLocationTxt;
    private TextView mReposTxt;
    private TextView mFollowersTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user);
        String userName = getIntent().getStringExtra(KEY_USER_NAME);
        initViews();
        loadData(userName);
    }

    private void initViews() {
        mAvatarIv = findViewById(R.id.iv_user_avatar);
        mTypeTxt = findViewById(R.id.tv_type);
        mNameTxt = findViewById(R.id.tv_name);
        mLocationTxt = findViewById(R.id.tv_location);
        mReposTxt = findViewById(R.id.tv_repos);
        mFollowersTxt = findViewById(R.id.tv_followers);
    }

    private void loadData(String userName) {
        if (NetworkUtils.isNetworkAvailable(this)) {//եթե ինտերնետը հասանելի է
            Call<SingleUser> call = ApiManager.getApiClient().getSingleUser(userName);////մեր GitHubService-ից ուզենք userName-ով յուզրի տվյալները
            call.enqueue(new Callback<SingleUser>() {
                @Override
                public void onResponse(Call<SingleUser> call, Response<SingleUser> response) {
                    Log.v("TAG", "Success");
                    SingleUser user = response.body();//եկած պատասխանից վերցնենք մեր user-ին ։դ
                    if (user != null) {
                        fillData(user);//լրացնենք յուզրին ներկայացնող դաշտերը
                    }
                }

                @Override
                public void onFailure(Call<SingleUser> call, Throwable t) {
                    Log.v("TAG", "Failure : " + t.toString());
                }
            });
        } else {
            Log.v("TAG", "No network connection");
        }
    }

    private void fillData(SingleUser user) {
        Glide.with(this)
                .load(user.getAvatarUrl())//Glide-ին տանք մեր յուզրի նկարի URL-ն
                .into(mAvatarIv);//ու ասենք որ պատկերի նկարը mAvatarIv-ում

        mTypeTxt.setText("Type : " + user.getType());
        mNameTxt.setText("Name : " + user.getName());
        mLocationTxt.setText("Location : " + user.getLocation());
        mReposTxt.setText("Repos : " + String.valueOf(user.getPublicRepos()));
        mFollowersTxt.setText("Followers : " + String.valueOf(user.getFollowers()));
    }
}
