package com.hotel.validators;

import com.hotel.dao.UserDao;
import com.hotel.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Dasha on 04.05.2015.
 */
public class ForgotPassLoginValidator implements Validator {
    @Autowired
    private UserDao userDao;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        String login = ((User) o).getLogin();
        if (userDao.checkUserNotExists(login)) {
            errors.rejectValue("login", "not.existed.login");
        }
    }
}
