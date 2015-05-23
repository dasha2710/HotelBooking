package com.hotel.service;

import com.hotel.dao.OrderDao;
import com.hotel.dao.StatusDao;
import com.hotel.domain.Order;
import com.hotel.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 21.05.2015.
 */
@Service
public class AdminService {

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private OrderDao orderDao;

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
}
