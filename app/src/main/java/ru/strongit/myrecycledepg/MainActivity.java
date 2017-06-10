package ru.strongit.myrecycledepg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.strongit.myrecycledepg.model.Channel;
import ru.strongit.myrecycledepg.model.EPGModel;


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

                List<Channel> chanels = mEpg.getChannels();
                Log.d(TAG, "onCreate: " + chanels.size());
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<EPGModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Произошла ошибка", Toast.LENGTH_SHORT).show();
            }
        });
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
                Log.d(TAG, "onTouch: "+v.getClass().getName()+": "+curX+" / "+curY);
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
}
