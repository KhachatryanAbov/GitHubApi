package com.example.githubapi.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleUser {

    @SerializedName("avatar_url")//SerializedName-wum պարտավոր ենք լրացնել դաշտի անունը այնպես ինչպես նշված է GitHub-ի API-ում
    @Expose
    public String avatarUrl;//էս դաշտը արդեն կարող ենք լրացնել այնպես ինչպես ընդունված է Java լեզվում
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("public_repos")
    @Expose
    public Integer publicRepos;
    @SerializedName("followers")
    @Expose
    public Integer followers;

    public SingleUser() {
    }


    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }







    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(Integer publicRepos) {
        this.publicRepos = publicRepos;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }
}
