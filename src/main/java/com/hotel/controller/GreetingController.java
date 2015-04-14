package com.hotel.controller;

import com.hotel.domain.Admin;
import com.hotel.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Dasha on 12.04.2015.
 */
@Controller
public class GreetingController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping("/savingAdmin")
    public String savingAdmin(@RequestParam(value="login", required=false, defaultValue="login") String login,
                              @RequestParam(value="pass", required=false, defaultValue="pass") String pass,
                              Model model) {
        adminService.save(new Admin(login, pass));
        model.addAttribute("name", login);
        return "greeting";
    }

}