package com.example.wenquxing;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.wenquxing.SQL.DatabaseOpenHelper;
import com.example.wenquxing.Utils.EventInfoGetterAndSetter;
import com.example.wenquxing.javabean.Event;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class Reminder extends AppCompatActivity {

    private final DatabaseOpenHelper helper = new DatabaseOpenHelper(Reminder.this, "EventDatabase", null, 1);

    private ScrollView scrollView;
    private TextView EditAndConfirm;
    private TextView Add;

    private Integer GlobalCount;

    private final Integer ContentIDBase = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        Add = findViewById(R.id.Add);
        Drawable[] drawable_setting = Add.getCompoundDrawables();
        drawable_setting[0].setBounds(0, 0, 80, 80);
        Add.setCompoundDrawables(drawable_setting[0], null,null,null);

        initClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initEvents();
    }

    private ArrayList<TextView> GetEventObject(){
        ArrayList<TextView> ret = new ArrayList<>();
        for(int i = 1; i <= GlobalCount; i++){
            ret.add(findViewById(ContentIDBase + i));
        }
        return ret;
    }

    private void initClick(){
        //TODO:实现编辑和确认之间的切换
        EditAndConfirm = findViewById(R.id.Edit);
        EditAndConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<TextView> textViewArrayList = GetEventObject();
                if(EditAndConfirm.getText().toString().equals("编辑")){
                    EditAndConfirm.setText("确认");
                    for(TextView Content : textViewArrayList){
                        System.out.println("绑定点击事件");
                        Content.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //跳转到内容编辑界面
                                Intent intent = new Intent(Reminder.this, NewEvent.class);
                                intent.putExtra("IsNewEvent", 0);
                                intent.putExtra("Content", Content.getText().toString());
                                startActivity(intent);
                            }
                        });
                    }
                } else {
                    EditAndConfirm.setText("编辑");
                    for(TextView Content : textViewArrayList){
                        //在非编辑状态取消监听器
                        Content.setOnClickListener(null);
                    }
                }
            }
        });

        //完成新任务的添加
        Add = findViewById(R.id.Add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Reminder.this, NewEvent.class);
                intent.putExtra("isNewEvent", 1);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("ResourceType")
    private void initEvents(){
        int Offest = 1;
        int Count = 1;
        scrollView = findViewById(R.id.events);
        LinearLayout.LayoutParams AllEventsParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout AllEvents = new LinearLayout(scrollView.getContext());
        AllEvents.setOrientation(LinearLayout.VERTICAL);
        AllEvents.setLayoutParams(AllEventsParams);

        //从数据库中获取提醒事项信息并动态生成
        ArrayList<Event> events = EventInfoGetterAndSetter.GetAllEvent(helper);
        for(Event event : events){
            //设置最外层LinearLayout
            LinearLayout.LayoutParams EventLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            EventLayoutParams.setMargins(0, 0, 0, 50);
            LinearLayout EventLayout = new LinearLayout(scrollView.getContext());
            EventLayout.setOrientation(LinearLayout.HORIZONTAL);
            EventLayout.setLayoutParams(EventLayoutParams);

            //设置内部组件
            LinearLayout.LayoutParams InsideLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            LinearLayout InsideLayout = new LinearLayout(EventLayout.getContext());
            InsideLayout.setOrientation(LinearLayout.VERTICAL);
            InsideLayout.setLayoutParams(InsideLayoutParams);

            //确认按钮
            LinearLayout.LayoutParams ButtonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ButtonLayoutParams.setMarginStart(40);
            RadioButton button = new RadioButton(EventLayout.getContext());
            button.setLayoutParams(ButtonLayoutParams);

            //内容
            LinearLayout.LayoutParams ContentLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView Content = new TextView(InsideLayout.getContext());
            Content.setId(ContentIDBase + Offest);
            Content.setText(event.getEventContent());
            Content.setTextSize(17);
            Content.setTextColor(R.color.black);
            Content.setPadding(50, 10, 0, 0);
            Content.setLayoutParams(ContentLayoutParams);

            //时间
            LinearLayout.LayoutParams TimeLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView Time = new TextView(InsideLayout.getContext());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Time.setText(format.format(event.getTime()));
            Time.setTextSize(12);
            Time.setTextColor(Color.parseColor("#D3D3D3"));
            Time.setPadding(50, 4, 0, 4);
            Time.setLayoutParams(TimeLayoutParams);

            InsideLayout.addView(Content);
            InsideLayout.addView(Time);
            EventLayout.addView(button);
            EventLayout.addView(InsideLayout);
            AllEvents.addView(EventLayout);

            Offest++;
            Count++;

            //绑定确认事件
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventLayout.setVisibility(View.GONE);
                    //删除数据库中的内容
                    EventInfoGetterAndSetter.DeleteEvent(helper, event.getID());
                }
            });
        }
        GlobalCount = Count - 1;

        scrollView.addView(AllEvents);
    }
}