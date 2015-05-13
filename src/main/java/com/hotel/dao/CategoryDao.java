package com.hotel.dao;

import com.hotel.domain.Category;
import com.hotel.domain.Room;
import com.hotel.domain.Order;
import com.hotel.domain.Status;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * Created by Admin on 19.04.2015.
 */
@Repository
public class CategoryDao extends AbstractDao<Category>{

    public List<Category> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Category.class).list();
    }

    @Transactional
    public List<Category> findByDates(Date dateCheckIn, Date dateCheckOut) {
        DetachedCriteria ordersCriteria = DetachedCriteria.forClass(Order.class, "o").createAlias("o.status", "s");
        Criterion startDateMiddle = Restrictions.and(
                Restrictions.ge("o.dateCheckIn", dateCheckIn), Restrictions.le("o.dateCheckIn", dateCheckOut));
        Criterion endDateMiddle = Restrictions.and(
                Restrictions.ge("o.dateCheckOut", dateCheckIn), Restrictions.le("o.dateCheckOut", dateCheckOut));
        Criterion innerDates = Restrictions.and(
                Restrictions.le("o.dateCheckIn", dateCheckIn), Restrictions.ge("o.dateCheckOut", dateCheckOut));
        Criterion outerDates = Restrictions.and(
                Restrictions.ge("o.dateCheckIn", dateCheckIn), Restrictions.le("o.dateCheckOut", dateCheckOut));
        ordersCriteria.add(Restrictions.and(Restrictions.or(Restrictions.or(Restrictions.or(startDateMiddle, endDateMiddle),
                        innerDates), outerDates), Restrictions.ne("s.type", Status.CANCELLED_TYPE)));
        ordersCriteria.setProjection(Projections.property("o.room.id"));

        DetachedCriteria roomsCriteria = DetachedCriteria.forClass(Room.class, "r").
                add(Restrictions.and(Restrictions.eqProperty("r.category.id", "c.id"),
                        Subqueries.propertyNotIn("r.id", ordersCriteria)));

        Criteria mainCriteria = sessionFactory.getCurrentSession().createCriteria(Category.class, "c");
        mainCriteria.add(Subqueries.exists(roomsCriteria.setProjection(Projections.property("r.id"))));

        List<Category> categories = mainCriteria.list();
        return categories;
    }
}
