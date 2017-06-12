package ru.strongit.myrecycledepg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.strongit.myrecycledepg.DAO.HelperFactory;
import ru.strongit.myrecycledepg.DAO.ChannelDB;
import ru.strongit.myrecycledepg.DAO.ScheduleDB;
import ru.strongit.myrecycledepg.model.Channel;
import ru.strongit.myrecycledepg.model.EPGModel;
import ru.strongit.myrecycledepg.model.Schedule;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {
    String TAG = "Main";

    EPGModel mEpg;

    RecyclerView recyclerView;
    private EPGAdapter epg_adapter;
    private LinearLayoutManager layoutManager;


    private HorizontalScrollView HScroll;
    private ScrollView VScroll;

    private float mx = 0f;
    private float my = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView) findViewById(R.id.cannels_recycle_view);

        HScroll = (HorizontalScrollView) findViewById(R.id.HScroll);
        VScroll = (ScrollView) findViewById(R.id.VScroll);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        TextView tv3 = (TextView) findViewById(R.id.btn3);
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: btn3 click");
            }
        });

        //LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        recyclerView.setOnTouchListener(this);
        VScroll.setOnTouchListener(this);
//        HScroll.setOnTouchListener(this);

        // epg_adapter = new EPGAdapter(mEpg);
        //Тут нажатие

        // recyclerView.setAdapter(epg_adapter);

        //epg_adapter.onVisitSelectedListener = new OnVisitSelectedListener() {
//            @Override
//            public void onVisitSelected(String id) {
//                selectMarkersByTag(id);
//            }
//        };

        retrofitGetData();


    }

    private void retrofitGetData() {

        myEPGApp.getApi().getEPG().enqueue(new Callback<EPGModel>() {
            @Override
            public void onResponse(Call<EPGModel> call, Response<EPGModel> response) {
                mEpg = response.body();

                recyclerView.setAdapter(new EPGAdapter(mEpg));
                //mEpg = response.body();


                for (Channel chnl : mEpg.getChannels()) {

                    createChannelDB(chnl);

                    for (Schedule schdl : chnl.getSchedule()) {
                        createScheduleDB(chnl, schdl);
                    }
                }


                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<EPGModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void createScheduleDB(Channel chnl, Schedule schdl) {
        try {
            ScheduleDB schdlDB = new ScheduleDB();
            schdlDB.setChannel_id(Integer.parseInt(chnl.getId()));
            HelperFactory.getHelper().getScheduleDAO().create(schdlDB);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void createChannelDB(Channel chnl) {
        try {
            ChannelDB channelDB = new ChannelDB(
                    Integer.parseInt(chnl.getId())
                    , chnl.getTitle()
                    , Integer.parseInt(chnl.getEpgChannelId()));
            HelperFactory.getHelper().getChannelDAO().create(channelDB);
        } catch (Exception e) {
            Log.d(TAG, "onResponse: " + e.getMessage());
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        switch (v.getId()) {
//            case R.id.cannels_recycle_view:
//                Log.d(TAG, "RR");
//                break;
//            case R.id.HScroll:
//                Log.d(TAG, "HS");
//                break;
//            case R.id.VScroll:
//                Log.d(TAG, "VS");
//                break;
//        }

        float curX, curY;


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mx = event.getX();
                my = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                curY = event.getY();
                VScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                HScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                mx = curX;
                my = curY;
                Log.d(TAG, "onTouch: " + v.getClass().getName() + ": " + curX + " / " + curY);
                break;
            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                VScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                HScroll.scrollBy((int) (mx - curX), (int) (my - curY));
                break;
        }

        return false;

    }

    @Override
    public void onBackPressed() {
        try {
            ChannelDB channel = new ChannelDB(123, "Название", 456);

            HelperFactory.getHelper().getChannelDAO().create(channel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
