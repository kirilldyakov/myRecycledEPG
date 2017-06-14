package ru.strongit.myrecycledepg.DAO;


import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 10.06.17.
 */

public class ScheduleDAO extends BaseDaoImpl<ScheduleDB, Integer> {

    protected ScheduleDAO(ConnectionSource connectionSource,
                          Class<ScheduleDB> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<ScheduleDB> getAllSchedules() throws SQLException {
        return this.queryForAll();
    }

    public List<ChannelDB> getActiveChanennelsOffset(long currentDT, long offset) throws SQLException {
        List<Integer> activeChIds = new ArrayList<>();

        String SQL = "select channel_id" +
                " from schedue" +
                " where ((start >= (" + String.valueOf(currentDT) + " - "
                + String.valueOf(offset) + "))" +
                " and (start < (" + String.valueOf(currentDT) + " + "
                + String.valueOf(offset) + ")))" +
                " order by channel_id";

        GenericRawResults<Object[]> rawResults =
                this.queryRaw(
                        SQL,
                        new DataType[]{DataType.INTEGER});
        for (Object[] resultArray : rawResults) {

            activeChIds.add((int) resultArray[0]);
        }

        List<ChannelDB> activeChannels = new ArrayList<>();
        for (int i = 0; i < activeChIds.size(); i++) {
            activeChannels.add(HelperFactory.getHelper().getChannelDAO().getChanelDBByEpg_Id(activeChIds.get(i)));
        }

        return activeChannels;
    }



    public ScheduleDB getScheduleDBByEpg_Id(int schedule_id) throws SQLException {
        ScheduleDB schdl = null;
        List<ScheduleDB> listToReturn = new ArrayList<>();
        try {
            listToReturn = this.queryForEq("id", schedule_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listToReturn.get(0);
    }


    public List<ScheduleDB> getActiveSchedules(int epg_channel_id, long currentDT, long offset) throws SQLException {
        List<Integer> activeSchdlIds = new ArrayList<>();

        String SQL = "select id" +
                " from schedue" +
                " where ("
                    +"(start >= (" + String.valueOf(currentDT) + " - "
                + String.valueOf(offset) + "))" +
                " and (start < (" + String.valueOf(currentDT) + " + "
                + String.valueOf(offset) + "))"
                +"and channel_id = "+epg_channel_id
                +")"

                +" order by start";

        GenericRawResults<Object[]> rawResults =
                this.queryRaw(
                        SQL,
                        new DataType[]{DataType.INTEGER});
        for (Object[] resultArray : rawResults) {

            activeSchdlIds.add((int) resultArray[0]);
        }

        List<ScheduleDB> activeSchedules = new ArrayList<>();
        for (int i = 0; i < activeSchdlIds.size(); i++) {
            activeSchedules.add(HelperFactory.getHelper().getScheduleDAO().getScheduleDBByEpg_Id(activeSchdlIds.get(i)));
        }

        return activeSchedules;
    }


}
