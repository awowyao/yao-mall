package org.cwy.cloud.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/24 17:30
 */
@Data
public class mallUserDetails implements UserDetails {
    private Integer id;
    private String name;
    private String nickName;
    private Integer sex;
    private String phone;
    private String email;
    private String password;
    private Collection<GrantedAuthority> authorities;

    public mallUserDetails(UserPO user, Collection<GrantedAuthority> authorities) {
        this.id = user.getId();
        this.authorities = authorities;
        this.name = user.getName();
        this.password = user.getPassword();
        this.sex = user.getSex();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.nickName = user.getNickName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.name;
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
