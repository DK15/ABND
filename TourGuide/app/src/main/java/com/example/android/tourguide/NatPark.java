package com.example.android.tourguide;

/**
 * Created by lhu513 on 4/29/18.
 */

public class NatPark {

    String name;

    String desc;

    String addr;

    public String getAddr() {
        return addr;
    }

    public NatPark(String nm, String desc, String addr) {
        this.name = nm;
        this.desc = desc;
        this.addr = addr;

    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

}
