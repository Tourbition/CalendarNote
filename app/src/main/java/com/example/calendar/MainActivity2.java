package com.example.calendar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.calendar.db.Note;
import com.example.calendar.db.UserService;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends AppCompatActivity implements AdapterView.OnItemClickListener {
    public String[] list_data = {"one","two","three"};
    private ArrayList<HashMap<String, String>> noteList;
    UserService userService;
    private SimpleAdapter listItemAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        GridView gridview = (GridView) findViewById(R.id.grid);
        noteList = new ArrayList<HashMap<String, String>>();
        userService = new UserService(MainActivity2.this);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        for(Note note : userService.listAll()){
            HashMap<String, String> map = new HashMap<>();
            map.put("title", note.getTitle());
            map.put("text",note.getText());
            map.put("time", note.getTime());
//            System.out.println(note.getTime());
            noteList.add(map);
        }
        listItemAdapter = new SimpleAdapter(this, noteList,
                R.layout.list_item2,
                new String[] { "title","text", "time" },
                new int[] { R.id.tv_title2, R.id.tv_text2,R.id.tv_time2 }
        );

        gridview.setAdapter(listItemAdapter);
        gridview.setOnItemClickListener(this);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}