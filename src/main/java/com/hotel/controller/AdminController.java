package com.hotel.controller;

import com.hotel.domain.Category;
import com.hotel.domain.Order;
import com.hotel.domain.Status;
import com.hotel.domain.User;
import com.hotel.service.AdminService;
import com.hotel.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Dasha on 21.05.2015.
 */
@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private MessageSource messageSource;

    private Logger logger = Logger.getLogger("AdminControllerLogger");

    @RequestMapping(value = "/admin/orders", method = RequestMethod.GET)
    public String redirectToPersonalOffice(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        List<Status> statuses = adminService.findAllStatuses();
        model.addAttribute("statuses", statuses);
        return "admin/orders";
    }

    @RequestMapping(value = "/admin/orders", method = RequestMethod.POST)
    public
    @ResponseBody
    String filter(@RequestParam(value = "date_check_in")
                  @DateTimeFormat(pattern = "MM/dd/yyyy") java.util.Date date1,
                  @RequestParam(value = "date_check_out")
                  @DateTimeFormat(pattern = "MM/dd/yyyy") java.util.Date date2,
                  @RequestParam(value = "statuses[]", required = false) List<Integer> statuses) {

        JSONObject response = new JSONObject();
        response.put("correct", true);

        if ((date1 == null || date1.equals("")) && (date2 == null || date2.equals(""))
                && (statuses == null || statuses.isEmpty())) {
            response.put("orders", new ArrayList<>());
            return response.toString();
        }

        Date dateCheckIn = null;
        Date dateCheckOut = null;

        if (!(date1 == null || date1.equals("")) || !(date2 == null || date2.equals(""))) {
            if (date1 == null || date1.equals("")) {
                dateCheckIn = null;
                dateCheckOut = new Date(date2.getTime());
            } else if (date2 == null || date2.equals("")) {
                dateCheckIn = new Date(date1.getTime());
                dateCheckOut = null;
            } else {
                dateCheckIn = new Date(date1.getTime());
                dateCheckOut = new Date(date2.getTime());

                if (dateCheckIn.compareTo(dateCheckOut) > 0) {
                    response.put("correct", false);
                    response.put("error", messageSource.getMessage("start.date.greater", null, Locale.getDefault()));
                    return response.toString();
                }

                if (dateCheckIn.compareTo(dateCheckOut) == 0) {
                    response.put("correct", false);
                    response.put("error", messageSource.getMessage("start.end.dates.equals", null, Locale.getDefault()));
                    return response.toString();
                }
            }
        }

        List<Order> appropriateOrders = adminService.findByDatesAndStatuses(dateCheckIn, dateCheckOut, statuses);
        response.put("orders", appropriateOrders);
        return response.toString();
    }
}
