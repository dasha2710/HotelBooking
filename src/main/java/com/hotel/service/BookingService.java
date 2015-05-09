package com.hotel.service;

import com.hotel.dao.CategoryDao;
import com.hotel.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * Created by Admin on 30.04.2015.
 */
@Service
public class BookingService {

    @Autowired
    private CategoryDao categoryDao;

    public List<Category> findByDates(Date startDate, Date endDate) {
        List<Category> categories = categoryDao.findByDates(startDate, endDate);
        for (Category category : categories) {
            category.setMainPicture();
        }
        return categories;
    }
}
