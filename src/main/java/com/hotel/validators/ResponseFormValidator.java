package com.hotel.validators;

import com.hotel.domain.Response;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Admin on 23.05.2015.
 */
public class ResponseFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Response.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "response.text.empty");
    }
}
