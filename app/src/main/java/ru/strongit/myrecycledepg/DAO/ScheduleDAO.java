package ru.strongit.myrecycledepg.DAO;





import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 10.06.17.
 */

public class ScheduleDAO extends BaseDaoImpl<ScheduleDB, Integer> {

    protected ScheduleDAO(ConnectionSource connectionSource,
                          Class<ScheduleDB> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<ScheduleDB> getAllSchedules() throws SQLException{
        return this.queryForAll();
    }
}
