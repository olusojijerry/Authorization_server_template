package com.work.authentication.server.config;

import com.work.authentication.server.domain.MainUsers;
import com.work.authentication.server.entity.CoreRole;
import com.work.authentication.server.entity.CoreUser;
import com.work.authentication.server.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/*this class retrieve the user from the database and sets it to the oauth mainusers entity*/
@Service
public class CurrentUsersDetails implements UserDetailsService {
    @Autowired
    AuthUserService authUserService;
    @Override
    public MainUsers loadUserByUsername(String username) throws UsernameNotFoundException {
        MainUsers mainUsers = new MainUsers();

        CoreUser coreUser = authUserService.findByUsername(username);

        if(ObjectUtils.isEmpty(coreUser))
            throw new UsernameNotFoundException("User does not exist");

        mainUsers.setEnabled(true);
        mainUsers.setUsername(coreUser.getUsername());
        String[] names = coreUser.getUsername().split(".");
        mainUsers.setLastName(names[1]);
        mainUsers.setFirstName(names[0]);
        mainUsers.setPassword(coreUser.getAppPassword());
        mainUsers.setHashedPassword(coreUser.getHashedUserPassword());
        if(!ObjectUtils.isEmpty(coreUser.getRole())) {
            CoreRole role = coreUser.getRole();
            mainUsers.setRoleId(role.getId());
            mainUsers.setRoleName(role.getName());
            mainUsers.setRoleDescription(role.getDescription());
        }
        mainUsers.setStaffId(coreUser.getStaffId());
        mainUsers.setId(coreUser.getId());

        return mainUsers;
    }
}
