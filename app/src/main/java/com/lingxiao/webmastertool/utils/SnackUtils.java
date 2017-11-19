package com.lingxiao.webmastertool.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 * Created by lingxiao on 17-11-19.
 */

public class SnackUtils {
    private static Snackbar mSnack;
    public static void show(String msg, View view){
        if (mSnack == null){
            mSnack = Snackbar.make(view,msg,Snackbar.LENGTH_SHORT);
        }else {
            mSnack.setText(msg);
        }
        mSnack.show();
    }
}
