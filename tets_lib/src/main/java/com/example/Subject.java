package com.example;

import java.util.ArrayList;

/**
 * Created by Denis on 12. 01. 2018.
 */

public class Subject {
    ArrayList<Pot> subject;
    int time;

    public Subject(){
        subject = new ArrayList<>();
        time = 0;
    }

    public void addPot(Pot pot){
        subject.add(pot);
        String Time = pot.getCas();
        String h = Time.split("h")[0];
        String min = "";
        int minute = 0;
        if(Time.contains("min")){
            min = Time.split("h")[1].replace("min", "");
            minute = Integer.parseInt(min);
        }
        minute += Integer.parseInt(h)*60;

        time += minute;
    }

    public Pot getPot(int i){
        return subject.get(i);
    }

    public int getTime(){
        return time;
    }

    public void clear(){
        subject = new ArrayList<>();
        time = 0;
    }

    public String toString(){
        String str = "";
        for(int i=0; i<subject.size(); i++){
            str += subject.get(i).cas + "\n";
        }
        str += "sestevek: " + time + "min";
        return  str;
    }
}
