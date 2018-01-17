package com.example;

import java.io.Serializable;

/**
 * Created by Denis on 27/02/2017.
 */

public class User implements Serializable {
    int ID;
    String Ime, Priimek;

    public User(int ID, String ime, String priimek) {
        this.ID = ID;
        Ime = ime;
        Priimek = priimek;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getIme() {
        return Ime;
    }

    public void setIme(String ime) {
        Ime = ime;
    }

    public String getPriimek() {
        return Priimek;
    }

    public void setPriimek(String priimek) {
        Priimek = priimek;
    }

    @Override
    public String toString() {
        return "com.example.User{" +
                "ID=" + ID +
                ", Ime='" + Ime + '\'' +
                ", Priimek='" + Priimek + '\'' +
                '}';
    }
}
