package com.example.githubapi.data;

import com.example.githubapi.model.ListUser;
import com.example.githubapi.model.SingleUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {

    @GET("users")
    Call<List<ListUser>> getUsers(@Query("since") int offset, @Query("per_page") int limit);


    @GET("users/{userName}")
    Call<SingleUser> getSingleUser(@Path("userName") String name);

}
