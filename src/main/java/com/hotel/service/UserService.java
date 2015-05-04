package com.hotel.service;

import com.hotel.dao.ClientDao;
import com.hotel.dao.UserDao;
import com.hotel.domain.Client;
import com.hotel.domain.Role;
import com.hotel.domain.User;
import com.hotel.util.EmailSender;
import com.hotel.util.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Dasha on 14.04.2015.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private ShaPasswordEncoder passwordEncoder;

    @Transactional
    public Client saveClient(Client client) {
        User user = client.getUser();
        user.setRole(Role.CLIENT);
        user.setPassword(passwordEncoder.encodePassword(user.getPassword(), user.getLogin()));
        user.setDateRegistered(new Date());
        userDao.save(user);
        clientDao.save(client);
        return client;
    }

    @Transactional
    public boolean updatePassword(String login) {
        User user = userDao.getUser(login);
        String newPass = PasswordGenerator.generate();
        if (EmailSender.send(user.getClient().getEmail(), "New password is " + newPass)) {
            user.setPassword(passwordEncoder.encodePassword(newPass, user.getLogin()));
            return true;
        } else {
            return false;
        }
    }

}
