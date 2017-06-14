package ru.strongit.myrecycledepg.DAO;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by user on 10.06.17.
 */

@DatabaseTable(tableName = "Schedue")
public class ScheduleDB {


    @DatabaseField(canBeNull = false, unique = true)
    private int id;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER)
    private int channel_id;

    @DatabaseField(canBeNull = false, dataType = DataType.LONG)
    private long end;

    @DatabaseField(canBeNull = false, dataType = DataType.LONG)
    private long start;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String title;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING)
    private String description;

    public ScheduleDB() {

    }

    public ScheduleDB(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Schedue {"
                + "id=" + id
                + '}';
    }
}