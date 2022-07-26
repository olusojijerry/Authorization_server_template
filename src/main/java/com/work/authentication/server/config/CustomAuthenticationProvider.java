package com.work.authentication.server.config;

import com.work.authentication.server.domain.MainUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;

//this class decides how authentication of users should be using the username and password
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    CurrentUsersDetails currentUsersDetails;
    @Autowired
    PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider() {
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        MainUsers users = currentUsersDetails.loadUserByUsername(username.trim());
        if(!users.isEnabled()){
            throw new BadCredentialsException(username + " :not active on Ad");
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();

        final String usernameToUppercase = username.toUpperCase();

        if(StringUtils.hasText(users.getHashedPassword())){
            if(!passwordEncoder.matches(users.getHashedPassword(), password)){
                throw new BadCredentialsException("Invalid user credentials");
            }
        }else {
            //todo for this part
            //here we check if the user is an application or a user
            //if he is a user then we will be using pin and token else
            //if it is an application then we will be using configured password
            //or else just grant access based on if allowed
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
