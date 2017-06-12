package ru.strongit.myrecycledepg.DAO;





import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 10.06.17.
 */

public class ChannelDAO extends BaseDaoImpl<dbChannel, Integer> {

    protected ChannelDAO(ConnectionSource connectionSource,
                      Class<dbChannel> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<dbChannel> getAllChannels() throws SQLException{
        return this.queryForAll();
    }
}
