package com.example;

import java.util.ArrayList;

/**
 * Created by Denis on 27/02/2017.
 */

public class Pot {
    int id;
    String ime;
    ArrayList<Point> pot;
    int tezavnost;

    public Pot(int id, ArrayList<Point> pot, int tezavnost, String Ime) {
        this.id = id;
        this.pot = pot;
        this.tezavnost = tezavnost;
        this.ime = Ime;
    }

    public ArrayList<Point> getPot() {
        return pot;
    }

    public void setPot(ArrayList<Point> pot) {
        this.pot = pot;
    }

    public int getTezavnost() {
        return tezavnost;
    }

    public void setTezavnost(int tezavnost) {
        this.tezavnost = tezavnost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    @Override
    public String toString() {
        return "com.example.Pot{" +
                "pot=" + pot +
                ", tezavnost=" + tezavnost +
                '}';
    }
}
