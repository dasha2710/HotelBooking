package com.hotel.controller;

import com.hotel.domain.User;
import com.hotel.service.UserService;
import com.hotel.validators.ForgotPassLoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Dasha on 25.04.2015.
 */
@Controller
public class LoginController {

    @Autowired
    private ForgotPassLoginValidator validator;

    @Autowired
    private UserService service;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @RequestMapping("/login")
    public String initForm() {
        return "login";
    }

    @RequestMapping("/forgot_pass")
    public String forgotPass(Model model) {
        model.addAttribute("user", new User());
        return "forgot_pass";
    }

    @RequestMapping(value = "/forgot_pass", method = RequestMethod.POST)
    public String changePass(@Validated User user, BindingResult result) {
        if (!result.hasErrors()) {
            if (service.updatePassword(user.getLogin())) {
                result.rejectValue("login", "pass.sending.succeed");
            } else {
                result.rejectValue("login", "pass.sending.failed");
            }
        }
        return "forgot_pass";
    }
}
