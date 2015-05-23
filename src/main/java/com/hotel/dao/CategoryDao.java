package com.hotel.dao;

import com.hotel.domain.Booking;
import com.hotel.domain.Category;
import com.hotel.domain.Room;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
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
        return sessionFactory.getCurrentSession().createCriteria(Category.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    public List<Category> findByDates(Date dateCheckIn, Date dateCheckOut) {

        DetachedCriteria busyRoomsCriteria = DetachedCriteria.forClass(Booking.class, "b")
                .add(Restrictions.ge("b.bookingPK.dayDate", dateCheckIn))
                .add(Restrictions.lt("b.bookingPK.dayDate", dateCheckOut))
                .setProjection(Projections.property("b.bookingPK.roomId"));

        DetachedCriteria roomsCriteria = DetachedCriteria.forClass(Room.class, "r").
                add(Restrictions.and(Restrictions.eqProperty("r.category.id", "c.id"),
                        Subqueries.propertyNotIn("r.id", busyRoomsCriteria)));

        Criteria mainCriteria = sessionFactory.getCurrentSession().createCriteria(Category.class, "c")
                .add(Subqueries.exists(roomsCriteria.setProjection(Projections.property("r.id"))))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List<Category> categories = mainCriteria.list();
        return categories;
    }
}
