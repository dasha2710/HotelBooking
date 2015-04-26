package com.hotel.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Dasha on 25.04.2015.
 */
public enum Role implements GrantedAuthority {
    CLIENT,ADMIN;
    @Override
    public String getAuthority() {
        return toString();
    }
}
