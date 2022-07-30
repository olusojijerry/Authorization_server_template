package com.work.authentication.server.config;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class CustomLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    CustomLoginFailureHandler(){
        this.setRedirectStrategy(new CustomFailureRedirectStrategy());
        //set the default redirect page in static folder
        this.setDefaultFailureUrl("/login");
    }
}
