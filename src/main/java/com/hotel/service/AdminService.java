package com.hotel.service;

import com.hotel.dao.*;
import com.hotel.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
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
    private RoomDao roomDao;

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
        order.setDateLastModified(new java.util.Date());
        orderDao.save(order);
        return order;
    }

    @Transactional
    public Order checkOutClient(String orderId) {
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
        order.setDateLastModified(new java.util.Date());
        orderDao.save(order);
        return order;
    }

    private Date getNext(Date current) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return new Date(calendar.getTimeInMillis());
    }

    @Transactional
    public Order prolongOrderIfPossible(String orderId, java.util.Date newCheckOutDate) {
        Order order = orderDao.findById(Order.class, new Integer(orderId));
        Date currentCheckOutDate = new Date(order.getDateCheckOut().getTime());
        Date newCheckOutDateSql = new Date(newCheckOutDate.getTime());
        Room room = order.getRoom();
        if (roomDao.checkRoomIsFreeForDates(room, currentCheckOutDate, newCheckOutDateSql)) {
            List<Booking> bookings = new ArrayList<>();
            for (Date d = currentCheckOutDate; d.compareTo(newCheckOutDateSql) < 0; d = getNext(d)) {
                Booking booking = new Booking(new BookingPK(d, room.getId()));
                booking.setOrder(order);
                bookingDao.save(booking);
                bookings.add(booking);
            }
            order.setBookingList(bookings);
            order.setDateCheckOut(newCheckOutDate);
            order.setTotalPrice();
            order.setDateLastModified(new java.util.Date());
            orderDao.save(order);
            return order;
        }
        return null;
    }
}