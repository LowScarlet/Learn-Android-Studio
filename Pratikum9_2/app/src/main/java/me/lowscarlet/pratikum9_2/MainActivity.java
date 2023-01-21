package me.lowscarlet.pratikum9_2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener {
    Button buttonTambahBiodata;
    ArrayList<Button> buttonEdit = new ArrayList<Button>();
    ArrayList<Button> buttonDelete = new ArrayList<Button>();
    String baseUrl = "http://192.168.0.100/crudtest/server.php/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BiodataAPI biodataapi = retrofit.create(BiodataAPI.class);
        Call<List<DataModel>> call = biodataapi.getDataModels();

        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error Test 1: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                renderTable(MainActivity.this, response);
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTambahBiodata) {
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
        TableLayout tabelBiodata = findViewById(R.id.tableBiodata);

        buttonTambahBiodata = (Button)
                findViewById(R.id.buttonTambahBiodata);
        buttonTambahBiodata.setOnClickListener(context);

        TableRow barisTabel = new TableRow(context);
        barisTabel.setBackgroundColor(Color.CYAN);

        TextView viewHeaderId = new TextView(context);
        TextView viewHeaderNama = new TextView(context);
        TextView viewHeaderAlamat = new TextView(context);
        TextView viewHeaderAction = new TextView(context);

        viewHeaderId.setText("ID");
        viewHeaderNama.setText("Nama");
        viewHeaderAlamat.setText("Alamat");
        viewHeaderAction.setText("Action");

        viewHeaderId.setPadding(5, 1, 5, 1);
        viewHeaderNama.setPadding(5, 1, 5, 1);
        viewHeaderAlamat.setPadding(5, 1, 5, 1);
        viewHeaderAction.setPadding(5, 1, 5, 1);

        barisTabel.addView(viewHeaderId);
        barisTabel.addView(viewHeaderNama);
        barisTabel.addView(viewHeaderAlamat);
        barisTabel.addView(viewHeaderAction);

        tabelBiodata.addView(barisTabel, new
                TableLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT,
                GridLayout.LayoutParams.WRAP_CONTENT));

        List<DataModel> datamodels = response.body();

        Integer i = 0;
        for (DataModel x : datamodels) {
            System.out.println("GGG " + i);

            barisTabel = new TableRow(context);
            if (i % 2 == 0) {
                barisTabel.setBackgroundColor(Color.LTGRAY);
            }

            TextView viewId = new TextView(context);
            viewId.setText(x.getId());
            viewId.setPadding(5, 1, 5, 1);

            barisTabel.addView(viewId);
            TextView viewNama = new TextView(context);
            viewNama.setText(x.getNama());
            viewNama.setPadding(5, 1, 5, 1);

            barisTabel.addView(viewNama);
            TextView viewAlamat = new TextView(context);
            viewAlamat.setText(x.getAlamat());
            viewAlamat.setPadding(5, 1, 5, 1);
            barisTabel.addView(viewAlamat);

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

            tabelBiodata.addView(barisTabel, new
                    TableLayout.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT,
                    GridLayout.LayoutParams.MATCH_PARENT));
            i++;
        }
    }

    public void tambahBiodata() {
        /* layout akan ditampilkan pada AlertDialog */
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);
        final EditText editNama = new EditText(this);
        editNama.setHint("Nama");
        layoutInput.addView(editNama);
        final EditText editAlamat = new EditText(this);
        editAlamat.setHint("Alamat");
        layoutInput.addView(editAlamat);
        AlertDialog.Builder builderInsertBiodata = new
                AlertDialog.Builder(this);
        builderInsertBiodata.setTitle("Insert Biodata");
        builderInsertBiodata.setView(layoutInput);
        builderInsertBiodata.setPositiveButton("Insert", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nama = editNama.getText().toString();
                        String alamat = editAlamat.getText().toString();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(baseUrl)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        BiodataAPI biodataapi = retrofit.create(BiodataAPI.class);
                        Call<List<DataModel>> call = biodataapi.insertDataModels(nama, alamat);

                        call.enqueue(new Callback<List<DataModel>>() {
                            @Override
                            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                                if (!response.isSuccessful()) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Error Test 1: " + response.code(), Toast.LENGTH_SHORT);
                                    toast.show();
                                    return;
                                }
                                Toast toast = Toast.makeText(getApplicationContext(), "Message" + response.code(), Toast.LENGTH_SHORT);
                                toast.show();
                            }

                            @Override
                            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        });

                        finish();
                        startActivity(getIntent());
                    }
                });
        builderInsertBiodata.setNegativeButton("Cancel", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builderInsertBiodata.show();
    }
    public void deleteBiodata(Integer id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BiodataAPI biodataapi = retrofit.create(BiodataAPI.class);
        Call<List<DataModel>> call = biodataapi.deleteDataModels(id);

        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error Test 1: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Toast toast = Toast.makeText(getApplicationContext(), "Message" + response.code(), Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
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

        BiodataAPI biodataapi = retrofit.create(BiodataAPI.class);
        Call<DataModel> call1 = biodataapi.getDataModels(id);

        call1.enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error Test 1: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                DataModel datamodels = response.body();

                String namaEdit = datamodels.getNama();
                String alamatEdit = datamodels.getAlamat();

                LinearLayout layoutInput = new LinearLayout(MainActivity.this);
                layoutInput.setOrientation(LinearLayout.VERTICAL);

                // buat id tersembunyi di alertbuilder
                final TextView viewId = new TextView(MainActivity.this);
                viewId.setText(String.valueOf(id));
                viewId.setTextColor(Color.TRANSPARENT);
                layoutInput.addView(viewId);

                final EditText editNama = new EditText(MainActivity.this);
                editNama.setText(namaEdit);
                layoutInput.addView(editNama);

                final EditText editAlamat = new EditText(MainActivity.this);
                editAlamat.setText(alamatEdit);
                layoutInput.addView(editAlamat);

                AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(MainActivity.this);
                builderEditBiodata.setTitle("Update Biodata");
                builderEditBiodata.setView(layoutInput);

                builderEditBiodata.setPositiveButton("Update", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateDataByID(id, editNama.getText().toString(), editAlamat.getText().toString());
                            }
                        });
                builderEditBiodata.setNegativeButton("Cancel", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                builderEditBiodata.show();
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
    public void updateDataByID(int id, String nama, String alamat) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BiodataAPI biodataapi = retrofit.create(BiodataAPI.class);
        Call<List<DataModel>> call = biodataapi.editDataModels(id, nama, alamat);

        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                if (!response.isSuccessful()) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error Test 1: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                Toast toast = Toast.makeText(getApplicationContext(), "Message" + response.code(), Toast.LENGTH_SHORT);
                toast.show();
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        finish();
        startActivity(getIntent());
    }
}