package com.example.task41;



import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.task41.Adapter.MyAdapter;
import com.example.task41.Bean.RichengBean;
import com.example.task41.Database.DBUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Add extends AppCompatActivity implements View.OnClickListener {


    private AlertDialog.Builder builder;
    private TextView textText;
    private String time;
    private EditText editText,editText1;
    private int year, month,day;
    private Button button;
    private List<RichengBean> ll;
    private int index;
    private DBUtils dbUtils;
    private Button del_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        initListener();

        if (getIntent().getStringExtra("intent")==null){
            del_button.setVisibility(View.INVISIBLE);
        }else{
            index = getIntent().getIntExtra("index",0);
            ll = (List<RichengBean>) getIntent().getSerializableExtra("richeng");
            textText.setText((dateToString(ll.get(index).getDate())));
            editText.setText(ll.get(index).getShixiang());
            editText1.setText(ll.get(index).gettitle());
            button.setText("Update Task");
            del_button.setVisibility(View.VISIBLE);
        }

    }
    public void initView(){
        button = findViewById(R.id.add_plan);
        editText = findViewById(R.id.shixiang);
        editText1 = findViewById(R.id.name);
        del_button = findViewById(R.id.delete_plan);
        textText = findViewById(R.id.setting_time);
        dbUtils = new DBUtils(this);
    }
    public void initListener(){
        button.setOnClickListener(this);
        textText.setOnClickListener(this);
        del_button.setOnClickListener(this);
    }

    public void Add(){
            if (dbUtils.AddPlan(editText1.getText().toString(),editText.getText().toString(),time)==0){
                Toast.makeText(this, "Add failed", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Add success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(Add.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
    }

    public void Update(int id){
            if (dbUtils.UpdatePlan(editText1.getText().toString(),editText.getText().toString(),time,id)==0){
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(Add.this,MainActivity.class);
                    startActivity(intent);
                    finish();

            }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onClick(View view) {
            if (view.getId() == R.id.add_plan) {
                if (TextUtils.isEmpty(editText.getText().toString())||TextUtils.isEmpty(editText1.getText().toString())||TextUtils.isEmpty(time)) {
                    Toast.makeText(getApplicationContext(), "Please fill all information", Toast.LENGTH_SHORT).show();
                } else {
                    if (button.getText().equals("Update Task")) {
                        Update(ll.get(index).getId());
                    } else {
                        Add();
                    }
                }
            }else if (view.getId()==R.id.setting_time){
                Time(textText);
            }else if (view.getId()==R.id.delete_plan){
                showTwo(ll.get(index).getId());
            }

    }
    private void showTwo(int id) {

        builder = new AlertDialog.Builder(this).setIcon(R.drawable.richeng_delete).setTitle("Delete Task")
                .setMessage("Do you want to delete this task").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (dbUtils.deletePlan(id)==0){
                            Toast.makeText(Add.this, "Failed", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Add.this, "Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(Add.this,MainActivity.class);
                            Add.this.startActivity(intent);
                            Add.this.finish();
                        }

                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //ToDo: 你想做的事情
                        dialogInterface.dismiss();
                    }
                });
        builder.create().show();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Time(TextView textView) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(this).inflate(R.layout.timepicker, null);
        DatePicker timePicker = view.findViewById(R.id.timepicker);
        timePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                year = i;
                month = i1+1;
                day = i2;
                if (month<10&&day<10){
                    time =  year + "-0" + month + "-0" + day;
                }else if (month<10&&day>10){
                    time =  year + "-0" + month + "-" + day;
                }else if (month>10&&day<10){
                    time =  year + "-" + month + "-0" + day;
                }else {
                    time =  year + "-" + month + "-" + day;
                }
            }
        });
        builder = new AlertDialog.Builder(this).setView(view).setTitle("Set Date").setIcon(R.drawable.richeng_alarm)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (time == null){
                            textView.setText("No time is set");
                        }else {
                            textView.setText(time);
                        }
                    }
                });

        builder.create().show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClass(Add.this,MainActivity.class);
            Add.this.startActivity(intent);
            Add.this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    public String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}