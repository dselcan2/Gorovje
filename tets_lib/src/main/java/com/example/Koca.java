package com.example;

import java.util.ArrayList;

/**
 * Created by Denis on 27/02/2017.
 */

public class Koca {
    String naziv;
    Point tocka;
    double visina;
    ArrayList<String> poudba;

    public Koca(String naziv, Point tocka, double visina, ArrayList<String> poudba) {
        this.naziv = naziv;
        this.tocka = tocka;
        this.visina = visina;
        this.poudba = poudba;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Point getTocka() {
        return tocka;
    }

    public void setTocka(Point tocka) {
        this.tocka = tocka;
    }

    public double getVisina() {
        return visina;
    }

    public void setVisina(double visina) {
        this.visina = visina;
    }

    public ArrayList<String> getPoudba() {
        return poudba;
    }

    public void setPoudba(ArrayList<String> poudba) {
        this.poudba = poudba;
    }

    @Override
    public String toString() {
        return "com.example.Koca{" +
                "naziv='" + naziv + '\'' +
                ", tocka=" + tocka +
                ", visina=" + visina +
                ", poudba=" + poudba +
                '}';
    }
}
