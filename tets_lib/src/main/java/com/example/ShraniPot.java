package com.example;

import java.util.ArrayList;

/**
 * Created by Denis on 16/04/2017.
 */

public class ShraniPot {
    ArrayList<Point> pot;
    String time;
    double length;
    double visina;

    public ArrayList<Point> getPot() {
        return pot;
    }

    public void setPot(ArrayList<Point> pot) {
        this.pot = pot;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getVisina() {
        return visina;
    }

    public void setVisina(double visina) {
        this.visina = visina;
    }

    public ShraniPot(ArrayList<Point> pot, String time, double length, double visina) {
        this.pot = pot;
        this.time = time;
        this.length = length;
        this.visina = visina;
    }
}
