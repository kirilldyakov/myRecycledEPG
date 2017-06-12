package ru.strongit.myrecycledepg.DAO;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by user on 10.06.17.
 */

@DatabaseTable(tableName = "Schedue")
public class ScheduleDB {

    @DatabaseField(canBeNull = false, generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER)
    private int channel_id;

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public ScheduleDB() {

    }

    public int getId() {
        return id;
    }


    public ScheduleDB(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Schedue {"
                + "id=" + id
                + '}';
    }
}