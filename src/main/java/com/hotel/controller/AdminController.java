package com.hotel.controller;

import com.hotel.domain.Client;
import com.hotel.domain.User;
import com.hotel.service.BookingService;
import com.hotel.service.UserService;
import com.hotel.validators.RegisterFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Dasha on 21.05.2015.
 */
@Controller
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private RegisterFormValidator registerFormValidator;
    @Autowired
    private MessageSource messageSource;

    @InitBinder("client")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(registerFormValidator);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
                dateFormat, false));
    }

    @RequestMapping("/admin/orders")
    public String redirectToPersonalOffice(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("user", user);
        return "admin/orders_view";
    }

    @RequestMapping(value = "/admin/add_client", method = RequestMethod.GET)
    public String initForm(Model model) {
        Client client = new Client();
        client.setUser(null);
        model.addAttribute("client", client);
        return "admin/register";
    }

    @RequestMapping(value = "/admin/add_client", method = RequestMethod.POST)
    public ModelAndView savingUser(@Validated Client client, BindingResult result) {
        if (!result.hasErrors()) {
            userService.saveClient(client);
            return new ModelAndView("admin/result", "result_message", messageSource.getMessage("create.client.success.admin", null, Locale.getDefault()));
        }
        return new ModelAndView("/admin/add_client");
    }

    @RequestMapping(value = "/admin/add_order", method = RequestMethod.POST)
    public ModelAndView addOrder(@RequestParam(value = "client", required = true) String clientName,
                           @RequestParam(value = "date_check_in", required = true)
                           @DateTimeFormat(pattern = "MM/dd/yyyy") java.util.Date date1,
                           @RequestParam(value = "date_check_out", required = true)
                           @DateTimeFormat(pattern = "MM/dd/yyyy") java.util.Date date2,
                           @RequestParam(value = "category_id") Integer categoryId) {
        Date dateCheckIn = new Date(date1.getTime());
        Date dateCheckOut = new Date(date2.getTime());

        if (bookingService.makeOrder(categoryId, dateCheckIn, dateCheckOut, clientName)) {
            return new ModelAndView("admin/result", "result_message", messageSource.getMessage("book.success.admin", null, Locale.getDefault()));
        } else {
            return new ModelAndView("admin/result", "result_message", messageSource.getMessage("book.fail.category.unavailable",
                    null, Locale.getDefault()));
        }

    }
}
