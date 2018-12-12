package com.androidpopcorn.tenx.rxretrofittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        userService  = ServiceGenerator.createService(UserService.class);
        Call<User> callSync = userService.getUser("10XMairing");



//        try {
//            retrofit2.Response<User> response = callSync.execute();
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        callSync.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {

                User user = response.body();
                assert user != null;
                Log.d(TAG,user.toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }
}
