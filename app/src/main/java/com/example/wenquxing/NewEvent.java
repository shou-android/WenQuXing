package com.example.wenquxing;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.wenquxing.SQL.DatabaseOpenHelper;
import com.example.wenquxing.Utils.EventInfoGetterAndSetter;
import com.example.wenquxing.javabean.Event;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class NewEvent extends AppCompatActivity {

    private Switch UseTime;
    private Switch UseDate;

    private Button Submit;
    private EditText Content;

    private TextView Date;
    private TextView Time;

    final Calendar calendar = Calendar.getInstance();

    private Integer PickedYear = null;
    private Integer PickedMonth;
    private Integer PickedDay;

    private Integer PickedHour;
    private Integer PickedMinute;

    private int isNewEvent;
    private boolean isBeenModified = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

        UseTime = findViewById(R.id.UseTime);
        UseDate = findViewById(R.id.UseDate);

        Date = findViewById(R.id.Date);
        Time = findViewById(R.id.Time);

        Submit = findViewById(R.id.SubmitEvent);
        Content = findViewById(R.id.NewContent);
        ImageView fan1=(ImageView)findViewById(R.id.iv_backward);

        isNewEvent = getIntent().getIntExtra("IsNewEvent", 1);

        initCheckedChange();
        initOnClick();
        initContent();

        fan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewEvent.this,Reminder.class);
                startActivity(intent);
            }
        });
    }

    private void initContent(){
        if(isNewEvent == 1){
            return;
        }
        Content.setText(getIntent().getStringExtra("Content"));
    }

    private void initOnClick(){
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseOpenHelper helper = new DatabaseOpenHelper(NewEvent.this, "EventDatabase", null, 1);
                java.util.Date date = new Date();
                if(isNewEvent == 1 || isBeenModified){
                    calendar.set(PickedYear, PickedMonth - 1, PickedDay, PickedHour, PickedDay);
                    EventInfoGetterAndSetter.SetEvent(helper, Content.getText().toString(), calendar.getTime());
                } else {
                    //TODO:从数据库中获取时间

                    EventInfoGetterAndSetter.SetEvent(helper, Content.getText().toString(), new Date());
                }
                finish();
            }
        });
    }

    private void initCheckedChange(){
        UseTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    new TimePickerDialog(NewEvent.this, new TimePickerDialog.OnTimeSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            isBeenModified = true;
                            PickedMinute = minute;
                            PickedHour = hourOfDay;
                            Time.setText(PickedHour + ":" + PickedMinute);
                        }
                    }, 0, 0, false).show();
                } else {
                    if(isNewEvent == 0){
                        Time.setText("选择新时间");
                    } else {
                        Time.setText("时间");
                    }
                }
            }
        });

        UseDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    new DatePickerDialog(NewEvent.this, new DatePickerDialog.OnDateSetListener() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            isBeenModified = true;
                            PickedYear = year;
                            PickedDay = dayOfMonth;
                            PickedMonth = month + 1;

                            Date.setText(PickedYear + "年" + PickedMonth + "月" + PickedDay + "日");
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                } else {
                    if(isNewEvent == 0){
                        Date.setText("日期");
                    } else {
                        Date.setText("选择新日期");
                    }
                }
            }
        });

    }

}