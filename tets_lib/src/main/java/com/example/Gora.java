package com.example;

/**
 * Created by Denis on 27/02/2017.
 */

public class Gora {
    int id;
    String naziv;
    Point tocka;
    double visina;
    String opis;
    String Slika;

    public Gora(String naziv, Point tocka, double visina, String opis, String slika, int id) {
        this.naziv = naziv;
        this.tocka = tocka;
        this.visina = visina;
        this.opis = opis;
        this.Slika = slika;
        this.id = id;
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

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSlika() { return Slika;}

    public void setSlika(String slika) {this.Slika = slika;}

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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
