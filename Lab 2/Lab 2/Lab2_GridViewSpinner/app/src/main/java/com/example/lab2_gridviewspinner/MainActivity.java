package com.example.lab2_gridviewspinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gvDishes = (GridView) findViewById(R.id.gv_Dishes);
        Button btnAdd = (Button) findViewById(R.id.btn_Add);
        CheckBox cbPromotion = (CheckBox) findViewById(R.id.cb_Yes);
        EditText etName = (EditText) findViewById(R.id.et_Name);
        etName.setSingleLine();

        Spinner snThumbnail = (Spinner) findViewById(R.id.sn_Thumbnail);
        ThumbnailsAdapter adapterThumbnails = new ThumbnailsAdapter(
                this, Thumbnails.values());
        snThumbnail.setAdapter(adapterThumbnails);

        ArrayList<Dish> dishes = new ArrayList<>();
        DishAdapter adapterDish = new DishAdapter(
                MainActivity.this, R.layout.item_dish, dishes);
        gvDishes.setAdapter(adapterDish);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isPromotion = cbPromotion.isChecked();
                String name = etName.getText().toString();
                Thumbnails thumbnails = (Thumbnails) snThumbnail.getSelectedItem();

                Dish dish = new Dish();
                dish.setPromotion(isPromotion);
                dish.setName(name);
                dish.setThumbnail(thumbnails);
                dishes.add(dish);
                adapterDish.notifyDataSetChanged();

                Toast.makeText(MainActivity.this,
                        "Add Successfully", Toast.LENGTH_LONG).show();

                etName.setText("");
                if (cbPromotion.isChecked()) {
                    cbPromotion.setChecked(false);
                }
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
        gvDishes.setAdapter(adapterDish);
    }
}