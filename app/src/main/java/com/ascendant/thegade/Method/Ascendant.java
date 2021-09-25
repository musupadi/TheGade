package com.ascendant.thegade.Method;

import android.util.Base64;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    public String Today(){
        DateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dates = dateFormat3.format(date);
        return dates;
    }
    public static String thisDay(){
        DateFormat dateFormat1 = new SimpleDateFormat("dd");
        DateFormat dateFormat2 = new SimpleDateFormat("MM");
        DateFormat dateFormat3 = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String Day = dateFormat1.format(date);
        String month = dateFormat2.format(date);
        String Year = dateFormat3.format(date);
        String MONTH = "Januari";
        if (month.equals("01") || month.equals("1")){
            MONTH = "Januari";
        }else if (month.equals("02") || month.equals("2")){
            MONTH = "Februari";
        }else if (month.equals("03") || month.equals("3")){
            MONTH = "Maret";
        }else if (month.equals("04") || month.equals("4")){
            MONTH = "April";
        }else if (month.equals("05") || month.equals("5")){
            MONTH = "Mei";
        }else if (month.equals("06") || month.equals("6")){
            MONTH = "Juni";
        }else if (month.equals("07") || month.equals("7")){
            MONTH = "Juli";
        }else if (month.equals("08") || month.equals("8")){
            MONTH = "Agustus";
        }else if (month.equals("09") || month.equals("9")){
            MONTH = "September";
        }else if (month.equals("10")){
            MONTH = "Oktober";
        }else if (month.equals("11")){
            MONTH = "November";
        }else if (month.equals("12")){
            MONTH = "Desember";
        }
        return Day+"-"+MONTH+"-"+Year;
    }
    public  String DateChanges(String year,String month,String day){
        String MONTH = "Januari";
        if (month.equals("01") || month.equals("1")){
            MONTH = "Januari";
        }else if (month.equals("02") || month.equals("2")){
            MONTH = "Februari";
        }else if (month.equals("03") || month.equals("3")){
            MONTH = "Maret";
        }else if (month.equals("04") || month.equals("4")){
            MONTH = "April";
        }else if (month.equals("05") || month.equals("5")){
            MONTH = "Mei";
        }else if (month.equals("06") || month.equals("6")){
            MONTH = "Juni";
        }else if (month.equals("07") || month.equals("7")){
            MONTH = "Juli";
        }else if (month.equals("08") || month.equals("8")){
            MONTH = "Agustus";
        }else if (month.equals("09") || month.equals("9")){
            MONTH = "September";
        }else if (month.equals("10")){
            MONTH = "Oktober";
        }else if (month.equals("11")){
            MONTH = "November";
        }else if (month.equals("12")){
            MONTH = "Desember";
        }
        String Dates = day+"-"+MONTH+"-"+year;
        return Dates;
    }
}
