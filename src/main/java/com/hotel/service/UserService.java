package com.hotel.service;

import com.hotel.dao.AbstractDao;
import com.hotel.dao.ClientDao;
import com.hotel.dao.UserDao;
import com.hotel.domain.Admin;
import com.hotel.domain.Client;
import com.hotel.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Dasha on 14.04.2015.
 */
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ClientDao clientDao;

    @Transactional
    public User saveClient(User user) {
        clientDao.save(user.getClient());
        user.setDateRegistered(new Date());
        userDao.save(user);
        return user;

    }


}