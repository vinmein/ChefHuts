package com.vinmein.chefhuts.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vinmein.chefhuts.R;
import com.vinmein.chefhuts.model.Tabledetail;

import java.util.ArrayList;
import java.util.List;

import static com.vinmein.chefhuts.R.drawable.web;

/**
 * Created by Vasanth on 5/6/2017.
 */

public class TableListAdapter extends ArrayAdapter<Tabledetail> {
    private int layoutResourceId;
    private ArrayList<Tabledetail> data = new ArrayList<Tabledetail>();
    private Context context;
    public List<Integer> selectedPositions;
    public TableListAdapter(Context mcontext,int layoutResourceId,ArrayList<Tabledetail> griddata) {
        super(mcontext,layoutResourceId, griddata);
        this.layoutResourceId=layoutResourceId;
        this.context=mcontext;
        this.data=griddata;


    }

    public void setGridData(ArrayList<Tabledetail> mGridData) {
        this.data = mGridData;
        notifyDataSetChanged();
    }

//    @NonNull
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View row = convertView;
//        ViewHolder holder;
//
//        if (row == null) {
//            LayoutInflater inflater = (LayoutInflater) Context.getSystem.
//            row = inflater.inflate(layoutResourceId, parent, false);
//            holder = new ViewHolder();
//            holder.titleTextView = (TextView) row.findViewById(R.id.grid_item_title);
//            holder.imageView = (ImageView) row.findViewById(R.id.grid_item_image);
//            row.setTag(holder);
//        } else {
//            holder = (ViewHolder) row.getTag();
//        }
//
//        RestaurantItem item = mGridData.get(position);
//        holder.titleTextView.setText(Html.fromHtml(item.getTitle()));
//
//        Picasso.with(mContext).load(item.getImage()).into(holder.imageView);
//        return row;
//    }
//
//    static class ViewHolder {
//        TextView titleTextView;
//        ImageView imageView;
//    }
    }

