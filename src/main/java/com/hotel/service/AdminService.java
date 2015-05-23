package com.hotel.service;

import com.hotel.dao.BookingDao;
import com.hotel.dao.OrderDao;
import com.hotel.dao.StatusDao;
import com.hotel.domain.Booking;
import com.hotel.domain.Order;
import com.hotel.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Dasha on 23.05.2015.
 */
@Service
public class AdminService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private BookingDao bookingDao;
    @Autowired
    private StatusDao statusDao;

    public Order getOrderById(String id) {
        return orderDao.findById(Order.class, new Integer(id));
    }

    public Order changeTypeForOrder(String id, String status) {
        Order order = orderDao.findById(Order.class, new Integer(id));
        order.setStatus(statusDao.findByType(status));
        orderDao.save(order);
        return order;
    }

    @Transactional
    public Order checkOutClient(Integer orderId) {
        Order order = orderDao.findById(Order.class, new Integer(orderId));
        Status status = statusDao.findByType(Status.CLOSED_TYPE);
        order.setStatus(status);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date curDate = new Date(dateFormat.format(new Date()));
        if (curDate.before(order.getDateCheckOut())) {
            order.setDateCheckOut(curDate);
            List<Booking> bookings = order.getBookingList();
            List<Booking> bookingsResult = new ArrayList<>();
            for (Booking booking: bookings) {
                if (booking.getBookingPK().getdayDate().after(curDate)) {
                   bookingDao.delete(booking);
                } else {
                    bookingsResult.add(booking);
                }
            }
            order.setBookingList(bookingsResult);
            order.setTotalPrice();
        }
        orderDao.save(order);
        return order;
    }


}
