package com.example;

import java.util.ArrayList;

/**
 * Created by Denis on 16/04/2017.
 */

public class ShraniPot {
    ArrayList<Point> pot;
    String time;
    double length;
    double visina;

    public ShraniPot(ArrayList<Point> pot, String time, double length, double visina) {
        this.pot = pot;
        this.time = time;
        this.length = length;
        this.visina = visina;
    }
}
