package com.example.lab2_gridviewspinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ThumbnailsAdapter extends BaseAdapter {
    Context context;
    Thumbnails[] thumbnails;
    LayoutInflater inflater;

    public ThumbnailsAdapter(Context applicationContext, Thumbnails[] thumbnails) {
        this.context = applicationContext;
        this.thumbnails = thumbnails;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return thumbnails.length;
    }

    @Override
    public Object getItem(int i) {
        return thumbnails[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.sp_item_text);
        names.setText(thumbnails[i].getName());
        ImageView imageView = (ImageView) view.findViewById(R.id.sp_item_image);
        imageView.setImageResource(thumbnails[i].getImg());
        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_spinner, null);
        ImageView icon = (ImageView) convertView.findViewById(R.id.sp_item_image);
        TextView names = (TextView) convertView.findViewById(R.id.sp_item_text);
        icon.setImageResource(thumbnails[position].getImg());
        names.setText(thumbnails[position].getName());
        return convertView;
    }
}
