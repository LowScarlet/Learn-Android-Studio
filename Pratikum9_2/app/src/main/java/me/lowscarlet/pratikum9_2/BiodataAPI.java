package me.lowscarlet.pratikum9_2;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface BiodataAPI {
    @GET("?operasi=view")
    Call<List<DataModel>> getDataModels();
}
