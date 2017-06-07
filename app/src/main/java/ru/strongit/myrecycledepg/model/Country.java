
package ru.strongit.myrecycledepg.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Country {

    @SerializedName("code")
    private Object mCode;
    @SerializedName("extid")
    private String mExtid;
    @SerializedName("id")
    private String mId;
    @SerializedName("namespace")
    private String mNamespace;
    @SerializedName("title")
    private String mTitle;

    public Object getCode() {
        return mCode;
    }

    public void setCode(Object code) {
        mCode = code;
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

    public String getNamespace() {
        return mNamespace;
    }

    public void setNamespace(String namespace) {
        mNamespace = namespace;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}
