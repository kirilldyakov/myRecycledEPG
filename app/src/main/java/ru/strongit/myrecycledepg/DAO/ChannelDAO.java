package ru.strongit.myrecycledepg.DAO;





import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 10.06.17.
 */

public class ChannelDAO extends BaseDaoImpl<Channel, Integer> {

    protected ChannelDAO(ConnectionSource connectionSource,
                      Class<Channel> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Channel> getAllChannels() throws SQLException{
        return this.queryForAll();
    }
}
