package com.example.han.coaching;

import java.util.ArrayList;

/**
 * Created by han on 2015-11-24.
 */
public class staticMerge {
    static String temp="clear";
    static String what="nothing";
    static String dong = "";
    static String bunji = "";
    static String[] finish_food = new String[5];
    static ArrayList<String> food = new ArrayList<>();
    static ArrayList<String> foodAnni = new ArrayList<>();
    static boolean timer = false;

    public static void idTotemp (int i) {
        if(200<=i && i<300) {
            temp = "thunderstorm";
        } else if(500<=i && i<600) {
            temp = "rain";
        } else if(600<=i && i<700){
            temp = "snow";
        } else if ( i==761) {
            temp = "dust";
        } else if(i == 800) {
            temp = "clear";
        } else if(800<i && i<900) {
            temp = "clouds";
        } else if(i == 903) {
            temp = "cold";
        } else if(i==904) {
            temp = "hot";
        } else if(i==905) {
            temp = "windy";
        } else if(i==902) {
            temp = "hurricane";
        } else if(i == 960) {
            temp = "storm";
        }
    }
}

