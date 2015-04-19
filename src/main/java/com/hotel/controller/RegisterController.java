package com.hotel.controller;

import com.hotel.domain.Client;
import com.hotel.domain.User;
import com.hotel.service.UserService;
import com.hotel.validators.RegisterFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dasha on 12.04.2015.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private RegisterFormValidator registerFormValidator;

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(registerFormValidator);
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
    }

    @RequestMapping(method = RequestMethod.GET)
    public String initForm(Model model) {
        User user = new User();
        user.setClient(new Client());
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String savingUser(Model model, @Validated User user, BindingResult result) {
        if (!result.hasErrors()) {
            User savedUser = userService.saveClient(user);
            model.addAttribute("user", savedUser);
            return "show";
        }
        return "register";
    }



}