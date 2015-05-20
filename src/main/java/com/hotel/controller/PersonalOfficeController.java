package com.hotel.controller;

import com.hotel.domain.User;
import com.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Locale;

/**
 * Created by Dasha on 10.05.2015.
 */
@Controller
public class PersonalOfficeController {
    @Autowired
    private UserService service;
    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/client/office")
    public String redirectToPersonalOffice(Model model, Principal principal) {
        User user = service.getCurrentUser();
        model.addAttribute("user", user);
        return "client/personal_office";
    }

    @RequestMapping(value = "/update_pass", method = RequestMethod.POST)
    public ModelAndView changePass(@RequestParam(value = "oldPass", required = true) String oldPass,
                             @RequestParam(value = "newPass", required = true) String newPass) {
        User user = service.getCurrentUser();

        if (service.updatePassword(user, oldPass, newPass)) {
            return new ModelAndView("client/change_pass", "result_message",
                                    messageSource.getMessage("password.changed.successfully", null, Locale.getDefault()));
        } else {
            return new ModelAndView("client/change_pass", "result_message",
                                    messageSource.getMessage("incorrect.password", null, Locale.getDefault()));
        }
    }


}
