package ru.strongit.myrecycledepg;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.facebook.stetho.Stetho;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import ru.strongit.myrecycledepg.DAO.HelperFactory;
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

        HelperFactory.setHelper(getApplicationContext());

        stetho_init();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://demo3761134.mockable.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        epgApi = retrofit.create(EPGApi.class);
    }

    public static EPGApi getApi() {
        return epgApi;
    }

    private void stetho_init() {
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder;
        initializerBuilder = Stetho.newInitializerBuilder(this);

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
    }

    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
