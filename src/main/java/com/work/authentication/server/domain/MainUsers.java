package com.work.authentication.server.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@Data
public class MainUsers implements UserDetails {
    Long id;
    String username;
    String hashedPassword;
    //this should application password
    String password;
    //remove
    String org;
    //application Id
    String loginId;
    //not needed
    String firstName;
    //not needed
    String lastName;
    Date lastLoginDate;
    //not needed
    String branchNo;
    String staffId;
    //not needed
    String branchName;
    //not needed
    String emailAddress;
    Long roleId;
    String roleName;
    String roleDescription;
    //not needed
    String adBranchNo;
    Collection<? extends GrantedAuthority> authorities;
    boolean accountNonExpired;
    boolean isAccountLocked;
    boolean enabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
