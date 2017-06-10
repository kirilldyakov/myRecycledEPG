package ru.strongit.myrecycledepg;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import ru.strongit.myrecycledepg.EPGApi;

/**
 * Created by user on 07.06.17.
 */

public class myEPGApp extends Application {
    private static EPGApi epgApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://demo3761134.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        epgApi = retrofit.create(EPGApi.class);
    }

    public static EPGApi getApi() {
        return epgApi;
    }
}
