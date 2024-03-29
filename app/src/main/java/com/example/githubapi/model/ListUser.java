package com.example.githubapi.model;

public class ListUser {
    private String login;
    private String avatar_url;//քանի որ չենք օգտագործել SerializedName, պարտավոր ենք լրացնել էնպես ինչպես նշված է GitHub-ի API-ում

    public ListUser() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
