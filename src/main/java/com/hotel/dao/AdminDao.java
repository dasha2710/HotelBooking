package com.hotel.dao;

import com.hotel.domain.Admin;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dasha on 14.04.2015.
 */
@Repository
public class AdminDao implements AbstractDao<Admin> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void save(Admin admin) {
        sessionFactory.getCurrentSession().saveOrUpdate(admin);
    }
}
