package com.arno.miaoshao.util;

import java.util.UUID;

/**
 * @Author arno
 * @date 2019-08-12 22:05
 */
public class UUIDUtil {

    public static String getId() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static void main(String[] args) {
        System.out.println(getId());
    }
}

