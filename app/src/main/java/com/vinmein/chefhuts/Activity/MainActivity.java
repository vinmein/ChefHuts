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
    ArrayList<String> worldlist;
    Button b1;
    String Userid;
    dataprocess processor;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processor = dataprocess.getInstance(this);
        setContentView(R.layout.activity_main);
        prefs = this.getSharedPreferences("com.vinmein.chefhuts.Activity", Context.MODE_PRIVATE);
        worldlist = new ArrayList<String>();
        b1=(Button)findViewById(R.id.Savebtn);

        Userid=processor.getUserId();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1=new Intent(MainActivity.this,Table_Activity.class);
                startActivity(i1);
            }
        });
        HttpPostClass getHotelvalue=new HttpPostClass();
        String Url=processor.gethost()+"/api/restaurants/users/"+Userid;
        Log.i("Url",Url);
        try {
            getHotelvalue.Getdata(getApplicationContext(), Url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    final String R1=response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("response",R1);
                            try {
                                JSONArray j1=new JSONArray(R1);
                                for(int i=0;i<=j1.length();i++){
                                    JSONObject jobj1=j1.getJSONObject(i);
                                    String rest_name=jobj1.getString("RestaurantName");
                                    worldlist.add(rest_name);
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

        Spinner mspinner=(Spinner)findViewById(R.id.my_spinner);
        mspinner.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,worldlist));
     mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
         @Override
         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String selected = parent.getItemAtPosition(position).toString();
             Log.i("selected",selected);
         }

         @Override
         public void onNothingSelected(AdapterView<?> parent) {

         }
     });
    }

}
