package ru.strongit.myrecycledepg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.strongit.myrecycledepg.DAO.HelperFactory;
import ru.strongit.myrecycledepg.DAO.ChannelDB;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    final long TWO_HOURS = 2 * 60 * 60;
    final long ONE_HOUR = 1 * 60 * 60;

    long currentDT = 1491822000; //System.currentTimeMillis();
    long offset = TWO_HOURS;


    private RecyclerView mRrecyclerView;
    private LinearLayoutManager mLayoutManager;

    private HorizontalScrollView mHScroll;
    private ScrollView mVScrollLogo;
    private ScrollView mVScrollTable;

    private MyBroadcastReceiver mMyBroadcastReceiver;

    private float mx = 0f;
    private float my = 0f;

    /**
     * Конструктор класс MainActivity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);



        init_views();

        currentDT = (long) System.currentTimeMillis() / 1000;

        setCurrentDTtoDemoDate(); //Потом удалить

        generateEPG(currentDT, offset);

        init_EPGLoad_Service();

        init_BroadcastReciever();
    }

    /**
     * Присваивает значение демонстрационной даты
     */
    private void setCurrentDTtoDemoDate() {
        String strDate = "10.04.17 15:00";
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
        try {
            currentDT = formatter.parse(strDate).getTime();
            currentDT /= 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Инициализация класса сервиса загрузки данных
     */
    private void init_EPGLoad_Service() {

        Intent intentMyIntentService = new Intent(this, EPGLoaderService.class);

        startService(intentMyIntentService);

    }


    /**
     * Регистрируем BroadcastReceiver для передачи сообщений в MainActivity
     */
    private void init_BroadcastReciever() {

        mMyBroadcastReceiver = new MyBroadcastReceiver();

        IntentFilter intentFilter = new IntentFilter(EPGLoaderService.ACTION_EPGLOADERSERVICE);

        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);

        registerReceiver(mMyBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        int pixel = this.getWindowManager().getDefaultDisplay().getWidth();

        int dp = pixel / (int) getResources().getDisplayMetrics().density;

        int x = mHScroll.getMaxScrollAmount();

    }

    /**
     * Генерирует представление программы передач
     *
     * @param currentDT
     * @param offset
     */
    private void generateEPG(long currentDT, long offset) {

        List<ChannelDB> ActiveChList = getActiveChannelsDBs(currentDT, offset);

        addLogos(ActiveChList);

        mRrecyclerView.setAdapter(new EPGAdapterDB(ActiveChList, currentDT, offset));

        mRrecyclerView.getAdapter().notifyDataSetChanged();
    }


    /**
     * Возвращает список активных каналов на дату currentDT +- смещение
     *
     * @param currentDT
     * @param offset
     * @return
     */
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

    /**
     * Инициализация графических компонент
     */
    private void init_views() {
        mRrecyclerView = (RecyclerView) findViewById(R.id.cannels_recycle_view);

        mHScroll = (HorizontalScrollView) findViewById(R.id.HScroll);

        mVScrollLogo = (ScrollView) findViewById(R.id.VScroll_logo);

        mVScrollTable = (ScrollView) findViewById(R.id.VScroll_table);

        mRrecyclerView.setNestedScrollingEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            mVScrollTable.setNestedScrollingEnabled(false);

            mHScroll.setNestedScrollingEnabled(false);
        }

        mLayoutManager = new LinearLayoutManager(this);

        mRrecyclerView.setLayoutManager(mLayoutManager);

        mHScroll.setOnTouchListener(this);

        mVScrollLogo.setOnTouchListener(this);
        //mVScrollTable.setOnTouchListener(this);
        mVScrollTable.setOnTouchListener(null);
    }

    /**
     * Реализует дагональный скролл
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Log.d("TAG", "onTouch: "+v.getClass().getName());

        float curX = 0, curY = 0;

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                mx = event.getX();

                my = event.getY();

                break;

            case MotionEvent.ACTION_MOVE:

                curX = event.getX();

                curY = event.getY();


                mVScrollTable.scrollBy((int) (mx - curX), (int) (my - curY));

                mVScrollLogo.scrollTo(0, mVScrollTable.getScrollY());

                mx = curX;

                my = curY;

                break;
            case MotionEvent.ACTION_UP:

                curX = event.getX();

                curY = event.getY();

                mVScrollTable.scrollBy((int) (mx - curX), (int) (my - curY));

                //mVScrollLogo.scrollBy((int) (mx - curX), (int) (my - curY));
                mVScrollLogo.scrollTo(0, mVScrollTable.getScrollY());

                break;
        }

        return false;

    }

    @Override
    public void onBackPressed() {

    }

    /**
     * Добавляет логотипы каналов
     *
     * @param lst список каналов
     */
    private void addLogos(List<ChannelDB> lst) {

        Resources r = getResources();

        final int heigh_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , 50, r.getDisplayMetrics());

        final int margin_px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                , 4, r.getDisplayMetrics());

        LinearLayout mLLLogos = (LinearLayout) findViewById(R.id.ll_logos);

        mLLLogos.removeAllViews();

        for (ChannelDB channelDB : lst) {

            TextView iconAsTextView = getIconAsTextView(channelDB, heigh_px, margin_px);

            mLLLogos.addView(iconAsTextView);
        }
    }

    /**
     * Возвращает сгенерированную иконку TextView
     *
     * @param channelDB
     * @param heigh_px
     * @param margin_px
     * @return
     */
    @NonNull
    private TextView getIconAsTextView(ChannelDB channelDB, int heigh_px, int margin_px) {
        TextView tv = new TextView(this);

        LinearLayout.LayoutParams tvParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heigh_px);

        tvParams.setMargins(margin_px, 0, margin_px, margin_px);

        tvParams.gravity = Gravity.CENTER;

        tv.setLayoutParams(tvParams);

        tv.setBackgroundColor(Color.WHITE);

        tv.setText(channelDB.getTitle());

        tv.setTag(channelDB.getEpg_channel_id());


        tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView tv = (TextView) v;

                Toast.makeText(
                        MainActivity.this
                        , "Нажата иконка \'" + tv.getText() + "\' " + (int) tv.getTag()
                        , Toast.LENGTH_SHORT).show();


            }
        });
        return tv;
    }


    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            String result = intent.getStringExtra(EPGLoaderService.EXTRA_KEY_OUT);

            Toast.makeText(context, "Лоадер:" + result, Toast.LENGTH_SHORT).show();

            Log.d("LOG_TAG",  "Лоадер:" + result);

            switch (result) {
                case EPGLoaderService.EXTRA_LOADING_STARTED:
                    break;
                case EPGLoaderService.EXTRA_JSON_LOADED:
                    break;
                case EPGLoaderService.EXTRA_LOADING_STOPPED:
                    generateEPG(currentDT, offset);
                    break;
                case EPGLoaderService.EXTRA_LOADING_ERROR:
                    break;
            }


        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        unregisterReceiver(mMyBroadcastReceiver);

    }
}


