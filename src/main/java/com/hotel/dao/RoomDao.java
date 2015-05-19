package com.hotel.dao;

import com.hotel.domain.*;
import com.hotel.domain.Order;
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
        DetachedCriteria busyRoomsCriteria = DetachedCriteria.forClass(Booking.class, "b")
                .add(Restrictions.ge("b.bookingPK.dayDate", dateCheckIn))
                .add(Restrictions.lt("b.bookingPK.dayDate", dateCheckOut))
                .setProjection(Projections.property("b.bookingPK.roomId"));

        Criteria mainCriteria = sessionFactory.getCurrentSession().createCriteria(Room.class, "r").
                add(Restrictions.and(Restrictions.eq("r.category.id", category.getId()),
                        Subqueries.propertyNotIn("r.id", busyRoomsCriteria)));

        List<Room> rooms = mainCriteria.list();
        return rooms;
    }
}
