package com.androidpopcorn.tenx.rxretrofittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    UserService userService;

 CompositeDisposable compo ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compo = new CompositeDisposable();
        userService  = ServiceGenerator.createService(UserService.class);
//        Call<User> callSync = userService.getUser("10XMairing");
//
//
//
////        try {
////            retrofit2.Response<User> response = callSync.execute();
////        }catch (IOException e){
////            e.printStackTrace();
////        }
//
//        callSync.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
//
//                User user = response.body();
//                assert user != null;
//                Log.d(TAG,user.toString());
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Log.d(TAG, t.getMessage());
//            }
//        });



        //rx

        String[] names = {"one", "two", "three", "four"};

        Disposable d = Observable.just(names).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap(new Function<String[], ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String[] strings) throws Exception {
                return Observable.fromArray(strings);
            }
        }).subscribe(name -> Log.d(TAG, name.toString()));


        Observable<List<User>> usersObservable  = userService.getUsers(10, 1);
         usersObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).
                 flatMap(new Function<List<User>, Observable<User>>() {
                     @Override
                     public Observable<User> apply(List<User> users) throws Exception {
                         return Observable.fromIterable(users);
                     }
                 }).map(new Function<User, User>() {
             @Override
             public User apply(User user) throws Exception {
                 user.setLogin(user.getLogin()+"@androidpopcorn.com");
                 return user;
             }
         }).subscribe(createUserObs());





    }

    public Observer<User> createUserObs() {
        return   new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {
                Log.d(TAG, user.toString());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "COMPLETED");
            }
        };

    }


    public Observer<List<User>> createUsersObserver() {
        return   new Observer<List<User>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<User> users) {
                Log.d(TAG, ""+users.size());
                Log.d(TAG, ""+users.get(users.size()-1));

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "COMPLETED");
            }
        };

    }
}
