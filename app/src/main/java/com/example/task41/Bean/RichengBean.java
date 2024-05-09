package com.example.task41.Bean;


import java.io.Serializable;
import java.util.Date;

public class RichengBean implements Serializable {
    private int id;
    private String title;
    private Date date;


    private String shixiang;

    public RichengBean(int id,String title,String jihua,Date date){
        this.id = id;
        this.title = title;
        this.shixiang = jihua;
        this.date = date;

    }



    public Date getDate() {
        return date;
    }

    public String getShixiang() {
        return shixiang;
    }

    public void setShixiang(String shixiang) {
        this.shixiang = shixiang;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }



    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }





}
