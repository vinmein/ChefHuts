package com.vinmein.chefhuts.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.vinmein.chefhuts.Adapter.TableListAdapter;
import com.vinmein.chefhuts.DataClass.dataprocess;
import com.vinmein.chefhuts.Network.HttpPostClass;
import com.vinmein.chefhuts.R;
import com.vinmein.chefhuts.model.Tabledetail;
import com.yalantis.euclid.library.EuclidActivity;
import com.yalantis.euclid.library.EuclidListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RunnableFuture;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FoodActivity extends EuclidActivity {
    dataprocess processor;
    SharedPreferences prefs;
    Map<String, Object> profileMap;
    List<Map<String, Object>> profilesList;
    ArrayList<String> Catalogname;
    ArrayList<String> CatalogImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        processor = dataprocess.getInstance(this);
        prefs = this.getSharedPreferences("com.vinmein.chefhuts.Activity", Context.MODE_PRIVATE);
        Catalogname=new ArrayList<>();
        CatalogImg=new ArrayList<>();

        mButtonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FoodActivity.this, "Oh hi!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getmenucategory(String myhotel_id) {
        HttpPostClass getcategory=new HttpPostClass();
        String Category_url=processor.gethost()+"/api/restaurants/"+myhotel_id+"/card";
        try {
            getcategory.Getdata(getApplicationContext(),Category_url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                 if(response.isSuccessful()){
                     final String menu_category=response.body().string();
                     FoodActivity.this.runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             Log.i("Menu_Category",menu_category);
                             try {
                                 JSONArray jarray1=new JSONArray(menu_category);
                                 for(int i=0;i<=jarray1.length();i++){
                                     JSONObject catrgy_list=jarray1.getJSONObject(i);
                                     String Category_title=catrgy_list.getString("title");
                                     JSONObject Catalog=catrgy_list.getJSONObject("category");
                                     JSONObject Catalog_image=Catalog.getJSONObject("image");
                                     String img_src=Catalog_image.getString("landscape");
                                     Catalogname.add(Category_title);
                                     CatalogImg.add(img_src);

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

    @Override
    protected BaseAdapter getAdapter() {
        profilesList=new ArrayList<>();
        String myhotel_id=processor.getmyrestuarantid();
            HttpPostClass getcategory=new HttpPostClass();
            String Category_url=processor.gethost()+"/api/restaurants/"+myhotel_id+"/card";
            try {
                getcategory.Getdata(getApplicationContext(),Category_url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if(response.isSuccessful()){
                            final String menu_category=response.body().string();
                            FoodActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i("Menu_Category",menu_category);
                                    try {
                                        JSONArray jarray1=new JSONArray(menu_category);
                                        for(int i=0;i<=jarray1.length();i++){
                                            JSONObject catrgy_list=jarray1.getJSONObject(i);
                                            String Category_title=catrgy_list.getString("title");
                                            JSONObject Catalog=catrgy_list.getJSONObject("category");
                                            JSONObject Catalog_image=Catalog.getJSONObject("image");
                                            String img_src=Catalog_image.getString("landscape");
                                            Catalogname.add(Category_title);
                                            CatalogImg.add(img_src);

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


//        int[] avatars = {
//                R.drawable.anastasia,
//                R.drawable.andriy,
//                R.drawable.dmitriy,
//                R.drawable.dmitry_96,
//                R.drawable.ed,
//                R.drawable.illya,
//                R.drawable.kirill,
//                R.drawable.konstantin,
//                R.drawable.oleksii,
//                R.drawable.pavel,
//                R.drawable.vadim};
//        String[] names = getResources().getStringArray(R.array.array_names);

        for (int i = 0; i < CatalogImg.size(); i++) {
            profileMap = new HashMap<>();
            profileMap.put(EuclidListAdapter.KEY_AVATAR, CatalogImg.get(i));
            profileMap.put(EuclidListAdapter.KEY_NAME, Catalogname.get(i));
            profileMap.put(EuclidListAdapter.KEY_DESCRIPTION_SHORT, getString(R.string.lorem_ipsum_short));
            profileMap.put(EuclidListAdapter.KEY_DESCRIPTION_FULL, getString(R.string.lorem_ipsum_long));
            profilesList.add(profileMap);
        }

        return new EuclidListAdapter(this, R.layout.list_item, profilesList);
    }
}
