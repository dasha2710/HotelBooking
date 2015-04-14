package com.hotel.service;

import com.hotel.dao.AbstractDao;
import com.hotel.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Dasha on 14.04.2015.
 */
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AbstractDao abstractDao;
    @Override
    public void save(Admin admin) {
        abstractDao.save(admin);
    }
}
