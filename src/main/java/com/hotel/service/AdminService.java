package com.hotel.service;

import com.hotel.dao.ClientDao;
import com.hotel.dao.OrderDao;
import com.hotel.dao.StatusDao;
import com.hotel.dao.BookingDao;
import com.hotel.domain.Booking;
import com.hotel.domain.Client;
import com.hotel.domain.Order;
import com.hotel.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

/**
 * Created by Admin on 21.05.2015.
 */
@Service
public class AdminService {

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ClientDao clientDao;

    @Transactional
    public List<Order> findByDatesAndStatuses(Date startDate, Date endDate, List<Integer> statusIds) {
        List<Status> statuses;
        if (statusIds != null && !statusIds.isEmpty()) {
            statuses = new ArrayList<>();
            for (Integer statusId : statusIds) {
                statuses.add(statusDao.findById(Status.class, statusId));
            }
        } else {
            statuses = statusDao.findAll(Status.class);
        }

        if (startDate != null || endDate != null) {
            return orderDao.findByDatesAndStatuses(startDate, endDate, statuses);
        } else {
            return orderDao.findByStatuses(statuses);
        }
    }

    @Transactional
    public List<Status> findAllStatuses() {
        return statusDao.findAll(Status.class);
    }

    @Transactional
    public List<Client> findAllClients() {
        return clientDao.findAll(Client.class);
    }

    @Transactional
    public Order getOrderById(String id) {
        return orderDao.findById(Order.class, new Integer(id));
    }

    @Transactional
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
        java.util.Date curDate = new java.util.Date(dateFormat.format(new java.util.Date()));
        if (curDate.before(order.getDateCheckOut())) {
            order.setDateCheckOut(curDate);
            List<Booking> bookings = order.getBookingList();
            List<Booking> bookingsResult = new ArrayList<>();
            for (Booking booking : bookings) {
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