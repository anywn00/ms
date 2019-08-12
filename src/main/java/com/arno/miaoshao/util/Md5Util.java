package com.arno.miaoshao.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author Arno
 * @ data 2019/8/12 14:20.
 */
public class Md5Util {


    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }


    public static final String salt = "1a2b3c4d";
    public static String inputPassToFormPass(String str) { //a21f3933b27eb9b38711211e45b54fe9
        String pass = "" + salt.charAt(0) + salt.charAt(1) + str + salt.charAt(4) + salt.charAt(5);
        return md5(pass);
    }


    public static String formPassToDbPass(String pass, String slat) {
        String str = "" + slat.charAt(0) + slat.charAt(1) + pass + slat.charAt(4) + slat.charAt(5);
        return md5(str);
    }

    public static String inputPassToDbPass(String pass, String slat){
        String str = inputPassToFormPass(pass);
        return formPassToDbPass(str, slat);
    }


    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("111222"));
    }

}
