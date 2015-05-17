package com.hotel.service;

import com.hotel.dao.CategoryDao;
import com.hotel.dao.OrderDao;
import com.hotel.dao.RoomDao;
import com.hotel.dao.StatusDao;
import com.hotel.domain.Category;
import com.hotel.domain.Order;
import com.hotel.domain.Room;
import com.hotel.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
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

    @Transactional
    public List<Category> findByDates(Date startDate, Date endDate) {
        List<Category> categories = categoryDao.findByDates(startDate, endDate);
        for (Category category : categories) {
            category.getMainPicture();
        }
        return categories;
    }

    @Transactional
    public boolean makeOrder(Integer categoryId, Date startDate, Date endDate) {
        Category category = categoryDao.findById(Category.class, categoryId);

        List<Room> rooms = roomDao.getFreeForCategoryAndDates(category, startDate, endDate);
        Date now = new Date(System.currentTimeMillis());

        if (!rooms.isEmpty()) {

            Order order = new Order();
            order.setDateCheckIn(startDate);
            order.setDateCheckOut(endDate);

            order.setDateCreated(now);
            order.setDateLastModified(now);

            order.setRoom(rooms.get(0));
            order.setStatus(statusDao.findByType(Status.BOOKED_TYPE));

            order.setTotalPrice();

            order.setClient(userService.getCurrentUser().getClient());
            orderDao.save(order);
            return true;
        }

        return false;
    }
}
