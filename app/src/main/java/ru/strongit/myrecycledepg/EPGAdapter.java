package ru.strongit.myrecycledepg;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Date;
import java.text.SimpleDateFormat;

import ru.strongit.myrecycledepg.model.Channel;
import ru.strongit.myrecycledepg.model.EPGModel;

/**
 * Класс адаптера (визиты-организации)
 */
public class EPGAdapter extends RecyclerView.Adapter<EPGAdapter.ViewHolder> {

//    //Интерфейс события выбора визита
//    public interface OnVisitSelectedListener {
//        void onVisitSelected(String id);
//    }
//
//    public OnVisitSelectedListener onVisitSelectedListener;

    private EPGModel mepg;


//    //Массив хранеия выделенных элементов
//    private SparseBooleanArray selectedItems = new SparseBooleanArray();


    //конструктор
    public EPGAdapter(EPGModel epg) {
        this.mepg = epg;
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
    public void onBindViewHolder(EPGAdapter.ViewHolder holder, int position) {

        Channel channel = mepg.getChannels().get(position);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvChannel.setText(Html.fromHtml(channel.getTitle()
                    , Html.FROM_HTML_MODE_LEGACY));
//            holder.mOrg.setText(Html.fromHtml(org != null ? org.getTitle() : "---"
//                    , Html.FROM_HTML_MODE_LEGACY));
        } else {
            holder.tvChannel.setText(Html.fromHtml(channel.getTitle()+channel.getTitle()+channel.getTitle())); //+longStrToDate(channel.getSchedule().get(0).getStart())
//            holder.mOrg.setText(Html.fromHtml(org != null ? org.getTitle() : "---"));

        }
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
        if (mepg == null)
            return 0;
        return mepg.getChannels().size();
    }

    //Вложенный класс ViewHolder. Используется для наполнения списка данными
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvChannel;
//        LinearLayout myBackground;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvChannel = (TextView) itemView.findViewById(R.id.channel_title);
            //myBackground = (LinearLayout) itemView.findViewById(R.id.myBackground);
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

