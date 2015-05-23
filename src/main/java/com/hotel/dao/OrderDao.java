package com.hotel.dao;

import com.hotel.domain.Order;
import com.hotel.domain.Status;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * Created by Admin on 13.05.2015.
 */
public class OrderDao extends AbstractDao<Order> {

    public List<Order> findByDatesAndStatuses(Date startDate, Date endDate, List<Status> statuses) {
        Criteria ordersCriteria = sessionFactory.getCurrentSession().createCriteria(Order.class, "o");

        Criterion datesCriterion;
        if (startDate == null) {
            datesCriterion = Restrictions.le("o.dateCheckOut", endDate);
        } else if (endDate == null) {
            datesCriterion = Restrictions.ge("o.dateCheckIn", startDate);
        } else {
            datesCriterion = Restrictions.and(Restrictions.ge("o.dateCheckIn", startDate),
                    Restrictions.le("o.dateCheckOut", endDate));
        }

        ordersCriteria.add(Restrictions.and(datesCriterion, Restrictions.in("o.status", statuses)));
        return ordersCriteria.list();
    }

    public List<Order> findByStatuses(List<Status> statuses) {
        Criteria ordersCriteria = sessionFactory.getCurrentSession().createCriteria(Order.class, "o");
        ordersCriteria.add(Restrictions.in("o.status", statuses));
        return ordersCriteria.list();
    }
}
