package com.example;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * Created by Denis on 27/02/2017.
 */

public class DataAll {
    private ArrayList<Gora> gore;
    private ArrayList<User> uporabniki;
    private ArrayList<Pot> poti;
    private ArrayList<Koca> koce;
    private ArrayList<ShraniPot> shranjenaPot;



    public DataAll(){
        gore = new ArrayList<>();
        uporabniki = new ArrayList<>();
        poti = new ArrayList<>();
        koce = new ArrayList<>();
        shranjenaPot = new ArrayList<>();
    }
    //public com.example.Gora(String naziv, com.example.Point tocka, double visina, String opis)
    public Gora addGora(String naziv, Point tocka, double visina, String opis, String slika, int id){
        Gora tmp = new Gora(naziv, tocka, visina, opis, slika, id);
        gore.add(tmp);
        return tmp;
    }
    //public com.example.User(int ID, String ime, String priimek)
    public User addUser(int ID, String ime, String priimek){
        User tmp = new User(ID, ime, priimek);
        uporabniki.add(tmp);
        return tmp;
    }
    //public com.example.Pot(ArrayList<com.example.Point> pot, int tezavnost)
    public Pot addPot(int id, ArrayList<Point> pot, int tezavnost, String Ime){
        Pot tmp = new Pot(id, pot, tezavnost, Ime);
        poti.add(tmp);
        return tmp;
    }
    //public com.example.Koca(String naziv, com.example.Point tocka, double visina, ArrayList<String> poudba)
    public Koca addKoca(String naziv, Point tocka, double visina, ArrayList<String> ponudba){
        Koca tmp = new Koca(naziv, tocka, visina, ponudba);
        koce.add(tmp);
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

    public ArrayList<Pot> getPoti() {
        return poti;
    }

    public ArrayList<Koca> getKoce() {
        return koce;
    }

    public ArrayList<ShraniPot> getShranjenaPot() {
        return shranjenaPot;
    }

    @Override
    public String toString() {
        return "com.example.DataAll{" +
                "gore=" + gore +
                ", uporabniki=" + uporabniki +
                ", poti=" + poti +
                ", koce=" + koce +
                '}';
    }
    public static DataAll scenarij1(){
        DataAll dat = new DataAll();
        dat.addGora("Triglav", new Point(46.380748, 13.832683),2864, "Najvecja slovenska gora", "triglav001", 0);
        dat.addGora("Spik", new Point(43.380748, 34.832683),2664, "vrh je spicast", "spik",1);
        dat.addGora("Prisojnik", new Point(41.380748, 73.832683),2564, "obrnjena proti soncu", "prisojnik",2);
        dat.addGora("Triglav", new Point(46.380748, 13.832683),2864, "Najvecja slovenska gora", "triglav001", 0);
        dat.addGora("Spik", new Point(43.380748, 34.832683),2664, "vrh je spicast", "spik",1);
        dat.addGora("Prisojnik", new Point(41.380748, 73.832683),2564, "obrnjena proti soncu", "prisojnik",2);
        dat.addGora("Triglav", new Point(46.380748, 13.832683),2864, "Najvecja slovenska gora", "triglav001", 0);
        dat.addGora("Spik", new Point(43.380748, 34.832683),2664, "vrh je spicast", "spik",1);
        dat.addGora("Prisojnik", new Point(41.380748, 73.832683),2564, "obrnjena proti soncu", "prisojnik",2);
        dat.addGora("Triglav", new Point(46.380748, 13.832683),2864, "Najvecja slovenska gora", "triglav001", 0);
        dat.addGora("Spik", new Point(43.380748, 34.832683),2664, "vrh je spicast", "spik",1);
        dat.addGora("Prisojnik", new Point(41.380748, 73.832683),2564, "obrnjena proti soncu", "prisojnik",2);
        dat.addGora("Triglav", new Point(46.380748, 13.832683),2864, "Najvecja slovenska gora", "triglav001", 0);
        dat.addGora("Spik", new Point(43.380748, 34.832683),2664, "vrh je spicast", "spik",1);
        dat.addGora("Prisojnik", new Point(41.380748, 73.832683),2564, "obrnjena proti soncu", "prisojnik",2);
        ArrayList<String> ponudba = new ArrayList<>();
        ponudba.add("palacinke");
        ponudba.add("jota");
        ponudba.add("pivo");
        ponudba.add("ledeni caj");
        dat.addKoca("Triglavski dom na kredarici", new Point(46.379494, 13.848485),2515, ponudba);
        ArrayList<Point> pot1 = new ArrayList<>();
        pot1.add(new Point(46.3446343,13.9198199));
        pot1.add(new Point(46.3791994, 13.8317281));
        dat.addPot(0,pot1,7, "Rudno polje - Triglav");
        ArrayList<Point> pot4 = new ArrayList<>();
        pot4.add(new Point(46.4128,13.8466));
        pot4.add(new Point(46.3791994, 13.8317281));
        dat.addPot(0,pot4,7, "Aljazev dom v vratih - Triglav");
        dat.addPot(0,null,7, "TEST");
        dat.addPot(0,null,7, "TEST");
        dat.addPot(0,null,7, "TEST");
        dat.addPot(0,null,7, "TEST");
        dat.addPot(0,null,7, "TEST");
        dat.addPot(0,null,7, "TEST");
        dat.addPot(0,null,7, "TEST");
        dat.addPot(0,null,7, "TEST");
        dat.addPot(0,null,7, "TEST");
        dat.addPot(0,null,7, "TEST");
        dat.addPot(0,null,7, "TEST");
        ArrayList<Point> pot2 = new ArrayList<>();
        pot2.add(new Point(46.432897,13.7408822));
        pot2.add(new Point(46.42464,13.76974));
        dat.addPot(2,pot2,6, "vrsic - Prisojnik");
        ArrayList<Point> pot3 = new ArrayList<>();
        pot3.add(new Point(46.5370623,15.5995941));
        pot3.add(new Point(46.4486111,13.8119779));
        dat.addPot(1,pot3,5, "Ruski kriz - Spik");
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
