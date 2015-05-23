package com.hotel.domain;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Dasha on 25.04.2015.
 */
public enum Role implements GrantedAuthority {
    CLIENT, ADMIN;

    @Override
    public String getAuthority() {
        return toString();
    }
}
