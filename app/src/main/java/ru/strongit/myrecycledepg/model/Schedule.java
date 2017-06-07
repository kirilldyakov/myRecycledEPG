
package ru.strongit.myrecycledepg.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Schedule {

    @SerializedName("age_rating")
    private String mAgeRating;
    @SerializedName("catchup_end_correction")
    private Object mCatchupEndCorrection;
    @SerializedName("catchup_start_correction")
    private Object mCatchupStartCorrection;
    @SerializedName("channel")
    private Channel mChannel;
    @SerializedName("channel_id")
    private String mChannelId;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("duration")
    private String mDuration;
    @SerializedName("end")
    private String mEnd;
    @SerializedName("id")
    private String mId;
    @SerializedName("is_catchup_available")
    private Boolean mIsCatchupAvailable;
    @SerializedName("program")
    private Program mProgram;
    @SerializedName("program_id")
    private String mProgramId;
    @SerializedName("start")
    private String mStart;
    @SerializedName("title")
    private String mTitle;

    public String getAgeRating() {
        return mAgeRating;
    }

    public void setAgeRating(String ageRating) {
        mAgeRating = ageRating;
    }

    public Object getCatchupEndCorrection() {
        return mCatchupEndCorrection;
    }

    public void setCatchupEndCorrection(Object catchupEndCorrection) {
        mCatchupEndCorrection = catchupEndCorrection;
    }

    public Object getCatchupStartCorrection() {
        return mCatchupStartCorrection;
    }

    public void setCatchupStartCorrection(Object catchupStartCorrection) {
        mCatchupStartCorrection = catchupStartCorrection;
    }

    public Channel getChannel() {
        return mChannel;
    }

    public void setChannel(Channel channel) {
        mChannel = channel;
    }

    public String getChannelId() {
        return mChannelId;
    }

    public void setChannelId(String channelId) {
        mChannelId = channelId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String getEnd() {
        return mEnd;
    }

    public void setEnd(String end) {
        mEnd = end;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Boolean getIsCatchupAvailable() {
        return mIsCatchupAvailable;
    }

    public void setIsCatchupAvailable(Boolean isCatchupAvailable) {
        mIsCatchupAvailable = isCatchupAvailable;
    }

    public Program getProgram() {
        return mProgram;
    }

    public void setProgram(Program program) {
        mProgram = program;
    }

    public String getProgramId() {
        return mProgramId;
    }

    public void setProgramId(String programId) {
        mProgramId = programId;
    }

    public String getStart() {
        return mStart;
    }

    public void setStart(String start) {
        mStart = start;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
