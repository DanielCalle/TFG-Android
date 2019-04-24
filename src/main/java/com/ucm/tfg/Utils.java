package com.ucm.tfg;

import android.widget.ImageView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;


public class Utils {

    public static void convertImage(byte[] image, ImageView view){
        //Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);
        //view.setImageBitmap(bm);
    }

    public static String timeFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public static String dateFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }


}
