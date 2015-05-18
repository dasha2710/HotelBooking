package com.hotel.controller;

import com.hotel.domain.Category;
import com.hotel.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by Admin on 19.04.2015.
 */
@Controller
public class ApartmentsController {

    @Autowired
    private ApartmentService apartmentService;

    @RequestMapping(value = "/apartments")
    public String getAllApartments(Model model) {
        List<Category> apartments = apartmentService.getAllApartments();
        model.addAttribute("apartments", apartments);
        return "apartments";
    }

    @RequestMapping(value = "/apartments/{id}")
    public ModelAndView getApartmentDetails(@PathVariable Integer id) {
        Category category = apartmentService.getDetailsCategory(id);
        return new ModelAndView("details", "category", category);
    }

    @RequestMapping(value= "/index")
    public String getInfoAboutHotel() {
        return "index";
    }

    @RequestMapping(value= "/contacts")
    public String getContacts() {
        return "contacts";
    }
}
