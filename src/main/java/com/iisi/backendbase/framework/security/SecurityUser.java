package com.iisi.backendbase.framework.security;

import com.iisi.backendbase.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {

    private List<GrantedAuthority> authorities = new ArrayList<>();
    private User user;

    public SecurityUser(User user) {
        this.user = user;
        user.getRoles().forEach(t -> {
            addRoles(t.getRoleId().toString());
        });
    }

    @Override
    // 取得所有權限
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return this.authorities;
    }

    @Override
    // 取得使用者名稱
    public String getUsername() {
        // TODO Auto-generated method stub
        return this.user.getUsername();
    }

    @Override
    // 取得密碼
    public String getPassword() {
        // TODO Auto-generated method stub
        return this.user.getPassword();
    }

    @Override
    // 帳號是否過期
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    // 帳號是否被鎖定
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    // 憑證/密碼是否過期
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    // 帳號是否可用
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

    public void addRoles(final String... roles) {
        for (final String role : roles) {
            addRoles(role);
        }
    }

    public void addRoles(final String role) {
        this.authorities.add(new SimpleGrantedAuthority(role));
    }
}