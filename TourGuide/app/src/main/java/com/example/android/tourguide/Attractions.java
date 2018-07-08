package com.example.android.tourguide;

/**
 * Created by lhu513 on 4/28/18.
 */

public class Attractions {

    String name;

    String desc;

    public String getAddr() {
        return addr;
    }

    String addr;

    public int getImage() {
        return image;
    }

    int image;

    public Attractions(String nm, String desc, int im, String addr) {
        this.name = nm;
        this.desc = desc;
        this.image = im;
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

}
