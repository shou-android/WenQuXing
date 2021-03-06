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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

public class Reminder extends AppCompatActivity {

    private final DatabaseOpenHelper helper = new DatabaseOpenHelper(Reminder.this, "EventDatabase", null, 1);

    private ScrollView scrollView;
    private TextView EditAndConfirm;
    private TextView Add;

    private ArrayList<Integer> IDs = new ArrayList<>();

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

    private ArrayList<LinearLayout> GetEventObject(){
        ArrayList<LinearLayout> ret = new ArrayList<>();
        for(Integer ID : IDs){
            ret.add(findViewById(ID));
        }
        return ret;
    }

    private ArrayList<Integer> ParseTime(String Time){
        ArrayList<Integer> ret = new ArrayList<>();
        int Year = Integer.parseInt(Time.substring(0, 4));
        int Month = Integer.parseInt(Time.substring(5, 7));
        int Day = Integer.parseInt(Time.substring(8, 10));
        int Hour = Integer.parseInt(Time.substring(11, 13));
        int Minute = Integer.parseInt(Time.substring(14));
        ret.add(Year);
        ret.add(Month - 1);
        ret.add(Day);
        ret.add(Hour);
        ret.add(Minute);
        return ret;
    }

    private void initClick(){
        //TODO:????????????????????????????????????
        EditAndConfirm = findViewById(R.id.Edit);
        EditAndConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<LinearLayout> textViewArrayList = GetEventObject();
                if(EditAndConfirm.getText().toString().equals("??????")){
                    EditAndConfirm.setText("??????");
                    for(LinearLayout InsideLayout : textViewArrayList){
                        String Content = null;
                        long Time = -1;
                        for(int i = 0; i < InsideLayout.getChildCount(); i++){
                            TextView textView = (TextView) InsideLayout.getChildAt(i);
                            String str = textView.getText().toString();
                            if(str.contains("-")){
                                //TODO:?????????????????????
                                ArrayList<Integer> Times = ParseTime(str);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Times.get(0), Times.get(1), Times.get(2), Times.get(3), Times.get(4));
                                Time = calendar.getTimeInMillis();
                            } else {
                                Content = str;
                            }
                        }
                        String finalContent = Content;
                        long finalTime = Time;
                        InsideLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //???????????????????????????
                                Intent intent = new Intent(Reminder.this, NewEvent.class);
                                intent.putExtra("IsNewEvent", 0);
                                intent.putExtra("Content", finalContent);
                                intent.putExtra("Time", finalTime);
                                intent.putExtra("EventID", InsideLayout.getId());
                                startActivity(intent);
                            }
                        });
                    }
                } else {
                    EditAndConfirm.setText("??????");
                    for(LinearLayout InsideLayout : textViewArrayList){
                        //?????????????????????????????????
                        InsideLayout.setOnClickListener(null);
                    }
                }
            }
        });

        //????????????????????????
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
        scrollView = findViewById(R.id.events);
        scrollView.removeAllViews();
        LinearLayout.LayoutParams AllEventsParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout AllEvents = new LinearLayout(scrollView.getContext());
        AllEvents.setOrientation(LinearLayout.VERTICAL);
        AllEvents.setLayoutParams(AllEventsParams);

        //??????????????????????????????????????????????????????
        ArrayList<Event> events = EventInfoGetterAndSetter.GetAllEvent(helper);
        Collections.sort(events);
        for(Event event : events){
            Integer EventID = event.getID();
            IDs.add(EventID);

            //???????????????LinearLayout
            LinearLayout.LayoutParams EventLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            EventLayoutParams.setMargins(0, 0, 0, 50);
            LinearLayout EventLayout = new LinearLayout(scrollView.getContext());
            EventLayout.setOrientation(LinearLayout.HORIZONTAL);
            EventLayout.setLayoutParams(EventLayoutParams);

            //??????????????????
            LinearLayout.LayoutParams InsideLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            LinearLayout InsideLayout = new LinearLayout(EventLayout.getContext());
            InsideLayout.setOrientation(LinearLayout.VERTICAL);
            InsideLayout.setLayoutParams(InsideLayoutParams);
            InsideLayout.setId(EventID);

            //????????????
            LinearLayout.LayoutParams ButtonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ButtonLayoutParams.setMarginStart(40);
            RadioButton button = new RadioButton(EventLayout.getContext());
            button.setLayoutParams(ButtonLayoutParams);

            //??????
            LinearLayout.LayoutParams ContentLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView Content = new TextView(InsideLayout.getContext());
            Content.setText(event.getEventContent());
            Content.setTextSize(17);
            Content.setTextColor(R.color.black);
            Content.setPadding(50, 10, 0, 0);
            Content.setLayoutParams(ContentLayoutParams);

            //??????
            TextView Time = new TextView(InsideLayout.getContext());
            if(event.getTime() != null){
                LinearLayout.LayoutParams TimeLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Time.setText(format.format(event.getTime()));
                Time.setTextSize(12);
                Time.setTextColor(Color.parseColor("#D3D3D3"));
                Time.setPadding(50, 4, 0, 4);
                Time.setLayoutParams(TimeLayoutParams);
            }

            InsideLayout.addView(Content);
            if(event.getTime() != null){
                InsideLayout.addView(Time);
            }
            EventLayout.addView(button);
            EventLayout.addView(InsideLayout);
            AllEvents.addView(EventLayout);

            //??????????????????
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventLayout.setVisibility(View.GONE);
                    //???????????????????????????
                    EventInfoGetterAndSetter.DeleteEvent(helper, event.getID());
                }
            });
        }

        scrollView.addView(AllEvents);
    }
}