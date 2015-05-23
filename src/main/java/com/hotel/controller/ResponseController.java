package com.hotel.controller;

import com.hotel.domain.Response;
import com.hotel.service.ResponseService;
import com.hotel.validators.ResponseFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by Admin on 23.05.2015.
 */
@Controller
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    @Autowired
    private ResponseFormValidator responseFormValidator;

    @RequestMapping(value = "/comments", method = RequestMethod.GET)
    public ModelAndView getAllResponses() {
        ModelAndView modelAndView = new ModelAndView("responses");
        modelAndView.addObject("responses", responseService.getAllResponses());
        modelAndView.addObject("response", new Response());
        return modelAndView;
    }

    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public ModelAndView addResponse(@ModelAttribute("response") Response response, BindingResult result) {
        responseFormValidator.validate(response, result);

        ModelAndView modelAndView = new ModelAndView("responses");

        if (result.hasErrors()) {
            modelAndView.addObject("response", response);
        } else {
            responseService.saveResponse(response);
            modelAndView.addObject("response", new Response());
        }

        modelAndView.addObject("responses", responseService.getAllResponses());
        return modelAndView;
    }
}
