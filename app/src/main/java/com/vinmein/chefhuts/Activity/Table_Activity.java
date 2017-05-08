package com.vinmein.chefhuts.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.vinmein.chefhuts.Adapter.TableListAdapter;
import com.vinmein.chefhuts.DataClass.dataprocess;
import com.vinmein.chefhuts.Network.HttpPostClass;
import com.vinmein.chefhuts.R;
import com.vinmein.chefhuts.model.Tabledetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Table_Activity extends Activity {
    GridView gridView;
    EditText friend;
    Button search;
    dataprocess processor;
    TableListAdapter fAdapter;
    private ArrayList<Tabledetail> tabledata;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        processor = dataprocess.getInstance(this);
        prefs = this.getSharedPreferences("com.vinmein.chefhuts.Activity", Context.MODE_PRIVATE);
        gridView = (GridView) findViewById(R.id.tableGrid);
        tabledata = new ArrayList<>();

        fAdapter=new TableListAdapter(Table_Activity.this,R.layout.tablegrid_layout,tabledata);
        gridView.setAdapter(fAdapter);
        getTableDetails();
    }

    private void getTableDetails() {
        String hotel_id=processor.getmyrestuarantid();
        String table_url=processor.gethost()+"/api/dining/"+hotel_id;
        Log.i("tableurl",table_url);
        HttpPostClass tablestrt=new HttpPostClass();
        try {
            tablestrt.Getdata(getApplicationContext(), table_url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                  e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                 final String table_Rsp=response.body().string();
                    parseresult(table_Rsp);
                    Table_Activity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("Tableview_Response",table_Rsp);
                            fAdapter.setData(tabledata);
                            fAdapter.notifyDataSetChanged();
                        }
                    });
                }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseresult(String table_rsp) {
        try {
            JSONObject jobj1=new JSONObject(table_rsp);
            JSONArray jarray1=jobj1.getJSONArray("dinings");
            for(int i=0;i<jarray1.length();i++)
            {
                JSONObject dining_value=jarray1.getJSONObject(i);
                String table_id=dining_value.getString("_id");
                JSONObject dining_table=dining_value.getJSONObject("table");

                String  table_name=dining_table.getString("name");
                String table_number=dining_table.getString("tableNo");
                String table_seating=dining_table.getString("seatingCount");
                String table_place=dining_table.getString("Location");

                Tabledetail td=new Tabledetail(table_id,table_name,table_number,table_seating,table_place);
                tabledata.add(td);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
