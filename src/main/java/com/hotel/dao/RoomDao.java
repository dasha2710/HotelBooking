package com.hotel.dao;

import com.hotel.domain.Category;
import com.hotel.domain.Order;
import com.hotel.domain.Room;
import com.hotel.domain.Status;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

/**
 * Created by Admin on 12.05.2015.
 */
@Repository
public class RoomDao extends AbstractDao<Room> {

    public List<Room> getFreeForCategoryAndDates(Category category, Date dateCheckIn, Date dateCheckOut) {
        DetachedCriteria ordersCriteria = DetachedCriteria.forClass(Order.class, "o").createAlias("o.status", "s");
        Criterion startDateMiddle = Restrictions.and(
                Restrictions.ge("o.dateCheckIn", dateCheckIn), Restrictions.le("o.dateCheckIn", dateCheckOut));
        Criterion endDateMiddle = Restrictions.and(
                Restrictions.ge("o.dateCheckOut", dateCheckIn), Restrictions.le("o.dateCheckOut", dateCheckOut));
        Criterion innerDates = Restrictions.and(
                Restrictions.le("o.dateCheckIn", dateCheckIn), Restrictions.ge("o.dateCheckOut", dateCheckOut));
        ordersCriteria.add(Restrictions.or(Restrictions.or(startDateMiddle, endDateMiddle), innerDates))
                .add(Restrictions.ne("s.type", Status.CANCELLED_TYPE));
        ordersCriteria.setProjection(Projections.property("o.room.id"));

        Criteria mainCriteria = sessionFactory.getCurrentSession().createCriteria(Room.class, "r").
                add(Restrictions.and(Restrictions.eq("r.category.id", category.getId()),
                        Subqueries.propertyNotIn("r.id", ordersCriteria)));

        List<Room> rooms = mainCriteria.list();
        return rooms;
    }
}
