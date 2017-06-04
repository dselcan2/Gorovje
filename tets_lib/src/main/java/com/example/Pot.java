package com.example;

/**
 * Created by Denis on 2. 06. 2017.
 */

public class Pot {
    String ime;
    String cas;
    String tezavnost;
    Point zacetek;

    public Pot(String ime, String cas, String tezavnost, Point zacetek) {
        this.ime = ime;
        this.cas = cas;
        this.tezavnost = tezavnost;
        this.zacetek = zacetek;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public String getTezavnost() {
        return tezavnost;
    }

    public void setTezavnost(String tezavnost) {
        this.tezavnost = tezavnost;
    }

    public Point getZacetek() {
        return zacetek;
    }

    public void setZacetek(Point zacetek) {
        this.zacetek = zacetek;
    }
}
