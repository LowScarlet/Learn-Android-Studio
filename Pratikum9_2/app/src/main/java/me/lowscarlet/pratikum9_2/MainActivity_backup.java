//package me.lowscarlet.pratikum9_2;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.TextView;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;
//
//import java.util.List;
//
//public class MainActivity_backup extends Activity implements OnClickListener {
//    private TextView warning;
//    private TextView tabelBiodata;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        tabelBiodata = findViewById(R.id.text_view_result);
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.105.201/crudtest/server.php/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        BiodataAPI biodataapi = retrofit.create(BiodataAPI.class);
//        Call<List<DataModel>> call = biodataapi.getDataModels();
//
//        call.enqueue(new Callback<List<DataModel>>() {
//            @Override
//            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
//                if (!response.isSuccessful()) {
//                    tabelBiodata.setText("Error Test 1: "+response.code() );
//                    return;
//                }
//
//                List<DataModel> datamodels = response.body();
//
//                String content = "";
//                for (DataModel x: datamodels) {
//                    content+="ID: "+ x.getId()+"\n";
//                    content+="Nama: "+ x.getNama()+"\n";
//                    content+="Alamat: "+ x.getAlamat()+"\n";
//                }
//
//                tabelBiodata.append(content);
//            }
//
//            @Override
//            public void onFailure(Call<List<DataModel>> call, Throwable t) {
//                tabelBiodata.setText(t.getMessage());
//            }
//        });
//
//
//    }
//
//    @Override
//    public void onClick(View view) {
//
//    }
//}