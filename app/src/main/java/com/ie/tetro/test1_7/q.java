package com.ie.tetro.test1_7;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.ie.tetro.test1_7.Ma;

import java.util.ArrayList;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class q {
    public  static  String token;
    public static Ma ma;
    public static int typeOfusage = 2;
    /*
     0-presentation
     1-debuging
     2-production
     */
       static ArrayList<String> exceptionBuffer = new ArrayList<String>();

    public static void dumb() {

    }

    public static void m(String mess) {
        Log.d("tetroMess", mess);
    }

    public static void productionAlert() {
        if (typeOfusage == 2 && exceptionBuffer.size() > 0) {
            String mess;
            mess = "We apologize for the inconvenience. There was a wrong number " + String.valueOf(exceptionBuffer.size()) + ". We suggest checking Your internet connection and restarting the application.";
            exceptionBuffer.clear();
            q.ma.showT(mess);
        }
    }

    public static void mex(Exception e) {
        /*
        I consider that there are 3 situations requiring 3 different behaviors
        for handling exceptions, which are:
            typeOfusage:
            0-presentation
            1-debuging
            2-production
        */
        switch (typeOfusage) {
            case 0:
                Log.d("tetroMess", e.getLocalizedMessage()+",st:"+e.getStackTrace());
                break;
            case 1:
                q.ma.showT(e.getLocalizedMessage()+",st:"+e.getStackTrace());
                Log.d("errors", e.getLocalizedMessage());
                break;
            case 2:
                exceptionBuffer.add(e.getLocalizedMessage()+",st:"+e.getStackTrace());//production,just one error have to popup!!
                break;
        }
    }
}
