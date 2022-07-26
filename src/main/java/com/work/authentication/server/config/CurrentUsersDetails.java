package com.work.authentication.server.config;

import com.work.authentication.server.domain.MainUsers;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/*this class retrieve the user from the database and sets it to the oauth mainusers entity*/
public class CurrentUsersDetails implements UserDetailsService {
    @Override
    public MainUsers loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
