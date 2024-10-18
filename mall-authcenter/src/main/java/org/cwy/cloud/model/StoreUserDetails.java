package org.cwy.cloud.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author yao
 * @version 1.0
 * @description: TODO
 * @date 2024/9/23 14:51
 */
@Data
public class StoreUserDetails implements UserDetails {

    private Long id;
    private String userId;
    private String password;
    private String userName;
    private Integer storeType;
    private Integer storeLv;
    private Collection<GrantedAuthority> authorities;

    public StoreUserDetails(StoreUserVo user, Collection<GrantedAuthority> authorities) {
        this.id = user.getId();
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.storeType = user.getStoreType();
        this.storeLv = user.getStoreLv();
        this.userName = user.getUserName();
        this.authorities = authorities;
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
        return this.userId;
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
