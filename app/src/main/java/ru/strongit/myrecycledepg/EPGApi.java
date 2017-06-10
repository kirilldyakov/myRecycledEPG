package ru.strongit.myrecycledepg;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.strongit.myrecycledepg.model.EPGModel;

/**
     * Интерфейс Api для общения с интернет сервером
     */
    public interface EPGApi {
        @GET("/getEPG")
        Call<EPGModel> getEPG();
    }