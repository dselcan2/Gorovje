package com.example;

import java.util.ArrayList;

/**
 * Created by Denis on 27/02/2017.
 */

public class Gora {
    String naziv;
    Point tocka;
    double visina;
    String slika;
    String gorovje;
    ArrayList<Pot> zacetki = new ArrayList<>();

    public ArrayList<Pot> getZacetki() {
        return zacetki;
    }

    public void setZacetki(ArrayList<Pot> zacetki) {
        this.zacetki = zacetki;
    }

    public String getGorovje() {
        return gorovje;
    }

    public void setGorovje(String gorovje) {
        this.gorovje = gorovje;
    }

    public Gora(String naziv, Point tocka, double visina, String slika, ArrayList<Pot> zacetki, String gorovje) {
        this.naziv = naziv;
        this.tocka = tocka;
        this.visina = visina;
        this.slika = slika;
        this.zacetki = zacetki;
        this.gorovje = gorovje;
    }

    public Gora() {
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

    public String getSlika() { return slika;}

    public void setSlika(String slika) {this.slika = slika;}

    @Override
    public String toString() {
        /*return "com.example.Gora{" +
                "naziv='" + naziv + '\'' +
                ", tocka=" + tocka +
                ", visina=" + visina +
                ", opis='" + opis + '\'' +
                '}';*/
        return  naziv;
    }
}
