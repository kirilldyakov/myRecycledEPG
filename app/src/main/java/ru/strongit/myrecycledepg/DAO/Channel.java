package ru.strongit.myrecycledepg.DAO;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by user on 10.06.17.
 */

public class Channel {
    @DatabaseField(foreign = true)
    private Channel Channel;

    public void setChannel(Channel value) {

        this.Channel = value;
    }

    public Channel getChannel() {
        return Channel;
    }
}

