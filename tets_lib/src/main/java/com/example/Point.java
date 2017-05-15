package com.example;

/**
 * Created by Denis on 27/02/2017.
 */

public class Point {
    double longitude, latitude;

    public Point(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void addPoint(double longitude, double latitude){this.longitude = longitude; this.latitude = latitude;}

    @Override
    public String toString() {
        return "com.example.Point{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
