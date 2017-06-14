package ru.strongit.myrecycledepg;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    String TAG = "LOG_TAG";


    long currentDT = 1491822000; //System.currentTimeMillis();

    final long TWO_HOURS = 2 * 60 * 60;
    final long ONE_HOUR = 1 * 60 * 60;

    RecyclerView recyclerView;
    private EPGAdapter epg_adapter;
    private LinearLayoutManager layoutManager;


    private HorizontalScrollView HScroll;
    private ScrollView VScrollLogo;
    private ScrollView VScrollTable;


    private MyBroadcastReceiver mMyBroadcastReceiver;

    private float mx = 0f;
    private float my = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intentMyIntentService = new Intent(this, EPGLoaderService.class);
        startService(intentMyIntentService);

        // регистрируем BroadcastReceiver
        mMyBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(
                EPGLoaderService.ACTION_EPGLOADERSERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(mMyBroadcastReceiver, intentFilter);


        String strDate = "10.04.17 15:00";
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
        try {
            currentDT = formatter.parse(strDate).getTime();
            currentDT /= 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        init_views();

//        List<ChannelDB> ActiveChList;
//
//        try {
//            ActiveChList = HelperFactory
//                    .getHelper()
//                    .getScheduleDAO()
//                    .getActiveChanennelsOffset(currentDT, ONE_HOUR);
//            Log.d(TAG, "onCreate: " + ActiveChList.size());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


        databaseGetData(currentDT, ONE_HOUR);

    }

    @Override
    protected void onStart() {
        super.onStart();
        int pixel = this.getWindowManager().getDefaultDisplay().getWidth();
        int dp = pixel / (int) getResources().getDisplayMetrics().density;
        int x = HScroll.getMaxScrollAmount();
    }

    private void databaseGetData(long currentDT, long offset) {

        List<ChannelDB> ActiveChList = getActiveChannelsDBs(currentDT, offset);

        addLogos(ActiveChList);

        recyclerView.setAdapter(new EPGAdapterDB(ActiveChList, currentDT, offset));

        recyclerView.getAdapter().notifyDataSetChanged();
    }

    private List<ChannelDB> getActiveChannelsDBs(long currentDT, long offset) {
        List<ChannelDB> ActiveChList = new ArrayList<>();

        try {
            ActiveChList = HelperFactory
                    .getHelper()
                    .getScheduleDAO()
                    .getActiveChanennelsOffset(currentDT, offset);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ActiveChList;
    }

    private void init_views() {
        recyclerView = (RecyclerView) findViewById(R.id.cannels_recycle_view);


        HScroll = (HorizontalScrollView) findViewById(R.id.HScroll);
        VScrollLogo = (ScrollView) findViewById(R.id.VScroll_logo);
        VScrollTable = (ScrollView) findViewById(R.id.VScroll_table);


        recyclerView.setNestedScrollingEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            VScrollTable.setNestedScrollingEnabled(false);
            HScroll.setNestedScrollingEnabled(false);
        }

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        //recyclerView.setOnTouchListener(this);
        HScroll.setOnTouchListener(this);
        VScrollLogo.setOnTouchListener(this);
        //VScrollTable.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Log.d(TAG, v.getClass().getName());


        float curX = 0, curY = 0;


        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                mx = event.getX();
                my = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                curX = event.getX();
                curY = event.getY();
                //VScrollTable.scrollBy((int) (mx - curX), (int) (my - curY));
                VScrollTable.scrollBy((int) (mx - curX), (int) (my - curY));
                VScrollLogo.scrollTo(0, VScrollTable.getScrollY());

                mx = curX;
                my = curY;
                break;
            case MotionEvent.ACTION_UP:
                curX = event.getX();
                curY = event.getY();
                VScrollTable.scrollBy((int) (mx - curX), (int) (my - curY));
                //VScrollLogo.scrollBy((int) (mx - curX), (int) (my - curY));
                VScrollLogo.scrollTo(0, VScrollTable.getScrollY());

                break;
        }


        //Log.d(TAG, "onTouch: "+event.getAction() + v.getClass().getName() + ": " + (int)curX + " / " + (int)curY);
        return false;

    }

    @Override
    public void onBackPressed() {
//        long l = 1491811200000L;
//
//        Date dt = new Date(l);
//        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy HH.mm.ss");
//        Log.d(TAG, "onBackPressed: "+sdf.format(dt)+" "+l);
//
//        final long l1=1491832200000L;
//        Date dt1 = new Date(l1);
//        Log.d(TAG, "onBackPressed: "+sdf.format(dt1)+" "+l1);
//
//        final long l2 = System.currentTimeMillis();
//        Date dt2 = new  Date(l2);
//        Log.d(TAG, "onBackPressed: "+sdf.format(dt2)+" "+l2);


    }


    private void addLogos(List<ChannelDB> lst) {
        Resources r = getResources();

        int heigh_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , 50, r.getDisplayMetrics());

        int margin_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , 4, r.getDisplayMetrics());

        LinearLayout ll_logos = (LinearLayout) findViewById(R.id.ll_logos);
        ll_logos.removeAllViews();

        for (ChannelDB chnl : lst) {
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heigh_px);
            tvParams.setMargins(margin_px, 0, margin_px, margin_px); //dp to px
            tvParams.gravity = Gravity.CENTER;
            tv.setLayoutParams(tvParams);
            tv.setBackgroundColor(Color.WHITE);
            tv.setText(chnl.getTitle());
            tv.setTag(chnl.getEpg_channel_id());
            ll_logos.addView(tv);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView) v;
                    Log.d(TAG, "onClick:" + tv.getText() + " " + (int) tv.getTag());
                }
            });
        }
    }


    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra(EPGLoaderService.EXTRA_KEY_OUT);
            Log.d(TAG, "onReceive: "+result);
            //Context ctx = getApplicationContext();
            Toast.makeText(context, "Программа обновлена", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mMyBroadcastReceiver);
    }
}


