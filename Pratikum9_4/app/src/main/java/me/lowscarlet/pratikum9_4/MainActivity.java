package me.lowscarlet.pratikum9_4;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener {
    Button buttonTambahBarang;
    ArrayList<Button> buttonEdit = new ArrayList<>();
    ArrayList<Button> buttonDelete = new ArrayList<>();
    String baseUrl = "http://192.168.0.100/crudtest2/server.php/";
    String[] header = {"Id", "Nama", "Alamat", "No Penjual", "Kode Barang", "Jumlah Barang", "Harga Satuan", "Diskon", "Total Harga", "Aksi"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataBarangAPI dataBarangAPI = retrofit.create(DataBarangAPI.class);
        Call<List<DataModel>> call = dataBarangAPI.getDataModels();

        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<DataModel>> call, @NotNull Response<List<DataModel>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                renderTable(MainActivity.this, response);
            }

            @Override
            public void onFailure(@NotNull Call<List<DataModel>> call, @NotNull Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTambahBarang) {
            tambahBiodata();
            return;
        }
        for (int i = 0; i < buttonEdit.size(); i++) {
            if (view.getId() == buttonEdit.get(i).getId() && view.getTag().toString().trim().equals("Edit")) {
                int id = buttonEdit.get(i).getId();
                getDataByID(id);
                return;
            } else if (view.getId() == buttonDelete.get(i).getId() && view.getTag().toString().trim().equals("Delete")) {
                Integer id = buttonDelete.get(i).getId();
                deleteBiodata(id);
                return;
            }
        }

    }

    public void renderTable(MainActivity context, Response<List<DataModel>> response) {
        TableLayout tabelbarang = findViewById(R.id.tableBiodata);

        buttonTambahBarang = findViewById(R.id.buttonTambahBarang);
        buttonTambahBarang.setOnClickListener(context);

        TableRow barisTabel = new TableRow(context);
        barisTabel.setBackgroundColor(Color.GREEN);

        for (String x : header) {
            TextView y = new TextView(context);
            y.setText(x);
            y.setPadding(5, 1, 5, 1);
            barisTabel.addView(y);
        }

        tabelbarang.addView(barisTabel, new
                TableLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT,
                GridLayout.LayoutParams.WRAP_CONTENT));

        List<DataModel> datamodels = response.body();

        int i = 0;
        for (DataModel x : datamodels) {
            barisTabel = new TableRow(context);

            if (i % 2 == 0) {
                barisTabel.setBackgroundColor(Color.LTGRAY);
            }

            Object[] content = {
                    x.getId(),
                    x.getNama(),
                    x.getAlamat(),
                    x.getNo_penjual(),
                    x.getKode_barang(),
                    x.getJumlah_penjualan(),
                    x.getHarga_satuan(),
                    x.getDiskon()
            };

            for (Object y : content) {
                TextView z = new TextView(context);
                z.setText(y.toString());
                z.setPadding(5, 1, 5, 1);
                barisTabel.addView(z);
            }

            TextView content_hargabarang = new TextView(context);
            content_hargabarang.setText(
                    "GGWP"
            );
            content_hargabarang.setPadding(5, 1, 5, 1);
            barisTabel.addView(content_hargabarang);

            buttonEdit.add(i, new Button(context));
            buttonEdit.get(i).setId(Integer.parseInt(x.getId()));
            buttonEdit.get(i).setTag("Edit");
            buttonEdit.get(i).setText("Edit");
            buttonEdit.get(i).setOnClickListener(context);
            barisTabel.addView(buttonEdit.get(i));

            buttonDelete.add(i, new Button(context));
            buttonDelete.get(i).setId(Integer.parseInt(x.getId()));
            buttonDelete.get(i).setTag("Delete");
            buttonDelete.get(i).setText("Delete");
            buttonDelete.get(i).setOnClickListener(context);
            barisTabel.addView(buttonDelete.get(i));

            tabelbarang.addView(barisTabel, new
                    TableLayout.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT,
                    GridLayout.LayoutParams.MATCH_PARENT));

            i++;
        }
    }

    public void tambahBiodata() {
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        List<EditText> input = new ArrayList<>();

        for (String x : header) {
            final EditText y = new EditText(this);
            if ((x != "Total Harga") & (x != "Id") & (x != "Aksi")) {
                y.setHint(x);
                layoutInput.addView(y);
            }
            input.add(y);
        }

        AlertDialog.Builder builderInsertBiodata = new AlertDialog.Builder(this);
        builderInsertBiodata.setTitle("Insert Barang");
        builderInsertBiodata.setView(layoutInput);
        builderInsertBiodata.setPositiveButton("Insert", (dialog, which) -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            DataBarangAPI biodataapi = retrofit.create(DataBarangAPI.class);
            Call<List<DataModel>> call = biodataapi.insertDataModels(
                    input.get(1).getText().toString(),
                    input.get(2).getText().toString(),
                    input.get(3).getText().toString(),
                    input.get(4).getText().toString(),
                    input.get(5).getText().toString(),
                    input.get(6).getText().toString(),
                    input.get(7).getText().toString()
            );

            call.enqueue(new Callback<List<DataModel>>() {
                @Override
                public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                    if (!response.isSuccessful()) {
                        Toast toast = Toast.makeText(getApplicationContext(), "Error: " + response.code(), Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    Toast toast = Toast.makeText(getApplicationContext(), "Success Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();
                }

                @Override
                public void onFailure(Call<List<DataModel>> call, Throwable t) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

            finish();
            startActivity(getIntent());
        });
        builderInsertBiodata.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builderInsertBiodata.show();
    }

    public void deleteBiodata(Integer id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataBarangAPI biodataapi = retrofit.create(DataBarangAPI.class);
        Call<List<DataModel>> call = biodataapi.deleteDataModels(id);

        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Toast toast = Toast.makeText(getApplicationContext(), "Success Code: " + response.code(), Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        finish();
        startActivity(getIntent());
    }

    public void getDataByID(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataBarangAPI biodataapi = retrofit.create(DataBarangAPI.class);
        Call<DataModel> call1 = biodataapi.getDataModels(id);

        call1.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                DataModel datamodels = response.body();

                LinearLayout layoutInput = new LinearLayout(MainActivity.this);
                layoutInput.setOrientation(LinearLayout.VERTICAL);

                final TextView viewId = new TextView(MainActivity.this);
                viewId.setText(String.valueOf(id));
                viewId.setTextColor(Color.TRANSPARENT);
                layoutInput.addView(viewId);

                List<EditText> input = new ArrayList<>();

                Object[] content = {
                        datamodels.getNama(),
                        datamodels.getAlamat(),
                        datamodels.getNo_penjual(),
                        datamodels.getKode_barang(),
                        datamodels.getJumlah_penjualan(),
                        datamodels.getHarga_satuan(),
                        datamodels.getDiskon()
                };

                for (Object y : content) {
                    final EditText z = new EditText(MainActivity.this);
                    z.setText(y.toString());
                    layoutInput.addView(z);
                    input.add(z);
                }

                AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(MainActivity.this);
                builderEditBiodata.setTitle("Update Barang");
                builderEditBiodata.setView(layoutInput);

                builderEditBiodata.setPositiveButton("Update", (dialog, which) -> updateDataByID(
                        id,
                        input.get(0).getText().toString(),
                        input.get(1).getText().toString(),
                        input.get(2).getText().toString(),
                        input.get(3).getText().toString(),
                        input.get(4).getText().toString(),
                        input.get(5).getText().toString(),
                        input.get(6).getText().toString()
                ));

                builderEditBiodata.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builderEditBiodata.show();
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void updateDataByID(
            int id,
            String nama,
            String alamat,
            String no_penjual,
            String kode_barang,
            String jumlah_penjualan,
            String harga_satuan,
            String diskon
    ) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataBarangAPI biodataapi = retrofit.create(DataBarangAPI.class);
        Call<List<DataModel>> call = biodataapi.editDataModels(
                id,
                nama,
                alamat,
                no_penjual,
                kode_barang,
                jumlah_penjualan,
                harga_satuan,
                diskon
        );

        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Toast toast = Toast.makeText(getApplicationContext(), "Success Code: " + response.code(), Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        finish();
        startActivity(getIntent());
    }
}