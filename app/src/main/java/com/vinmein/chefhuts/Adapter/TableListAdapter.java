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
    public TableListAdapter(Context mcontext,int layoutResourceId,ArrayList<Tabledetail> data) {
        super(mcontext,layoutResourceId, data);
        this.layoutResourceId=layoutResourceId;
        this.context=mcontext;
        this.data=data;
    }

    public void setData(ArrayList<Tabledetail> mGridData) {
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TableListAdapter.ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new TableListAdapter.ViewHolder();
            holder.TableNo = (TextView) row.findViewById(R.id.Tblenumber);
            holder.Tablesession = (TextView) row.findViewById(R.id.Tablesession);

            row.setTag(holder);
        } else {
            holder = (TableListAdapter.ViewHolder) row.getTag();
        }

        Tabledetail item = data.get(position);
        String number = item.getTableno();
        holder.TableNo.setText(number);
        String Sessioncnt = item.getSeatingcnt();
        holder.Tablesession.setText(Sessioncnt);

        return row;
    }


    static class ViewHolder {
        TextView TableNo;
        TextView Tablesession;
    }
    }

