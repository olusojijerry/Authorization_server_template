package com.work.authentication.server.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

public class Util {
    private Pattern usernamePattern = Pattern.compile(Constant.usernamePattern);

    public static boolean isValidString(String str){
        if (str != null && str.trim().length()>0)
            return true;

        return false;
    }

    public static String getBaseURL(HttpServletRequest request){
        String scheme = request.getScheme() + "://";
        String serverName = request.getServerName();
        String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
        String contextPath = request.getContextPath();

        return scheme +serverName+serverPort+contextPath;
    }

}
