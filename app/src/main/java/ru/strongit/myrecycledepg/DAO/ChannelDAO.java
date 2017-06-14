package ru.strongit.myrecycledepg.DAO;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.strongit.myrecycledepg.model.Channel;

/**
 * Created by user on 10.06.17.
 */

public class ChannelDAO extends BaseDaoImpl<ChannelDB, Integer> {

    protected ChannelDAO(ConnectionSource connectionSource,
                         Class<ChannelDB> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<ChannelDB> getAllChannels() throws SQLException {
        return this.queryForAll();
    }

    public ChannelDB getChanelDBByEpg_Id(int channel_id) throws SQLException {
        ChannelDB chnl = null;
        List<ChannelDB> listToReturn = new ArrayList<>();
        try {
            listToReturn = this.queryForEq("epg_channel_id", channel_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listToReturn.get(0);
    }

    public void createChannelDB(Channel chnl) throws SQLException {
            ChannelDB channelDB = new ChannelDB(
                    Integer.parseInt(chnl.getId())
                    , chnl.getTitle()
                    , Integer.parseInt(chnl.getEpgChannelId()));
            HelperFactory.getHelper().getChannelDAO().create(channelDB);
    }

}


