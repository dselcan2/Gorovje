package com.example;

import java.io.Serializable;

/**
 * Created by Denis on 2. 06. 2017.
 */

public class Pot implements Serializable {
    String ime;
    String cas;
    String tezavnost;
    String ocena;
    Point zacetek;

    public Pot(Pot pot) {
        this.ime = pot.getIme();
        this.cas = pot.getCas();
        this.tezavnost = pot.getTezavnost();
        this.ocena = pot.getOcena();
        this.zacetek = pot.getZacetek();
    }

    public Pot(String ime, String cas, String tezavnost, String ocena, Point zacetek) {
        this.ime = ime;
        this.cas = cas;
        this.tezavnost = tezavnost;
        this.zacetek = zacetek;

        this.ocena = ocena;
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

    public String getOcena() {
        return ocena;
    }

    public void setOcena(String ocena) {
        this.ocena = ocena;
    }

}
