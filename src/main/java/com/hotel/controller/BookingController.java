package com.hotel.controller;

import com.hotel.domain.Category;
import com.hotel.service.BookingService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Admin on 27.04.2015.
 */
@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = "/booking", method = RequestMethod.GET)
    public ModelAndView getAvailableRooms() {
        return new ModelAndView("booking");
    }

    @RequestMapping(value = "/booking", method = RequestMethod.POST)
    public @ResponseBody String filter(@RequestParam(value = "date_check_in", required = true)
                                           @DateTimeFormat(pattern = "MM/dd/yyyy") java.util.Date date1,
                                       @RequestParam(value = "date_check_out", required = true)
                                       @DateTimeFormat(pattern = "MM/dd/yyyy") java.util.Date date2) {
        JSONObject response = new JSONObject();
        response.put("correct", false);
        if (date1 == null || date2 == null) {
            response.put("error", messageSource.getMessage("dates.null", null, Locale.getDefault()));
            return response.toString();
        }

        Date dateCheckIn = new Date(date1.getTime());
        Date dateCheckOut = new Date(date2.getTime());

        if (dateCheckIn.compareTo(dateCheckOut) > 0) {
            response.put("error", messageSource.getMessage("start.date.greater", null, Locale.getDefault()));
            return response.toString();
        }

        if (dateCheckIn.compareTo(dateCheckOut) == 0) {
            response.put("error", messageSource.getMessage("start.end.dates.equals", null, Locale.getDefault()));
            return response.toString();
        }

        Date now = new Date(System.currentTimeMillis());
        if (dateCheckIn.compareTo(now) <= 0) {
            response.put("error", messageSource.getMessage("book.past.date", null, Locale.getDefault()));
            return response.toString();
        }

        List<Category> availableCategories = bookingService.findByDates(dateCheckIn, dateCheckOut);
        response.put("correct", true);
        response.put("categories", availableCategories);
        return response.toString();
    }

    @RequestMapping(value = "/result", method = RequestMethod.POST)
    public ModelAndView makeBooking(@RequestParam(value = "date_check_in", required = true)
                                        @DateTimeFormat(pattern = "MM/dd/yyyy") java.util.Date date1,
                                    @RequestParam(value = "date_check_out", required = true)
                                        @DateTimeFormat(pattern = "MM/dd/yyyy") java.util.Date date2,
                                    @RequestParam(value = "category_id") Integer categoryId) {
        Date dateCheckIn = new Date(date1.getTime());
        Date dateCheckOut = new Date(date2.getTime());

        if (bookingService.makeOrder(categoryId, dateCheckIn, dateCheckOut)) {
            return new ModelAndView("result", "result_message", messageSource.getMessage("book.success", null, Locale.getDefault()));
        } else {
            return new ModelAndView("result", "result_message", messageSource.getMessage("book.fail.category.unavailable",
                    null, Locale.getDefault()));
        }
    }
}
