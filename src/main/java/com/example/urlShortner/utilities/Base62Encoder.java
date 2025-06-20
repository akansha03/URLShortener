package com.example.urlShortner.utilities;

public class Base62Encoder {

    private static final String CHAR_SET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String encode(long num) {
        StringBuilder sb = new StringBuilder();
        while(num>0) {
            sb.append(CHAR_SET.charAt((int)(num%62)));
            num/=62;
        }
        return sb.reverse().toString();
    }

}
