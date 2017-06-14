package ru.strongit.myrecycledepg;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import org.json.JSONException;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.strongit.myrecycledepg.DAO.HelperFactory;
import ru.strongit.myrecycledepg.model.Channel;
import ru.strongit.myrecycledepg.model.EPGModel;
import ru.strongit.myrecycledepg.model.Schedule;


public class EPGLoaderService extends IntentService {



    public static final String ACTION_EPGLOADERSERVICE = "ru.strongit.myrecycledepg.intentservice.RESPONSE";
    public static final String EXTRA_KEY_OUT = "EXTRA_OUT";
    String extraOut = "Кота накормили, погладили и поиграли с ним";

    public EPGLoaderService() {

        super("EPGLoaderService");

    }

    @Override
    public void onCreate() {

        // TODO Auto-generated method stub
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final Timer t = new Timer();

        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {

                retrofitGetData();


            }



        }, 0,
                20 * 1000); // 4 * 60 * 60 * 1000 = 4 часа

    }


    private void retrofitGetData() {

        myEPGApp.getApi().getEPG().enqueue(new Callback<EPGModel>() {

            @Override
            public void onResponse(Call<EPGModel> call, Response<EPGModel> response) {
                EPGModel mEpg = response.body();

                //recyclerView.setAdapter(new EPGAdapter(mEpg));
                //mEpg = response.body();

                for (Channel chnl : mEpg.getChannels()) {
                    try {

                        HelperFactory.getHelper().getChannelDAO().createChannelDB(chnl);

                    } catch (SQLException e) {

                        e.printStackTrace();

                    }
                    for (Schedule schdl : chnl.getSchedule()) {

                        try {

                            HelperFactory.getHelper().getScheduleDAO().createScheduleDB(schdl);

                        } catch (SQLException e) {

                            e.printStackTrace();

                        }
                    }
                }

                //recyclerView.getAdapter().notifyDataSetChanged();
                Intent responseIntent = new Intent();
                responseIntent.setAction(ACTION_EPGLOADERSERVICE);
                responseIntent.addCategory(Intent.CATEGORY_DEFAULT);
                responseIntent.putExtra(EXTRA_KEY_OUT, extraOut);
                sendBroadcast(responseIntent);
            }

            @Override
            public void onFailure(Call<EPGModel> call, Throwable t) {
                //Toast.makeText(MainActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
