package com.vinmein.chefhuts.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.vinmein.chefhuts.DataClass.dataprocess;
import com.vinmein.chefhuts.Network.HttpPostClass;
import com.vinmein.chefhuts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends Activity {
    ProgressDialog mProgressDialog;
    ArrayList<String> hotellist;
    Button b1;
    String Userid;
    String selected="";
    String selectedHotelid="";
    dataprocess processor;
    SharedPreferences prefs;
    ArrayList<String> Hotelid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        processor = dataprocess.getInstance(this);
        prefs = this.getSharedPreferences("com.vinmein.chefhuts.Activity", Context.MODE_PRIVATE);
        hotellist = new ArrayList<String>();
        Hotelid = new ArrayList<String>();
        b1 = (Button) findViewById(R.id.Savebtn);

        Userid = processor.getUserId();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processor.setmyRestaurant(selected);
                processor.setmyRestaurantid(selectedHotelid);
                Intent i1 = new Intent(MainActivity.this, Table_Activity.class);
                startActivity(i1);

            }
        });
        HttpPostClass getHotelvalue = new HttpPostClass();
        String Url = processor.gethost() + "/api/restaurants/users/" + Userid;
        Log.i("Url", Url);
        try {
            getHotelvalue.Getdata(getApplicationContext(), Url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String R1 = response.body().string();
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("response", R1);
                                try {
                                    JSONArray j1 = new JSONArray(R1);
                                    for (int i = 0; i < j1.length(); i++) {
                                        JSONObject jobj1 = j1.getJSONObject(i);
                                        String rest_nameid=jobj1.getString("_id");
                                        String rest_name = jobj1.getString("RestaurantName");
                                        hotellist.add(rest_name);
                                        Hotelid.add(rest_nameid);
                                        loadSpinner();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void loadSpinner() {
        Spinner mspinner = (Spinner) findViewById(R.id.my_spinner);
        mspinner.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item,hotellist));

        mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = parent.getItemAtPosition(position).toString();
                selectedHotelid=Hotelid.get(position);
                Log.i("selected", selected);
                Log.i("Hotelid",selectedHotelid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
