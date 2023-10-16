package com.example.employee;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvEmployees = (ListView) findViewById(R.id.lv_employees);
        Button btnAdd = (Button) findViewById(R.id.btn_Add);
        RadioGroup rgType = (RadioGroup) findViewById(R.id.rg_loai_NV);
        EditText etId = (EditText) findViewById(R.id.et_ma_nv);
        EditText etName = (EditText) findViewById(R.id.et_ten_nv);

        ArrayList<Employee> employees = new ArrayList<>();

        ArrayAdapter<Employee> adapter = new ArrayAdapter<Employee>
                (this, android.R.layout.simple_list_item_1, employees);
        lvEmployees.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Lấy ra đúng id của Radio Button được checked
                int radId = rgType.getCheckedRadioButtonId();
                String id = etId.getText().toString();
                String name = etName.getText().toString();
                Employee employee;
                if (radId == R.id.rb_Chinh_Thuc) {
                    //tạo instance là FullTime
                    employee = new EmployeeFulltime();
                } else {
                    //Tạo instance là Partime
                    employee = new EmployeeParttime();
                }
                //FullTime hay Partime thì cũng là Employee nên có các hàm này là hiển nhiên
                employee.setID(id);
                employee.setName(name);
                //Đưa employee vào ArrayList
                employees.add(employee);
                //Cập nhập giao diện
                adapter.notifyDataSetChanged();
                etId.setText("");
                etName.setText("");
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });
    }
}