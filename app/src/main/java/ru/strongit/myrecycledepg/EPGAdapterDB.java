package ru.strongit.myrecycledepg;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.strongit.myrecycledepg.DAO.ChannelDB;
import ru.strongit.myrecycledepg.DAO.HelperFactory;
import ru.strongit.myrecycledepg.DAO.ScheduleDB;
import ru.strongit.myrecycledepg.model.Channel;
import ru.strongit.myrecycledepg.model.EPGModel;

/**
 * Класс адаптера (визиты-организации)
 */
public class EPGAdapterDB extends RecyclerView.Adapter<EPGAdapterDB.ViewHolder> {

//    //Интерфейс события выбора визита
//    public interface OnVisitSelectedListener {
//        void onVisitSelected(String id);
//    }
//
//    public OnVisitSelectedListener onVisitSelectedListener;

    private List<ChannelDB> channelDBs;
    private RelativeLayout rlChannelRow;
    private long currentDT;
    private long offset;

    //конструктор
    public EPGAdapterDB(List<ChannelDB> channelDBs, long currentDT, long offset){ //}, List<ScheduleDB> scheduleDBs) {
        this.channelDBs = channelDBs;
        this.currentDT = currentDT;
        this.offset = offset;
        //this.scheduleDBs = scheduleDBs;
    }


    //переопределенная процедура на создание холдера
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater
                        .from(parent
                        .getContext())
                        .inflate(R.layout.channel_item, parent, false);
        return new ViewHolder(v);
    }

    //переопределенная процедура на "связывание" данных и лейаута
    @Override
    public void onBindViewHolder(EPGAdapterDB.ViewHolder holder, int position) {

        ChannelDB channel = channelDBs.get(position);
        rlChannelRow = (RelativeLayout) holder.itemView.findViewById(R.id.rlChannelRow);
        rlChannelRow.removeAllViews();

        List<ScheduleDB> actSchedules_ids = new ArrayList<>();
        try {
            actSchedules_ids =
                    HelperFactory.getHelper().getScheduleDAO()
                            .getActiveSchedules(channel.getEpg_channel_id(), currentDT, offset);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (ScheduleDB schdl: actSchedules_ids){
            TextView tv = new TextView(this.rlChannelRow.getContext());

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, RelativeLayout.LayoutParams.MATCH_PARENT);
            //tvParams.setMargins(margin_px, 0, margin_px, margin_px); //dp to px
            //llParams= Gravity.CENTER;
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

            params.setMargins(250, 0, 0, 0);


            tv.setLayoutParams(params);


            tv.setBackgroundColor(Color.BLUE);
            tv.setText(schdl.getTitle());
            long offStart = schdl.getStart()-currentDT;
            long offEnd = schdl.getEnd()-currentDT;
            Log.d("LOG_TAG", "offset: "+offStart+"/"+offEnd);

            //tv.setTag(chnl.getEpg_channel_id());
            rlChannelRow.addView(tv);
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            holder.tvChannel.setText(Html.fromHtml(channel.getTitle()
//                    , Html.FROM_HTML_MODE_LEGACY));
////            holder.mOrg.setText(Html.fromHtml(org != null ? org.getTitle() : "---"
////                    , Html.FROM_HTML_MODE_LEGACY));
//        } else {
//            holder.tvChannel.setText(Html.fromHtml(channel.getTitle())); //+longStrToDate(channel.getSchedule().get(0).getStart())
////            holder.mOrg.setText(Html.fromHtml(org != null ? org.getTitle() : "---"));
//
//        }
//        try {
//            holder.itemView.setTag(org.getOrganizationId());
//
//            holder.mOrg.setTag(org.getOrganizationId());
//            if (selectedItems.get(position, false))
//                holder.itemView.setBackgroundColor(Color.GREEN);
//            else
//                holder.itemView.setBackgroundColor(Color.TRANSPARENT);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

//    //Выделяет объект в списке по указанному id
//    void selectListItem(String id) {
//        selectedItems.clear();
//        for (int i = 0; i < mVisits.size(); i++) {
//            if (mVisits.get(i).getOrganizationId().equals(id)) {
//                selectedItems.put(i, true);
//            }
//        }
//        this.notifyDataSetChanged();
//    }

    public String longStrToDate(String s){
    long l = Long.valueOf(s);
        Date d = new Date(l*1000);
        SimpleDateFormat sdf =new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        return sdf.format(d);
    }

    //Возвращает количество объектов в mVisit
    @Override
    public int getItemCount() {
        if (channelDBs == null)
            return 0;
        return channelDBs.size();
    }

    //Вложенный класс ViewHolder. Используется для наполнения списка данными
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvChannel;
        RelativeLayout rlChannelRow;
//        LinearLayout myBackground;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //tvChannel = (TextView) itemView.findViewById(R.id.channel_title);
            //myBackground = (LinearLayout) itemView.findViewById(R.id.myBackground);
            rlChannelRow = (RelativeLayout)itemView.findViewById(R.id.rlChannelRow);


        }

        @Override
        public void onClick(View v) {
            Log.d("LOG_TAG", "onClick: "+v.toString());

//            selectedItems.clear();
//            if (selectedItems.get(getAdapterPosition(), false)) {
//                selectedItems.delete(getAdapterPosition());
//            } else {
//                selectedItems.put(getAdapterPosition(), true);
//            }
            notifyDataSetChanged();
            //onVisitSelectedListener.onVisitSelected(v.getTag().toString()); // fire callback
        }

    }
}

