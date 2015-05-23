package com.hotel.dao;

import com.hotel.domain.Status;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Admin on 13.05.2015.
 */
public class StatusDao extends AbstractDao<Status> {

    public Status findByType(String type) {
        return (Status) sessionFactory.getCurrentSession().createCriteria(Status.class, "s")
                .add(Restrictions.eq("s.type", type)).uniqueResult();
    }

}
