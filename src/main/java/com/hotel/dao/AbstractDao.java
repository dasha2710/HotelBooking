package com.hotel.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by Dasha on 14.04.2015.
 */
@Repository
public abstract class AbstractDao<T> {
    @Autowired
    protected SessionFactory sessionFactory;

    public void save(T object) {
        sessionFactory.getCurrentSession().saveOrUpdate(object);
    }

    public void delete(T object) {
        sessionFactory.getCurrentSession().delete(object);
    }

    public T findById(Class<T> clazz, Integer id) {
        return (T) sessionFactory.getCurrentSession().get(clazz, id);
    }

    public List<T> findAll(Class<T> clazz) {
        return sessionFactory.getCurrentSession().createCriteria(clazz).list();
    }
}
