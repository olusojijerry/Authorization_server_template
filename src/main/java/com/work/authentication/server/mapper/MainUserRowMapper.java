package com.work.authentication.server.mapper;

import com.work.authentication.server.domain.MainUsers;
import com.work.authentication.server.utils.Constant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MainUserRowMapper implements RowMapper<MainUsers> {
    @Override
    public MainUsers mapRow(ResultSet rs, int rowNum) throws SQLException {
        MainUsers mainUsers = new MainUsers();

        mainUsers.setId(rs.getLong("id"));
        mainUsers.setUsername(rs.getNString("username"));
        mainUsers.setFirstName(rs.getNString("firstname"));
        mainUsers.setLastName(rs.getNString("lastname"));
        mainUsers.setEnabled(rs.getNString("status") != null &&
                rs.getString("status").equalsIgnoreCase(Constant.ACTIVE));

        return mainUsers;
    }
}
