package com.example.demo.Tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Convert {
    public static String SHA(String s) throws NoSuchAlgorithmException {
        MessageDigest md=MessageDigest.getInstance("SHA");
        md.update(s.getBytes());
        String str=new BigInteger(md.digest()).toString(32);
        return str;

    }

    public static String splitAndFilterString(String input, int length) {
        if (input == null || input.trim().equals("")) {
            return "";
        }
        // 去掉所有html元素,
        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                "<[^>]*>", "");
        str = str.replaceAll("[(/>)<]", "");
        int len = str.length();
        if (len <= length) {
            return str;
        } else {
            str = str.substring(0, length);
            str += "......";
        }
        return str;
    }




}
