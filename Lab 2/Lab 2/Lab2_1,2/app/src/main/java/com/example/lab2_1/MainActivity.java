package com.example.lab2_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvPerson = (ListView) findViewById(R.id.lv_person);
        TextView tvPerson = (TextView) findViewById(R.id.tv_person);
        Button btnAdd = (Button) findViewById(R.id.btn_OK);
        EditText etName = (EditText) findViewById(R.id.et_name);

        ArrayList<String> arr;
        arr = new ArrayList<>();
        arr.add("Teo");
        arr.add("Ty");
        arr.add("Bin");
        arr.add("Bo");

        ArrayAdapter<String> adapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, arr);
        lvPerson.setAdapter(adapter);

        lvPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String value = lvPerson.getItemAtPosition(position).toString();
                tvPerson.setText("Position: " +  position + ", Value: " + value);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                arr.add(name);
                adapter.notifyDataSetChanged();
                etName.setText("");
              /*  InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);*/
            }
        });

        lvPerson.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick (AdapterView<?> adapterView, View view, int position, long id) {
                arr.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }
}