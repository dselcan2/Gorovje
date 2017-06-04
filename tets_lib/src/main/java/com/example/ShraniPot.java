package com.example;

import java.util.ArrayList;

/**
 * Created by Denis on 16/04/2017.
 */

public class ShraniPot {
    ArrayList<Tocka> pot;
    String time;
    String ime;
    double length;

    public ArrayList<Tocka> getPot() {
        return pot;
    }

    public void setPot(ArrayList<Tocka> pot) {
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


    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public ShraniPot(ArrayList<Tocka> pot, String time, double length, String ime) {
        this.pot = pot;
        this.time = time;
        this.length = length;
        this.ime = ime;
    }
}
