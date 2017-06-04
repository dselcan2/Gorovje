package com.example;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by Denis on 27/02/2017.
 */

public class DataAll {
    private ArrayList<Gora> gore;
    private ArrayList<User> uporabniki;
    private ArrayList<ShraniPot> shranjenaPot;
    final public String JULIJSKE_ALPE = "julijske Alpe";
    final public String KAMNISKOSAVINJSE_ALPE = "kamniskosavinjske Alpe";
    final public String KARAVANKE = "karavanke";
    final public String POHORJE = "pohorje";
    final public String SNEZNIK = "sneznik";
    final public String SKOFJELOSKO_HRIBOVJE = "skofjelosko hribovje";
    final public String VZHODNOSLOVENSKO_HRIBOVJE = "vzhodnoslovensko hribovje";



    public DataAll(){
        gore = new ArrayList<>();
        uporabniki = new ArrayList<>();
        shranjenaPot = new ArrayList<>();
    }
    //public com.example.Gora(String naziv, com.example.Point tocka, double visina, String opis)
    public Gora addGora(String naziv, Point tocka, double visina, String opis, String slika, ArrayList<Pot> zacetki, String gorovje){
        Gora tmp = new Gora(naziv, tocka, visina, slika, zacetki, gorovje);
        gore.add(tmp);
        return tmp;
    }
    //public com.example.User(int ID, String ime, String priimek)
    public User addUser(int ID, String ime, String priimek){
        User tmp = new User(ID, ime, priimek);
        uporabniki.add(tmp);
        return tmp;
    }

    public ShraniPot addShranjenaPot(ArrayList<Point> pot, String time, double length, double visina){
        ShraniPot tmp = new ShraniPot(pot, time, length, visina);
        shranjenaPot.add(tmp);
        return tmp;
    }

    public void addShranjenaPot(ShraniPot pot){
        shranjenaPot.add(pot);
    }

    public ArrayList<Gora> getGore() {
        return gore;
    }

    public ArrayList<User> getUporabniki() {
        return uporabniki;
    }

    public ArrayList<ShraniPot> getShranjenaPot() {
        return shranjenaPot;
    }

    @Override
    public String toString() {
        return "com.example.DataAll{" +
                "gore=" + gore +
                ", uporabniki=" + uporabniki +
                '}';
    }
    public static DataAll scenarij1(){
        DataAll dat = new DataAll();
        ArrayList<Point> triglav = new ArrayList<>();
        triglav.add(new Point(46.4105329,13.8422495));
        triglav.add(new Point(46.3386,13.9045));
        triglav.add(new Point(46.2896,13.8022));
        //dat.addGora("Triglav", new Point(46.380748, 13.832683),2864, "Najvecja slovenska gora", "triglav001", triglav ,dat.JULIJSKE_ALPE);
        ArrayList<Point> spik = new ArrayList<>();
        spik.add(new Point(46.4688,13.7833));
        spik.add(new Point(46.4444,13.7746));
        spik.add(new Point(46.4688,13.7833));
        //dat.addGora("Spik", new Point(43.380748, 34.832683),2472, "vrh je spicast", "spik", spik, dat.JULIJSKE_ALPE);
        ArrayList<Point> prisojnik = new ArrayList<>();
        prisojnik.add(new Point(46.4336,13.7435));
        prisojnik.add(new Point(46.4399,13.7624));
        prisojnik.add(new Point(46.4391,13.7483));
        //dat.addGora("Prisojnik", new Point(41.380748, 73.832683),2547, "obrnjena proti soncu", "prisojnik", prisojnik, dat.JULIJSKE_ALPE);
        ArrayList<String> ponudba = new ArrayList<>();
        ponudba.add("palacinke");
        ponudba.add("jota");
        ponudba.add("pivo");
        ponudba.add("ledeni caj");
        ArrayList<Point> pot1 = new ArrayList<>();
        pot1.add(new Point(46.3446343,13.9198199));
        pot1.add(new Point(46.3791994, 13.8317281));
        ArrayList<Point> pot4 = new ArrayList<>();
        pot4.add(new Point(46.4128,13.8466));
        pot4.add(new Point(46.3791994, 13.8317281));
        ArrayList<Point> pot2 = new ArrayList<>();
        pot2.add(new Point(46.432897,13.7408822));
        pot2.add(new Point(46.42464,13.76974));
        ArrayList<Point> pot3 = new ArrayList<>();
        pot3.add(new Point(46.5370623,15.5995941));
        pot3.add(new Point(46.4486111,13.8119779));
        dat.addUser(1,"Denis","Selcan");
        ArrayList<Point> pot = new ArrayList<>();
        pot.add(new Point( 15.6004783, 46.5373304));
        pot.add(new Point( 15.6714532, 46.5465528));
        ArrayList<Point> pot_1 = new ArrayList<>();
        pot_1.add(new Point(23.5373304, 32.6004783));
        pot_1.add(new Point(12.5373304, 78.6004783));
        ArrayList<Point> pot_2 = new ArrayList<>();
        pot_2.add(new Point(11.5373304, 12.6004783));
        pot_2.add(new Point(13.5373304, 14.6004783));
        dat.addShranjenaPot(pot,"00:07:34", 1000, 10);
        dat.addShranjenaPot(pot_1,"01:02:41", 4000, 11);
        dat.addShranjenaPot(pot_2,"00:47:32", 3200, 12);
        return dat;
    }
}
