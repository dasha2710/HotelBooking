package com.hotel.validators;

import com.hotel.dao.UserDao;
import com.hotel.domain.Client;
import com.hotel.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Daryna_Ragimova on 4/17/2015.
 */
public class RegisterFormValidator implements Validator {

    private static final String PHONE_PATTERN = "^[0-9]{6,20}$";
    private static final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    @Autowired
    private UserDao userDao;

    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
    }

    public void validate(Object obj, Errors errors) {
        Client client = (Client) obj;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "empty.surname");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "empty.name");
        if(!client.getPhone().matches(PHONE_PATTERN)) {
            errors.rejectValue("phone","invalid.phone");
        }
        if(!client.getEmail().matches(EMAIL_PATTERN)) {
            errors.rejectValue("email","invalid.email");
        }
        if (client.getBirthday() != null && client.getBirthday().after(getDateYearsAgo(18))) {
            errors.rejectValue("birthday", "invalid.birthday");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passport","empty.passport");
        User user = client.getUser();
        if (user != null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.login", "empty.login");
            if (!userDao.checkUserNotExists(user.getLogin())) {
                errors.rejectValue("user.login", "existed.login");
            }
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user.password", "empty.password");
            if (user.getPassword().length() > 0 && user.getPassword().length() < 6) {
                errors.rejectValue("user.password", "invalid.password.short");
            }
            if (user.getMatchingPassword().isEmpty() || !user.getPassword().equals(user.getMatchingPassword())) {
                errors.rejectValue("user.matchingPassword", "invalid.passwordConfDiff");
            }
        }

    }

    private Date getDateYearsAgo(int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.DAY_OF_YEAR, -n);
        return new Date(calendar.getTimeInMillis());
    }
}
