package com.vinmein.chefhuts.model;

import android.graphics.Color;
import android.util.Log;

import java.util.Random;

/**
 * Created by Vasanth on 5/6/2017.
 */

public class Tabledetail {

    private String Diningid;
    private String name;
    private String TableNumber;
    private String SeatingCount;
    private String Location;


    public Tabledetail(String diningid, String name, String Tableno, String seatingcnt, String location) {
        super();
        this.Diningid = diningid;
        this.name = name;
        this.TableNumber = Tableno;
        this.SeatingCount = seatingcnt;
        this.Location = location;


//int randomColor = Color.parseColor(allColors[new Random().nextInt(allColors.length)]);
    }

    public String getdiningid() {
        return Diningid;
    }

    public String getName() {
        return name;
    }

    public String getTableno() {
        return TableNumber;
    }

    public String getSeatingcnt() {
        return SeatingCount;
    }

    public String getlocation() {
        return Location;
    }


    public void setdiningId(String id) {
        this.Diningid = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTablenumber(String tnumber) {
        this.TableNumber =tnumber;
    }

    public void setSeatingCount(String Seatcnt) {
        this.SeatingCount = Seatcnt;
    }

    public void setLocation(String location) {
        this.Location = location;
    }
}
