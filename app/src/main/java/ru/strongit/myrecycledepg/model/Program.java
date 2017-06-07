
package ru.strongit.myrecycledepg.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Program {

    @SerializedName("age_rating")
    private String mAgeRating;
    @SerializedName("countries")
    private List<Country> mCountries;
    @SerializedName("country")
    private Country mCountry;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("genres")
    private List<Genre> mGenres;
    @SerializedName("id")
    private String mId;
    @SerializedName("is_series")
    private Boolean mIsSeries;
    @SerializedName("persons")
    private List<Person> mPersons;
    @SerializedName("resources")
    private List<Resource> mResources;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("year")
    private Object mYear;

    public String getAgeRating() {
        return mAgeRating;
    }

    public void setAgeRating(String ageRating) {
        mAgeRating = ageRating;
    }

    public List<Country> getCountries() {
        return mCountries;
    }

    public void setCountries(List<Country> countries) {
        mCountries = countries;
    }

    public Country getCountry() {
        return mCountry;
    }

    public void setCountry(Country country) {
        mCountry = country;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public List<Genre> getGenres() {
        return mGenres;
    }

    public void setGenres(List<Genre> genres) {
        mGenres = genres;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Boolean getIsSeries() {
        return mIsSeries;
    }

    public void setIsSeries(Boolean isSeries) {
        mIsSeries = isSeries;
    }

    public List<Person> getPersons() {
        return mPersons;
    }

    public void setPersons(List<Person> persons) {
        mPersons = persons;
    }

    public List<Resource> getResources() {
        return mResources;
    }

    public void setResources(List<Resource> resources) {
        mResources = resources;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Object getYear() {
        return mYear;
    }

    public void setYear(Object year) {
        mYear = year;
    }

}
