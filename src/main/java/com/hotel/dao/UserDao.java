package com.hotel.dao;

import com.hotel.domain.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Daryna_Ragimova on 4/16/2015.
 */
public class UserDao extends AbstractDao{

    @Transactional
    public User getUser(final String login) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("login", login));
        return (User) criteria.uniqueResult();
    }

    @Transactional
    public boolean checkUserNotExists(final String login) {
        return getUser(login) == null;
    }
}
