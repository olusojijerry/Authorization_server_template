package com.work.authentication.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.authentication.server.domain.LoginRequest;
import com.work.authentication.server.utils.Constant;
import com.work.authentication.server.utils.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

@Slf4j
public class CustomPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Autowired
    Util util;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("Authentication using filter");

        String username = null;
        String password = null;

        if (!request.getMethod().equals(Constant.POST_REQUEST_TYPE)){
            throw new AuthenticationServiceException("Method not supported");
        }

        UsernamePasswordAuthenticationToken authRequest = null;

        if (Constant.JSON_APPLICATION_TYPE.equals(request.getHeader(Constant.HEADER_CONTENT_TYPE)) ||
                Constant.CONTENT_TYPE_JSON_CHAR_SET.equalsIgnoreCase(request
                        .getHeader(Constant.HEADER_CONTENT_TYPE))){
            LoginRequest loginRequest = null;

            try{
                loginRequest = getLoginRequest(request);
            }catch (Exception ex){
                throw new AuthenticationServiceException("Invalid Request Type");
            }
            username = loginRequest.getUsername();
            password = loginRequest.getPassword();
        }else{
            username = request.getParameter(Constant.USERNAME);
            password = request.getParameter(Constant.PASSWORD);
        }

        if (!Util.isValidString(username) || !Util.isValidString(password))
            throw new AuthenticationServiceException("Invalid Authorisation details");

        authRequest = new UsernamePasswordAuthenticationToken(username,password);

        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private LoginRequest getLoginRequest(HttpServletRequest req) throws Exception{
        BufferedReader reader = null;
        LoginRequest loginRequest = new LoginRequest();

        try{
            StringBuffer sb = new StringBuffer();
            String line = null;

            reader = req.getReader();
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
            ObjectMapper obj = new ObjectMapper();

            loginRequest = obj.readValue(sb.toString(), LoginRequest.class);
        }catch (Exception ex){
            throw ex;
        }

        return loginRequest;
    }
}
