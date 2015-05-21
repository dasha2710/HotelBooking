package com.hotel.controller;

import com.hotel.domain.User;
import com.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Dasha on 21.05.2015.
 */
@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @RequestMapping("/admin/orders")
    public String redirectToPersonalOffice(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "admin/orders_view";
    }
}
