package com.example.wenquxing;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.wenquxing.SQL.DatabaseOpenHelper;

public class Reminder extends AppCompatActivity {

    private final DatabaseOpenHelper helper = new DatabaseOpenHelper(Reminder.this, "EventDatabase", null, 1);

    private ScrollView scrollView;
    private TextView EditAndConfirm;
    private TextView Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        Add = findViewById(R.id.Add);
        Drawable[] drawable_setting = Add.getCompoundDrawables();
        drawable_setting[0].setBounds(0, 0, 80, 80);
        Add.setCompoundDrawables(drawable_setting[0], null,null,null);

        initClick();
        initEvents();
    }

    private void initClick(){
        EditAndConfirm = findViewById(R.id.Edit);

        //TODO:实现编辑和确认之间的切换
    }

    private void initEvents(){
        scrollView = findViewById(R.id.events);

        //TODO：从数据库中获取提醒事项信息

    }
}