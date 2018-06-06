package com.study.apfox.barbershopapp;

public class Client {

    private String name, date, time;

    Client(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    Client(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return name + "\nПридет " + date + " в " + time;
    }
}


