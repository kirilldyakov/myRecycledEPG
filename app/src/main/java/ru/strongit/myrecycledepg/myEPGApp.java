package ru.strongit.myrecycledepg;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import ru.strongit.myrecycledepg.DAO.HelperFactory;


/**
 * Created by user on 07.06.17.
 */

public class myEPGApp extends Application {
    private static EPGApi epgApi;
    private Retrofit retrofit;

    public static class MyClientBuilder {
        protected static OkHttpClient configureClient() {
            OkHttpClient client = null;
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.readTimeout(180, TimeUnit.SECONDS);
            builder.addNetworkInterceptor(new StethoInterceptor());
            client = builder.build();
            return client;
        }

        public static OkHttpClient createClient() {
            return configureClient();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        HelperFactory.setHelper(getApplicationContext());

        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://demo3761134.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(MyClientBuilder.configureClient())
                .build();

        epgApi = retrofit.create(EPGApi.class);
    }

    public static EPGApi getApi() {
        return epgApi;
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
