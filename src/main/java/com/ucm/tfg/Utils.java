package com.ucm.tfg;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ucm.tfg.entities.User;
import com.ucm.tfg.service.Service;
import com.ucm.tfg.service.UserService;


public class Utils {

    public static void convertImage(byte[] image, ImageView view){
        //Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);
        //view.setImageBitmap(bm);
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }


}
