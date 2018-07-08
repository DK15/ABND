package com.example.android.tourguide;

/**
 * Created by lhu513 on 4/29/18.
 */

public class Food {

    String name;

    public String getAddr() {
        return addr;
    }

    String addr;

    public String getDesc() {
        return desc;
    }

    String desc;

    public Food(String nm, String desc, String addr) {
        this.name = nm;
        this.desc = desc;
        this.addr = addr;
    }

    public String getName() {
        return name;
    }

}
