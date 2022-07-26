package com.work.authentication.server.utils;

import java.util.regex.Pattern;

public class Util {
    private Pattern usernamePattern = Pattern.compile(Constant.usernamePattern);

    public static boolean isValidString(String str){
        if (str != null && str.trim().length()>0)
            return true;

        return false;
    }
}
