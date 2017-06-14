package ru.strongit.myrecycledepg.DAO;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by user on 10.06.17.
 */

@DatabaseTable(tableName = "Channels")
public class ChannelDB {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, unique = true)
    private String title;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, unique = true)
    private int epg_channel_id;

    public ChannelDB() {

    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpg_channel_id() {
        return epg_channel_id;
    }

    public void setEpg_channel_id(int epg_channeel_id) {
        this.epg_channel_id = epg_channeel_id;
    }

    public ChannelDB(int id, String title, int epg_channeel_id) {
        this.id = id;
        this.title = title;
        this.epg_channel_id = epg_channeel_id;
    }

    @Override
    public String toString() {
        return "Channel {" +
                "id=" + id +
                ", title=" + title +
                ", epg_channeel_id=" + epg_channel_id +
                '}';
    }
}