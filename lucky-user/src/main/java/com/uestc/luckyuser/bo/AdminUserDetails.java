package com.uestc.luckyuser.bo;

import com.uestc.luckyuser.model.User;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jacob
 * @date 2022/4/21 14:54
 */
public class AdminUserDetails implements UserDetails {


    private String password;
    private String mobilePhoneNumber;
    private Collection<? extends GrantedAuthority> authorities;

    public AdminUserDetails(User user) {
        this.password = user.getPassword();
        this.mobilePhoneNumber = user.getMobilePhoneNumber();
        List<SimpleGrantedAuthority> roles = Arrays.stream(user.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        this.authorities = roles;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public void setUsername(String username) {
        this.mobilePhoneNumber = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return mobilePhoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
