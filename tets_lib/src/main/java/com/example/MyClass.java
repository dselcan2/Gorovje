package com.example;

public class MyClass {
    private int test1;
    private String test2;

    public MyClass(int test1, String test2) {
        this.test1 = test1;
        this.test2 = test2;
    }

    public int getTest1() {
        return test1;
    }

    public void setTest1(int test1) {
        this.test1 = test1;
    }

    public String getTest2() {
        return test2;
    }

    public void setTest2(String test2) {
        this.test2 = test2;
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "test1=" + test1 +
                ", test2='" + test2 + '\'' +
                '}';
    }
}
