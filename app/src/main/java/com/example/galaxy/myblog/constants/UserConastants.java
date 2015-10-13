package com.example.galaxy.myblog.constants;

import android.graphics.Bitmap;

/**
 * Created by galaxy on 12/10/15.
 */
public class UserConastants
{
    public static UserConastants constants = null;

    public static String name;
    public static String email;
    public static Bitmap imageBitmap;

    public static UserConastants shared()
    {
        if (constants == null)
        {
            constants = new UserConastants();
        }
        return constants;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserConastants.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserConastants.email = email;
    }

    public static Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public static void setImageBitmap(Bitmap imageBitmap) {
        UserConastants.imageBitmap = imageBitmap;
    }


}
