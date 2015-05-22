package com.hotel.service;

import com.hotel.dao.*;
import com.hotel.domain.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Admin on 30.04.2015.
 */
@Service
public class BookingService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private BookingDao bookingDao;

    @Transactional
    public List<Category> findByDates(Date startDate, Date endDate) {
        List<Category> categories = categoryDao.findByDates(startDate, endDate);
        for (Category category : categories) {
            category.getMainPicture();
        }
        return categories;
    }

    private Date getNext(Date current) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return new Date(calendar.getTimeInMillis());
    }

    @Transactional
    public boolean makeOrder(Integer categoryId, Date startDate, Date endDate) {
        return createAndSaveOrder(categoryId, startDate, endDate, userService.getCurrentUser().getClient());
    }

    @Transactional
    public boolean makeOrder(Integer categoryId, Date startDate, Date endDate, String clientName) {
        Client client = clientDao.findByNameAndSurname(clientName);
        return createAndSaveOrder(categoryId, startDate, endDate, client);
    }

    private boolean createAndSaveOrder(Integer categoryId, Date startDate, Date endDate, Client client) {
        Category category = categoryDao.findById(Category.class, categoryId);

        List<Room> rooms = roomDao.getFreeForCategoryAndDates(category, startDate, endDate);
        Date now = new Date(System.currentTimeMillis());

        boolean roomBooked = false;
        while (!roomBooked) {
            if (!rooms.isEmpty()) {
                Room room = rooms.get(0);
                List<Booking> bookings = new ArrayList<>();
                try {
                    for (Date d = startDate; d.compareTo(endDate) < 0; d = getNext(d)) {
                        Booking booking = new Booking(new BookingPK(d, room.getId()));
                        bookingDao.save(booking);
                        bookings.add(booking);
                        roomBooked = true;
                    }
                } catch (ConstraintViolationException ex) {
                    continue;
                }

                Order order = new Order();
                order.setDateCheckIn(startDate);
                order.setDateCheckOut(endDate);

                order.setDateCreated(now);
                order.setDateLastModified(now);

                order.setRoom(room);
                order.setStatus(statusDao.findByType(Status.BOOKED_TYPE));

                order.setTotalPrice();

                order.setClient(client);
                orderDao.save(order);
                for (Booking booking: bookings) {
                    booking.setOrder(order);
                    bookingDao.save(booking);
                }

                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Transactional
    public Order cancelOrder(int orderId) {
        Order order = orderDao.findById(Order.class, orderId);
        order.setStatus(statusDao.findByType(Status.CANCELLED_TYPE));
        List<Booking> bookings = order.getBookingList();
        for (Booking booking: bookings) {
            bookingDao.delete(booking);
        }
        order.setBookingList(null);
        orderDao.save(order);
        return order;
    }
}
