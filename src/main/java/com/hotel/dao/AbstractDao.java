package com.hotel.dao;

import com.hotel.domain.Admin;

/**
 * Created by Dasha on 14.04.2015.
 */
public interface AbstractDao<T> {
    public void save(T object);
}
