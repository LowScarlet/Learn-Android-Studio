package me.lowscarlet.pratikum9_5.ui.entry;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import me.lowscarlet.pratikum9_5.DataBarangAPI;
import me.lowscarlet.pratikum9_5.DataModel;
import me.lowscarlet.pratikum9_5.MainActivity;
import me.lowscarlet.pratikum9_5.databinding.FragmentEntryBinding;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.ArrayList;
import java.util.List;

public class EntryFragment extends Fragment implements View.OnClickListener {

    private FragmentEntryBinding binding;

    MainActivity MainActivity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEntryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MainActivity = (MainActivity) getActivity();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DataBarangAPI dataBarangAPI = retrofit.create(DataBarangAPI.class);
        Call<List<DataModel>> call = dataBarangAPI.getDataModels();

        LinearLayout layoutInput = binding.entryContent;
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        List<EditText> input = new ArrayList<>();

        for (String x : MainActivity.header) {
            final EditText y = new EditText(MainActivity);
            if ((x != "Total Harga") & (x != "ID") & (x != "Aksi") & (x != "Total harga")) {
                y.setHint(x);
                layoutInput.addView(y);
            }
            input.add(y);
        }
        tambahData(input);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {

    }

    public void tambahData(List<EditText> input) {
        LinearLayout layoutInput = binding.entryContent;
        layoutInput.setOrientation(LinearLayout.VERTICAL);

        Button submit_btn = binding.entrySubmit;
        submit_btn.setOnClickListener(v -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MainActivity.baseUrl)
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
                        Toast toast = Toast.makeText(MainActivity.getApplicationContext(), "Error: " + response.code(), Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    Toast toast = Toast.makeText(MainActivity.getApplicationContext(), "Success Code: " + response.code(), Toast.LENGTH_SHORT);
                    toast.show();

                    for (EditText x : input) {
                        x.setText(null);
                    }
                }

                @Override
                public void onFailure(Call<List<DataModel>> call, Throwable t) {
                    Toast toast = Toast.makeText(MainActivity.getApplicationContext(), "Failed: " + t.getMessage(), Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        });
    }
}