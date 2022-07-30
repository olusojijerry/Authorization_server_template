package com.work.authentication.server.config;

import com.work.authentication.server.utils.Constant;
import com.work.authentication.server.utils.Util;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
       log.info("Custom authentication Entry point");

       String baseUrl = Util.getBaseURL(httpServletRequest);

       log.info("AUTH EXCEPTION====" + e.getMessage());

       String acceptHeader = httpServletRequest.getHeader("accept");
       if(Util.isValidString(acceptHeader) && (Constant.JSON_APPLICATION_TYPE.equals(acceptHeader)
            || acceptHeader.contains(Constant.JSON_APPLICATION_TYPE))){
           httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           httpServletResponse.addHeader("WWW-Authenticate", "FormBased");

           JSONObject jsonObject = new JSONObject();
           jsonObject.put("success", Boolean.FALSE);
           jsonObject.put("reason", e.getMessage());
           jsonObject.put("access-denied", Boolean.TRUE);
           jsonObject.put("cause", "SC_UNAUTHORIZED");

           httpServletResponse.setStatus(401);
           PrintWriter out = httpServletResponse.getWriter();
           out.println(jsonObject);
           out.flush();
           out.close();
       }else{
           String queryString = httpServletRequest.getQueryString();
           if(Util.isValidString(queryString)){
               queryString += "?" + queryString;
           }else{
               queryString = "";
           }
           httpServletResponse.sendRedirect(baseUrl.trim() + "" + "?done=" + baseUrl.trim() +
                   httpServletRequest.getServletPath().trim() + queryString);
       }
    }
}
