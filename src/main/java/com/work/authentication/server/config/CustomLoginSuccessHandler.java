package com.work.authentication.server.config;

import com.work.authentication.server.service.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired
    AuthUserService authUserService;

    CustomLoginSuccessHandler(){
        setRedirectStrategy(new CustomRedirectStrategy());
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        //here state what you want to happen on successful authentication.

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
