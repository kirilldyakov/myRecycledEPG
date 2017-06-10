
package ru.strongit.myrecycledepg.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")

public class EPGModel {

    @SerializedName("channels")
    private List<Channel> mChannels;
    @SerializedName("result")
    private String mResult;

    public List<Channel> getChannels() {
        return mChannels;
    }

    public void setChannels(List<Channel> channels) {
        mChannels = channels;
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

}
