package org.cwy.cloud.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

@Data
public class UserAuto {
    Map authorities;
    userMsg userMsg;
}
