package com.example.bai02;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<SinhVien> mSinhVien = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnRemove = findViewById(R.id.btnRemove);
        Button btnUpdate = findViewById(R.id.btnUpdate);


        EditText etName = findViewById(R.id.etName);
        EditText etKhoa = findViewById(R.id.etKhoa);
        EditText etMssv = findViewById(R.id.etMSSV);

        DatabaseHandler db = new DatabaseHandler(this);

        List<SinhVien> sinhVienList = db.getAllSinhVien();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        SinhVienAdapter adapter = new SinhVienAdapter(this, sinhVienList);
        recyclerView.setAdapter(adapter);
        // vailidate input function
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SinhVien item = new SinhVien(etName.getText().toString(), etKhoa.getText().toString(),
                        etMssv.getText().toString());
                db.addSinhVien(item);
                item = new SinhVien(db.getLatestId(), etName.getText().toString(), etKhoa.getText().toString(),
                        etMssv.getText().toString());
                sinhVienList.add(item);
                adapter.notifyDataSetChanged();
            }
        });
        // remove all items
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ask for confirmation
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete all items")
                        .setMessage("Are you sure you want to delete all items?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                db.deleteAll();
                                sinhVienList.clear();
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        // remove item on long click
        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(
                new ItemClickSupport.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                        db.deleteSinhVien(sinhVienList.get(position).getId());
                        sinhVienList.remove(position);
                        adapter.notifyDataSetChanged();
                        return true;
                    }
                });
        // update item
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SinhVien item = new SinhVien(etName.getText().toString(), etKhoa.getText().toString(),
                        etMssv.getText().toString());
                db.updateSinhVien(item, id);
                sinhVienList.clear();
                sinhVienList.addAll(db.getAllSinhVien());
                adapter.notifyDataSetChanged();
            }
        });
        // select item
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {                
                        SinhVien item = sinhVienList.get(position);
                        id = item.getId();
                        etName.setText(item.getName());
                        etKhoa.setText(item.getKhoa());
                        etMssv.setText(item.getMssv());
                    }
                });



    }
}