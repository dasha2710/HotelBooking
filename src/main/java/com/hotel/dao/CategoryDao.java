package com.hotel.dao;

import com.hotel.domain.Category;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Admin on 19.04.2015.
 */
@Repository
public class CategoryDao extends AbstractDao<Category>{

    @Transactional
    public List<Category> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Category.class).list();
    }
}
