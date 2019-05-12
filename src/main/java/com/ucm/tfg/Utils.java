package com.ucm.tfg;

import android.widget.ImageView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;


public class Utils {

    /**
     * Given a date parameter it turns out into a string in the hh:mm format
     * @param date
     * @return the string in hh:mm format
     */
    public static String timeFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        String strDate = dateFormat.format(date);
        return strDate;
    }


    /**
     * Given a date parameter it turns out into a string in the dd-mm-yyyy format
     * @param date
     * @return the string in dd-mm-yyyy format
     */
    public static String dateFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    /**
     * Checks if the string is either null or empty ("")
     * @param s
     * @return true if empty or null, false otherwise
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }


}
