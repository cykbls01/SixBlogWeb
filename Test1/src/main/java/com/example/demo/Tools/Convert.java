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



}
