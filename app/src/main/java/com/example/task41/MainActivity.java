package com.example.task41;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.example.task41.Adapter.MyAdapter;
import com.example.task41.Bean.RichengBean;
import com.example.task41.Database.DBUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private AlertDialog.Builder builder;
    private List<RichengBean> mm;
    private ListView listView;
    private MyAdapter myAdapter;
    private FloatingActionButton floatingActionButton;
    private DBUtils dbUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_plan);
        dbUtils = new DBUtils(this);
        floatingActionButton = findViewById(R.id.add_richeng);
        QueryRi();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Add.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int j, long l) {
                    Intent intent = new Intent();
                    intent.putExtra("richeng", (Serializable) mm);
                    intent.putExtra("index", j);
                    intent.putExtra("intent", "update");
                    intent.setClass(MainActivity.this, Add.class);
                    MainActivity.this.startActivity(intent);
                    MainActivity.this.finish();

            }
        });

    }





    //查询日程
    public void QueryRi() {
            mm = dbUtils.QueryPlan();
            if (mm.size()==0) {
                Toast.makeText(this, "No Task", Toast.LENGTH_SHORT).show();
            } else {
                myAdapter = new MyAdapter(this, mm);
                listView.setAdapter(myAdapter);
        }

    }
    public String dateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}

