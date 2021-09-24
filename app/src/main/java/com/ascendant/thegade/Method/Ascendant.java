package com.ascendant.thegade.Method;

import android.util.Base64;

public class Ascendant {
    public String AUTH(){
        String username = "gade-api";
        String password = "963852741";

        String base = username+":"+password;

        String authHeader = "Basic "+ Base64.encodeToString(base.getBytes(),Base64.NO_WRAP);
        return authHeader;
    }
    public String SmallDescription(String description){
        String Des = description;
        if (description.length() >= 100){
            Des = description.substring(0,100)+"...";
        }
        return Des;
    }
    public String TimeChanger(String time){
        String Times = time.substring(0,8);
        return Times;
    }
}
