package com.ucm.tfg.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.widget.ImageView;

public class ImageConverter {

    public ImageConverter(){}

    public ImageView convert(byte[] image, ImageView view){
        Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);
        view.setImageBitmap(bm);
        return view;
    }
}
