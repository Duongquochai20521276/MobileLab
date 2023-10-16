package com.example.lab2_gridviewspinner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DishAdapter extends ArrayAdapter<Dish> {
    private Activity context;

    public DishAdapter(Activity context, int layoutID, List<Dish>
            objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.item_dish, null, false);
        }
        Dish dish = getItem(position);

        TextView tvDishName = (TextView)
                convertView.findViewById(R.id.item_dish_position);

        ImageView imageView = (ImageView) convertView.findViewById(R.id.dish_image);
        ImageView star = (ImageView) convertView.findViewById(R.id.star_icon);
        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.item_footer);


        if (dish.getName() != null) {
            tvDishName.setText(dish.getName());
            linearLayout.setBackgroundColor(Color.argb(30, 0,0,0));
        }

        if (dish.getThumbnail() != null) {
            imageView.setImageResource(dish.getThumbnail().getImg());
        }

        if (dish.isSale()) {
            star.setVisibility(View.VISIBLE);
        } else {
            star.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }
}
