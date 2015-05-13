package com.hotel.service;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.hotel.dao.CategoryDao;
import com.hotel.domain.Category;
import com.hotel.domain.Picture;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Admin on 19.04.2015.
 */
@Service
public class ApartmentService {

    @Autowired
    private CategoryDao categoryDao;

    @Transactional
    public List<Category> getAllApartments() {
        List<Category> categories = categoryDao.findAll();
        for (Category category: categories) {
            category.setMainPicture();
        }
        return categories;
    }


}
