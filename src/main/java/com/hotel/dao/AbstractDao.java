package com.hotel.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Dasha on 14.04.2015.
 */
@Repository
public abstract class AbstractDao<T> {
    @Autowired
    protected SessionFactory sessionFactory;

    @Transactional
    public void save(T object) {
        sessionFactory.getCurrentSession().saveOrUpdate(object);
    }

    @Transactional
    public void delete(T object) {
        sessionFactory.getCurrentSession().delete(object);
    }
}
