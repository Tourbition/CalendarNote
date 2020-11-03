package com.example.calendar.db;

public class Note {
    private int id;
    private String text;
    private String time;
    private String title;
    public Note(){
        super();
        text = "";
        time = "";
        title = "";

    }
    public Note(String text,String time,String title){
        super();
        this.text = text;
        this.time = time;
        this.title = title;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getText(){
        return text;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getTime(){
        return time;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
}

