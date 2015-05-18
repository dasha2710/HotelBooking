package com.hotel.controller;

import com.hotel.dao.UserDao;
import com.hotel.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Created by Dasha on 10.05.2015.
 */
@Controller
public class PersonalOfficeController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/office")
    public String redirectToPersonalOffice(Model model, Principal principal) {
        String userName = principal.getName();
        User user = userDao.getUser(userName);
        model.addAttribute("user", user);
        return "personal_office";
    }


}
