package com.vinmein.chefhuts.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.vinmein.chefhuts.DataClass.dataprocess;
import com.vinmein.chefhuts.R;
import com.vinmein.chefhuts.model.Tabledetail;

public class FoodCategory extends Activity {
    dataprocess processor;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_category);
        processor = dataprocess.getInstance(this);
        prefs = this.getSharedPreferences("com.vinmein.chefhuts.Activity", Context.MODE_PRIVATE);
        Intent in=getIntent();
        Tabledetail myitem=(Tabledetail)in.getSerializableExtra("bundle");
        Log.i("myitem",myitem.getTableno());
    }
}
