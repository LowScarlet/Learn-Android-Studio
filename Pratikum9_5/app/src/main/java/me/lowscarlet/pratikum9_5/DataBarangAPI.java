package me.lowscarlet.pratikum9_5;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface DataBarangAPI {
    @GET("?operasi=view")
    Call<List<DataModel>> getDataModels();
    @GET("?operasi=insert")
    Call<List<DataModel>> insertDataModels(
            @Query("nama") String nama,
            @Query("alamat") String alamat,
            @Query("no_penjual") String no_penjual,
            @Query("kode_barang") String kode_barang,
            @Query("jumlah_penjualan") String jumlah_penjualan,
            @Query("harga_satuan") String harga_satuan,
            @Query("diskon") String diskon
    );
    @GET("?operasi=get")
    Call<DataModel> getDataModels(
            @Query("id") Integer id
    );
    @GET("?operasi=update")
    Call<List<DataModel>> editDataModels(
            @Query("id") Integer id,
            @Query("nama") String nama,
            @Query("alamat") String alamat,
            @Query("no_penjual") String no_penjual,
            @Query("kode_barang") String kode_barang,
            @Query("jumlah_penjualan") String jumlah_penjualan,
            @Query("harga_satuan") String harga_satuan,
            @Query("diskon") String diskon
    );
    @GET("?operasi=delete")
    Call<List<DataModel>> deleteDataModels(
            @Query("id") Integer id
    );
}
