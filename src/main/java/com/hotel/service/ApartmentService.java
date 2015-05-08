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

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Admin on 19.04.2015.
 */
@Service
public class ApartmentService {

    @Autowired
    private CategoryDao categoryDao;

    Log log = LogFactory.getLog(ApartmentService.class);

    public List<Category> getAllApartments() {
        List<Category> categories = categoryDao.findAll();
        for (Category category: categories) {
            setMainPictureForCategory(category);
        }
        return categories;
    }

    private void setMainPictureForCategory(Category category) {
         try {
            Picture mainPicture = Iterables.find(category.getPicturesCollection(), new Predicate<Picture>() {
                @Override
                public boolean apply(Picture picture) {
                    return picture.isCentral();
                }
            });
            category.setMainPicture(mainPicture);
        } catch (NoSuchElementException e) {
            log.warn(String.format("The main image for category %s was not found", category.getType()));
        }

    }
}
