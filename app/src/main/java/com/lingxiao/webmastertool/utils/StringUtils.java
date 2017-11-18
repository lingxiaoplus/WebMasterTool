package com.lingxiao.webmastertool.utils;

/**
 * Created by lingxiao on 17-11-18.
 */

public class StringUtils {
    public static String mainRegex = "[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(/.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+/.?";
    public static boolean regexStr(String s){
        if (s == "" || s == null){
            return false;
        }
        return s.matches(mainRegex);
    }
}
