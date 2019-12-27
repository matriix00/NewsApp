package com.example.newsapp.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppConstants {

    public static final String API_KEY = "374888e53f7c452d870f367edf3e1838";
    public static Date parse(String message) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd'T'HH:mm:ssz");

        if (message.endsWith("Z")){
            message = message.substring(0,message.length()-1)+"GMT-00:00";
        }else {
            int inset = 6;
            String s0 = message.substring(0,message.length()-inset);
            String s1 = message.substring(message.length()-inset,message.length());
            message = s0+ "GMT" +s1;

        }
        return dateFormat.parse(message);
    }
}
