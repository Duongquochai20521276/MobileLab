package com.example.lab2_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rcvEmployee = (RecyclerView) findViewById(R.id.rcv_Employee);
        Button btnAdd = (Button) findViewById(R.id.btn_Add);
        CheckBox cbManager = (CheckBox) findViewById(R.id.cb_Yes);
        EditText etName = (EditText) findViewById(R.id.et_Name);
        EditText etId = (EditText) findViewById(R.id.et_ID);

        List<Employee> employees = new ArrayList<>();

        EmployeeAdapter adapterEmployee = new EmployeeAdapter(employees);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvEmployee.setLayoutManager(linearLayoutManager);
        rcvEmployee.setAdapter(adapterEmployee);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Kiểm tra có phải Manager không
                boolean checkManager = cbManager.isChecked();
                String name = etName.getText().toString();
                Employee employee;
                if (checkManager == true) {
                    //tạo instance là Manager
                    employee = new Manager();
                } else {
                    //Tạo instance là Staff
                    employee = new Staff();
                }
                //Manager hay Staff thì cũng là Employee nên có các hàm này là hiển nhiên
                employee.setName(name);
                //Đưa employee vào ArrayList
                employees.add(employee);
                etId.setText("");
                etName.setText("");
                if (cbManager.isChecked())
                    cbManager.setChecked(false);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                //Cập nhập giao diện
                adapterEmployee.notifyItemInserted(employees.size()-1);
                rcvEmployee.scrollToPosition(employees.size()-1);

            }
        });
    }
}