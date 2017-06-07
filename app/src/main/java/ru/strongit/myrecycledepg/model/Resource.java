
package ru.strongit.myrecycledepg.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Resource {

    @SerializedName("category")
    private String mCategory;
    @SerializedName("id")
    private String mId;
    @SerializedName("is_deployed")
    private Boolean mIsDeployed;
    @SerializedName("is_public")
    private Boolean mIsPublic;
    @SerializedName("resource_group_id")
    private String mResourceGroupId;
    @SerializedName("type")
    private String mType;
    @SerializedName("url")
    private String mUrl;

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Boolean getIsDeployed() {
        return mIsDeployed;
    }

    public void setIsDeployed(Boolean isDeployed) {
        mIsDeployed = isDeployed;
    }

    public Boolean getIsPublic() {
        return mIsPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        mIsPublic = isPublic;
    }

    public String getResourceGroupId() {
        return mResourceGroupId;
    }

    public void setResourceGroupId(String resourceGroupId) {
        mResourceGroupId = resourceGroupId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

}
