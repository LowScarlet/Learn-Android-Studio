package me.lowscarlet.pratikum9_2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface BiodataAPI {
    @GET("?operasi=view")
    Call<List<DataModel>> getDataModels();
    @GET("?operasi=insert")
    Call<List<DataModel>> insertDataModels(
            @Query("nama") String nama,
            @Query("alamat") String alamat
    ) ;
    @GET("?operasi=get")
    Call<DataModel> getDataModels(
            @Query("id") Integer id
    );
    @GET("?operasi=update")
    Call<List<DataModel>> editDataModels(
            @Query("id") Integer id,
            @Query("nama") String nama,
            @Query("alamat") String alamat
    );
    @GET("?operasi=delete")
    Call<List<DataModel>> deleteDataModels(
            @Query("id") Integer id
    );
}
