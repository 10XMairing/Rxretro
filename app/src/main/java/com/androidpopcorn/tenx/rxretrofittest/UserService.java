package com.androidpopcorn.tenx.rxretrofittest;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @GET("/users")
    public Observable<List<User>> getUsers(
            @Query("per_page") int per_page,
            @Query("page") int page
    );


    @GET("/users/{username}")
    public Observable<User> getUser(@Path("username") String username);
}
