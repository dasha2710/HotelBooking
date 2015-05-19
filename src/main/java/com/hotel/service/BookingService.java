package com.hotel.service;

import com.hotel.dao.*;
import com.hotel.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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
        Category category = categoryDao.findById(Category.class, categoryId);

        List<Room> rooms = roomDao.getFreeForCategoryAndDates(category, startDate, endDate);
        Date now = new Date(System.currentTimeMillis());

        if (!rooms.isEmpty()) {
            Room room = rooms.get(0);

            Order order = new Order();
            order.setDateCheckIn(startDate);
            order.setDateCheckOut(endDate);

            order.setDateCreated(now);
            order.setDateLastModified(now);

            order.setRoom(room);
            order.setStatus(statusDao.findByType(Status.BOOKED_TYPE));

            order.setTotalPrice();

            order.setClient(userService.getCurrentUser().getClient());
            orderDao.save(order);

            for (Date d = startDate; d.compareTo(endDate) < 0; d = getNext(d)) {
                Booking booking = new Booking();
                booking.setBookingPK(new BookingPK(d, room.getId()));

                bookingDao.save(booking);
            }
            return true;
        }

        return false;
    }
}
