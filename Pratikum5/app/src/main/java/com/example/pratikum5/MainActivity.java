package com.example.pratikum5;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends ListActivity {
    ArrayList<HashMap<String, Object>> searchResults;
    ArrayList<HashMap<String, Object>> originalValues;
    LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText kotakpencari = (EditText) findViewById(R.id.kotakpencari);
        ListView playersListView = (ListView) findViewById(android.R.id.list);
        inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String namabarangs[] = {"TV", "SMARTPHONE", "KULKAS", "MEJA"};
        String hargabarangs[] = {"Harga barang TV", "Harga barang Smartphone", "Harga barang Kulkas", "Harga barang Meja"};
        Integer[] icons
                = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
        originalValues = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> temp;
        int noOfPlayers = namabarangs.length;
        for (int i = 0; i < noOfPlayers; i++) {
            temp = new HashMap<String, Object>();
            temp.put("namabarang", namabarangs[i]);
            temp.put("hargabarang", hargabarangs[i]);
            temp.put("icon", icons[i]);
            originalValues.add(temp);
        }
        searchResults = new ArrayList<HashMap<String, Object>>(originalValues);
        final CustomAdapter adapter = new CustomAdapter(this, R.layout.daftar_barang, searchResults);
        playersListView.setAdapter(adapter);
        kotakpencari.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchString = kotakpencari.getText().toString();
                int textLength = searchString.length();
                searchResults.clear();
                for (int i = 0; i < originalValues.size(); i++) {
                    String playerName = originalValues.get(i).get("namabarang").toString();
                    if (textLength <= playerName.length()) {
                        if (searchString.equalsIgnoreCase(playerName.substring(0, textLength)))
                            searchResults.add(originalValues.get(i));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,

                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
    }

    private class CustomAdapter extends ArrayAdapter<HashMap<String, Object>> {
        public CustomAdapter(Context context, int textViewResourceId,
                             ArrayList<HashMap<String, Object>> Strings) {

            super(context, textViewResourceId, Strings);
        }

        private class ViewHolder {
            ImageView icon;
            TextView namabarang, hargabarang;
        }

        ViewHolder viewHolder;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.daftar_barang, null);
                viewHolder = new ViewHolder();
                viewHolder.icon = (ImageView) convertView.findViewById(R.id.icon);
                viewHolder.namabarang = (TextView)
                        convertView.findViewById(R.id.namabarang);
                viewHolder.hargabarang = (TextView)
                        convertView.findViewById(R.id.hargabarang);
                convertView.setTag(viewHolder);
            } else
                viewHolder = (ViewHolder) convertView.getTag();
            int iconId = (Integer) searchResults.get(position).get("icon");
            viewHolder.icon.setImageDrawable(getResources().getDrawable(iconId));
            viewHolder.namabarang.setText(searchResults.get(position).get("namabarang").toString());
            viewHolder.hargabarang.setText(searchResults.get(position).get("hargabarang").toString());
            return convertView;
        }
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
// TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        String str = searchResults.get(position).get("namabarang").toString();
        try {
            if (str == "TV") {
                Toast.makeText(getApplicationContext(), "Harga Tv",
                        Toast.LENGTH_SHORT).show();
            }
            if (str == "SMARTPHONE") {
                Toast.makeText(getApplicationContext(), "Harga Smartphone",
                        Toast.LENGTH_SHORT).show();
            }
            if (str == "KULKAS") {
                Toast.makeText(getApplicationContext(), "Harga Kulkas",
                        Toast.LENGTH_SHORT).show();
            }
            if (str == "MEJA") {
                Toast.makeText(getApplicationContext(), "Harga Meja",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}