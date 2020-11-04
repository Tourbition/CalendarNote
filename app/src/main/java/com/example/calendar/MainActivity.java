package com.example.calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.calendar.db.Note;
import com.example.calendar.db.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "lq";
    private Button buttonTime, buttonCalender,buttonSubmit;
    public TextView textView,textView2;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar;
    private ArrayList<HashMap<String, String>> noteList;
    private SimpleAdapter listItemAdapter;
//    private String[] testData = {"one","two","three"};
    public String time,calender;
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendar = Calendar.getInstance();
//        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy年mm月dd日");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calender = year + "年" + month + "月" + dayOfMonth+"日";
//        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
//        int minute = calendar.get(Calendar.MINUTE);
//        time = hourOfDay + ":" + minute;
        time = formatter2.format(calendar.getTime());
        buttonTime = findViewById(R.id.btTime);
        buttonCalender = findViewById(R.id.btCalender);
        buttonSubmit = findViewById(R.id.btSubmit);
        textView = findViewById(R.id.text);
        textView2 = findViewById(R.id.text2);
        buttonTime.setOnClickListener((View.OnClickListener) this);
        buttonCalender.setOnClickListener((View.OnClickListener) this);
        buttonSubmit.setOnClickListener((View.OnClickListener) this);
        ActionBar actionBar = getSupportActionBar();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initView();
    }
    private void initView(){
        noteList = new ArrayList<HashMap<String, String>>();
        userService = new UserService(MainActivity.this);
        for(Note note : userService.listAll()){
            HashMap<String, String> map = new HashMap<>();
            map.put("title", note.getTitle());
            map.put("time", note.getTime());
//            System.out.println(note.getTime());
            noteList.add(map);
        }
        listItemAdapter = new SimpleAdapter(this, noteList,
                R.layout.list_item,
                new String[] { "title", "time" },
                new int[] { R.id.tv_title, R.id.tv_time }
        );
        ListView listview=(ListView)findViewById(R.id.list);
        listview.setAdapter(listItemAdapter);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btSubmit:
                submit();
                break;
            case R.id.btTime:
                showTimeDialog();
                break;
            case R.id.btCalender:
                showCalenderDialog();
                break;
        }
    }

    private void showTimeDialog() {
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                time = hourOfDay + ":" + minute;//人工校准
                Log.e(TAG, "time : " + time);
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }

    private void showCalenderDialog() {
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calender = year + "年" + (month + 1) + "月" + dayOfMonth+"日";
                Log.e(TAG, "calender : " + calender);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    public void open(View v){
        startActivity(new Intent(this, MainActivity2.class));
    }
    public void refresh() {
        Bundle savedInstanceState = new Bundle();
        onCreate(savedInstanceState);
    }
    protected void onResume() {
        super.onResume();
        initView();
    }
    public void submit(){
        Note note = new Note();
//        UserService userService = new UserService(MainActivity.this);
        note.setTitle(String.valueOf(textView.getText()));
        note.setText(String.valueOf(textView2.getText()));
        String date = calender+" "+time;
        note.setTime(date);
        userService.add(note);
        onResume();
    }
}
