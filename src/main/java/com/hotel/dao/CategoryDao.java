package com.hotel.dao;

import com.hotel.domain.Category;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * Created by Admin on 19.04.2015.
 */
@Repository
public class CategoryDao extends AbstractDao<Category>{

    @Transactional
    public List<Category> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Category.class).list();
    }

    @Transactional
    public List<Category> findByDates(Date startDate, Date endDate) {
        String sqlQuery = "select *\n" +
                "from hotel.categories c\n" +
                "where exists\n" +
                "	(select *\n" +
                "	from hotel.rooms r\n" +
                "	where r.category_id=c.id and r.id not in\n" +
                "		(select o.room_id\n" +
                "		from hotel.orders o\n" +
                "		where (o.date_check_in >= ? and o.date_check_in <= ?) or\n" +
                "		(o.date_check_out >= ? and o.date_check_out <= ?) or\n" +
                "		(o.date_check_in <= ? and o.date_check_out >= ?)))";

        Query query = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery).addEntity(Category.class)
                .setDate(0, startDate).setDate(1, endDate).setDate(2, startDate).setDate(3, endDate)
                .setDate(4, startDate).setDate(5, endDate);

        List<Category> categories = query.list();
        return categories;
    }
}
