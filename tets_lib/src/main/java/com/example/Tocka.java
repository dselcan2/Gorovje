package com.example;

import java.util.ArrayList;

/**
 * Created by Denis on 4. 06. 2017.
 */

public class Tocka {
    Point tocka;
    double dolzina;
    double povprecnaHitrost;
    String cas;

    public Tocka(Point tocka, double dolzina, double povprecnaHitrost, String cas) {
        this.tocka = tocka;
        this.dolzina = dolzina;
        this.povprecnaHitrost = povprecnaHitrost;
        this.cas = cas;
    }

    public Point getTocka() {
        return tocka;
    }

    public void setTocka(Point tocka) {
        this.tocka = tocka;
    }

    public double getDolzina() {
        return dolzina;
    }

    public void setDolzina(double dolzina) {
        this.dolzina = dolzina;
    }

    public double getPovprecnaHitrost() {
        return povprecnaHitrost;
    }

    public void setPovprecnaHitrost(double povprecnaHitrost) {
        this.povprecnaHitrost = povprecnaHitrost;
    }

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }
}
