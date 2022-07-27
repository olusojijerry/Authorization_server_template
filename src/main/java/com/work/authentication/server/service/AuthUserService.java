package com.work.authentication.server.service;

import com.work.authentication.server.domain.MainUsers;
import com.work.authentication.server.entity.CoreUser;
import com.work.authentication.server.repository.services.CoreUsersService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class AuthUserService {
    private static final String UPDATE_LAST_LOGIN_DATE_SQL = "UPDATE USERS SET LAST_LOGIN_DT = ? WHERE ID = ?";
    private static final String UPDATE_LAST_LOGIN_DATE_AND_BRANCH_SQL = "UPDATE USERS SET LAST_LOGIN_DT = ?, BRANCH_NO = ? WHERE ID = ?";
    private static final String UPDATE_USERNAME_SQL = "UPDATE USERS SET USERNAME = ?, FIRST_NAME = ?, " +
            "LAST_NAME = ?, EMAIL_ADDRESS = ?, BRANCH_NO = ? WHERE ID = ?";
    private static final String UPDATE_STAFF_ID_SQL = "UPDATE USERS SET STAFF_ID = ?, FIRST_NAME = ?, LAST_NAME = ?, EMAIL_ADDRESS = ?," +
            "BRANCH_NO = ? WHERE ID = ?";

    @Autowired
    EntityManager entityManager;
    @Autowired
    CoreUsersService coreUsersService;

    @Transactional
    public void updateUsersInfo(@NonNull Long userId, @NonNull String branchNo, @NonNull MainUsers mainUsers){

    }

    public CoreUser findByUsername(String username){
        return coreUsersService.findByusername(username);
    }


}
