package com.womensafety.util;

import java.util.regex.Pattern;

public class Validator {
    private static final Pattern EMAIL  = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern MOBILE = Pattern.compile("^[6-9][0-9]{9}$");   // 10-digit Indian mobile

    public static boolean isBlank(String s)      { return s == null || s.trim().isEmpty(); }
    public static boolean validEmail(String s)   { return s != null && EMAIL.matcher(s).matches(); }
    public static boolean validMobile(String s)  { return s != null && MOBILE.matcher(s).matches(); }
    public static boolean validPassword(String s){ return s != null && s.length() >= 8; }
    public static String  clean(String s)        { return s == null ? "" : s.trim(); }
}
