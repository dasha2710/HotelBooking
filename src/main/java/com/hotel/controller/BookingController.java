package com.hotel.controller;

import com.hotel.domain.Category;
import com.hotel.service.BookingService;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.List;

/**
 * Created by Admin on 27.04.2015.
 */
@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @RequestMapping(value = "/booking", method = RequestMethod.GET)
    public ModelAndView getAvailableRooms() {
        return new ModelAndView("booking");
    }

    @RequestMapping(value = "/booking", method = RequestMethod.POST)
    public @ResponseBody String filter(@RequestParam(value = "date_check_in", required = true) String date1,
                                       @RequestParam(value = "date_check_out", required = true) String date2) {
        Date dateCheckIn = Date.valueOf(date1);
        Date dateCheckOut = Date.valueOf(date2);

        List<Category> availableCategories = bookingService.findByDates(dateCheckIn, dateCheckOut);

        JSONArray arr = new JSONArray();
        arr.addAll(availableCategories);
        return arr.toString();
    }
}
