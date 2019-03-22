package com.ucm.tfg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class Utils {

    public static void convertImage(byte[] image, ImageView view){
        Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);
        view.setImageBitmap(bm);
    }
}
