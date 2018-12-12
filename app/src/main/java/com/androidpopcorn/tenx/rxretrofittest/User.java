package com.androidpopcorn.tenx.rxretrofittest;

public class User {
    private String login;
    private long id;
    private String url;

    public String getLogin() {
        return login;
    }

    public long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Login :" +getLogin()+" Url : " + getUrl()+" Id :" + getId();
    }
}
