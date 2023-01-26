package me.lowscarlet.pratikum9_5.ui.update;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import me.lowscarlet.pratikum9_5.DataBarangAPI;
import me.lowscarlet.pratikum9_5.DataModel;
import me.lowscarlet.pratikum9_5.MainActivity;
import me.lowscarlet.pratikum9_5.databinding.FragmentUpdateBinding;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import me.lowscarlet.pratikum9_5.R;

import java.util.ArrayList;
import java.util.List;

public class UpdateFragment extends Fragment implements View.OnClickListener {

    private FragmentUpdateBinding binding;

    MainActivity MainActivity;
    ArrayList<Button> buttonEdit = new ArrayList<>();
    ArrayList<Button> buttonDelete = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUpdateBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MainActivity = (MainActivity) getActivity();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataBarangAPI dataBarangAPI = retrofit.create(DataBarangAPI.class);
        Call<List<DataModel>> call = dataBarangAPI.getDataModels();

        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(@NotNull Call<List<DataModel>> call, @NotNull Response<List<DataModel>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(MainActivity.getApplicationContext(), "Error: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                renderTable(MainActivity, response, binding);
            }

            @Override
            public void onFailure(@NotNull Call<List<DataModel>> call, @NotNull Throwable t) {
                Toast toast = Toast.makeText(MainActivity.getApplicationContext(), "Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < buttonEdit.size(); i++) {
            if (v.getId() == buttonEdit.get(i).getId() && v.getTag().toString().trim().equals("Edit")) {
                int id = buttonEdit.get(i).getId();
                getDataByID(id);
                return;
            } else if (v.getId() == buttonDelete.get(i).getId() && v.getTag().toString().trim().equals("Delete")) {
                Integer id = buttonDelete.get(i).getId();
                deleteData(id);
                return;
            }
        }
    }

    public void renderTable(MainActivity context, Response<List<DataModel>> response, FragmentUpdateBinding binding) {
        TableLayout tabelbarang = binding.updateTable;

        TableRow barisTabel = new TableRow(context);
        int primary_color = ContextCompat.getColor(context, R.color.purple_500);
        barisTabel.setBackgroundColor(primary_color);

        for (String x : MainActivity.header) {
            TextView y = new TextView(context);
            y.setText(x);
            y.setTextColor(ContextCompat.getColor(context, R.color.white));
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

            Integer total_harga_raw = (x.getHarga_satuan().intValue()*x.getJumlah_penjualan());
            Integer total_harga = total_harga_raw - (total_harga_raw*x.getDiskon().intValue()/100);
            TextView content_hargabarang = new TextView(context);
            content_hargabarang.setText(total_harga.toString());
            content_hargabarang.setPadding(5, 1, 5, 1);
            barisTabel.addView(content_hargabarang);

            buttonEdit.add(i, new Button(context));
            buttonEdit.get(i).setId(Integer.parseInt(x.getId()));
            buttonEdit.get(i).setTag("Edit");
            buttonEdit.get(i).setText("Edit");
            buttonEdit.get(i).setOnClickListener(this);
            barisTabel.addView(buttonEdit.get(i));

            buttonDelete.add(i, new Button(context));
            buttonDelete.get(i).setId(Integer.parseInt(x.getId()));
            buttonDelete.get(i).setTag("Delete");
            buttonDelete.get(i).setText("Delete");
            buttonDelete.get(i).setOnClickListener(this);
            barisTabel.addView(buttonDelete.get(i));

            tabelbarang.addView(barisTabel, new
                    TableLayout.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT,
                    GridLayout.LayoutParams.MATCH_PARENT));

            i++;
        }

    }
    public void deleteData(Integer id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataBarangAPI biodataapi = retrofit.create(DataBarangAPI.class);
        Call<List<DataModel>> call = biodataapi.deleteDataModels(id);

        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(MainActivity.getApplicationContext(), "Error: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Toast toast = Toast.makeText(MainActivity.getApplicationContext(), "Success Code: " + response.code(), Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                Toast toast = Toast.makeText(MainActivity.getApplicationContext(), "Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        MainActivity.finish();
        startActivity(MainActivity.getIntent());
    }

    public void getDataByID(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataBarangAPI biodataapi = retrofit.create(DataBarangAPI.class);
        Call<DataModel> call1 = biodataapi.getDataModels(id);

        call1.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(MainActivity.getApplicationContext(), "Error: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                DataModel datamodels = response.body();

                LinearLayout layoutInput = new LinearLayout(MainActivity);
                layoutInput.setOrientation(LinearLayout.VERTICAL);

                final TextView viewId = new TextView(MainActivity);
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
                    final EditText z = new EditText(MainActivity);
                    z.setText(y.toString());
                    layoutInput.addView(z);
                    input.add(z);
                }

                AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(MainActivity);
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
                Toast toast = Toast.makeText(MainActivity.getApplicationContext(), "Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
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
                .baseUrl(MainActivity.baseUrl)
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
                    Toast toast = Toast.makeText(MainActivity.getApplicationContext(), "Error: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Toast toast = Toast.makeText(MainActivity.getApplicationContext(), "Success Code: " + response.code(), Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                Toast toast = Toast.makeText(MainActivity.getApplicationContext(), "Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        MainActivity.finish();
        startActivity(MainActivity.getIntent());
    }
}