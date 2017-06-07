
package ru.strongit.myrecycledepg.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Channel {

    @SerializedName("epg_channel_id")
    private String mEpgChannelId;
    @SerializedName("extid")
    private String mExtid;
    @SerializedName("id")
    private String mId;
    @SerializedName("schedule")
    private List<Schedule> mSchedule;
    @SerializedName("title")
    private String mTitle;

    public String getEpgChannelId() {
        return mEpgChannelId;
    }

    public void setEpgChannelId(String epgChannelId) {
        mEpgChannelId = epgChannelId;
    }

    public String getExtid() {
        return mExtid;
    }

    public void setExtid(String extid) {
        mExtid = extid;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public List<Schedule> getSchedule() {
        return mSchedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        mSchedule = schedule;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
