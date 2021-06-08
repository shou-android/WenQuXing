package com.example.wenquxing;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.wenquxing.SQL.DatabaseOpenHelper;
import com.example.wenquxing.Utils.EventInfoGetterAndSetter;

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

        initCheckedChange();
        initOnClick();
    }

    private void initOnClick(){
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseOpenHelper helper = new DatabaseOpenHelper(NewEvent.this, "EventDatabase", null, 1);
                java.util.Date date = new Date();
                calendar.set(PickedYear, PickedMonth - 1, PickedDay);
                EventInfoGetterAndSetter.SetEvent(helper, Content.getText().toString(), calendar.getTime());
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
                            PickedMinute = minute;
                            PickedHour = hourOfDay;
                            Time.setText(PickedHour + ":" + PickedMinute);
                        }
                    }, 0, 0, false).show();
                } else {
                    Time.setText("时间");
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
                            PickedYear = year;
                            PickedDay = dayOfMonth;
                            PickedMonth = month + 1;

                            Date.setText(PickedYear + "年" + PickedMonth + "月" + PickedDay + "日");
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
                } else {
                    Date.setText("日期");
                }
            }
        });
    }

}