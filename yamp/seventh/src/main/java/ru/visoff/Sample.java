package ru.visoff;

import java.util.List;

public class Sample {
    private int number;
    private String str;
    private List<String> strs;

    Sample(int number, String str, List<String> strs) {
        this.number = number;
        this.str = str;
        this.strs = strs;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public List<String> getStrs() {
        return strs;
    }

    public void setStrs(List<String> strs) {
        this.strs = strs;
    }
}
