package com.hotel.controller;

import com.hotel.dao.ClientDao;
import com.hotel.domain.Client;
import com.hotel.domain.Order;
import com.hotel.domain.Status;
import com.hotel.domain.User;
import com.hotel.service.AdminService;
import com.hotel.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.hotel.service.BookingService;
import com.hotel.validators.RegisterFormValidator;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static java.util.Collections.sort;

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
    private AdminService adminService;
    @Autowired
    private RegisterFormValidator registerFormValidator;
    @Autowired
    private ClientDao clientDao;
    @Autowired
    private MessageSource messageSource;

    private Logger logger = Logger.getLogger("AdminControllerLogger");

    @InitBinder("client")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(registerFormValidator);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(
                dateFormat, false));
    }

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

    @RequestMapping(value = "/admin/add_order", method = RequestMethod.GET)
    public ModelAndView getAvailableRooms() {
        ModelAndView modelAndView = new ModelAndView("booking");
        List<Client> clients = clientDao.findAll();
        sort(clients);
        modelAndView.addObject("clients", clients);
        return modelAndView;
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

    @RequestMapping(value = "/admin/result", method = RequestMethod.POST)
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
    @RequestMapping(value = "/admin/orders/{orderId}", method = RequestMethod.GET)
    public String viewOrder(@PathVariable String orderId, Model model) {
        Order order = adminService.getOrderById(orderId);
        model.addAttribute("order", order);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        model.addAttribute("curDate", new java.util.Date(dateFormat.format(new java.util.Date())));
        model.addAttribute("result_message", "");
        return "admin/order";
    }

    @RequestMapping(value = "/admin/orders/{orderId}/check_in")
    public String checkInClient(@PathVariable String orderId, Model model) {
        Order order = adminService.changeTypeForOrder(orderId, Status.SETTLED_TYPE);
        model.addAttribute("order", order);
        model.addAttribute("result_message", messageSource.getMessage("success.checkIn",
                null, Locale.getDefault()));
        return "admin/order";
    }

    @RequestMapping(value = "/admin/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable String orderId, Model model) {
        Order order = bookingService.cancelOrder(new Integer(orderId));
        model.addAttribute("order", order);
        model.addAttribute("result_message", messageSource.getMessage("success.cancel.order",
                null, Locale.getDefault()));
        return "admin/order";
    }

    @RequestMapping(value = "/admin/orders/{orderId}/check_out")
    public String checkOutClient(@PathVariable String orderId, Model model) {
        Order order = adminService.checkOutClient(new Integer(orderId));
        model.addAttribute("order", order);
        model.addAttribute("result_message", messageSource.getMessage("success.checkOut",
                null, Locale.getDefault()));
        return "admin/order";
    }
}
