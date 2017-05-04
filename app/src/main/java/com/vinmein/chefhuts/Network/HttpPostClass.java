package com.vinmein.chefhuts.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Vasanth on 11/10/2016.
 */
public class HttpPostClass {
    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static Context contextOfApplication;
    OkHttpClient client = new OkHttpClient();
    SharedPreferences prefs;

    public Call PostData(Context mContext, String url, String json, Callback callback) throws IOException {
        Log.i("POST METHOD:",url);
        prefs = mContext.getSharedPreferences("com.vinmein.chefhuts.Activity", Context.MODE_PRIVATE);
        String token=prefs.getString("token",null);
        if(token != null) {
            Log.i("Profil", token);
            // add your code here
        }
        RequestBody body=RequestBody.create(JSON,json);
        Log.i("url", String.valueOf(body));
        Request request = new Request.Builder().url(url).header("Authorization","Bearer "+token).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public Call Getdata(Context mContext,String url, Callback callback) throws IOException {

        Log.i("GET METHOD:",url);
        prefs = mContext.getSharedPreferences("com.vinmein.chefhuts.Activity", Context.MODE_PRIVATE);
        String token=prefs.getString("token",null);
        if(token != null) {
            Log.i("Profil", token);
            // add your code here
        }
        Request request = new Request.Builder().url(url).header("Authorization","Bearer "+token).build();
        Call call = client.newCall(request);
        call.enqueue(callback);

        return call;
    }



}
