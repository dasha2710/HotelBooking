package com.hotel.dao;

import com.hotel.domain.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daryna_Ragimova on 4/16/2015.
 */
public class UserDao extends AbstractDao{

    @Transactional
    public User getUser(final String login, final String pass) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        Map<String, String> argsMap = Collections.unmodifiableMap(new HashMap<String, String>() {{
            put("login", login);
            put("pass", pass);
        }});
        criteria.add(Restrictions.allEq(argsMap));
        return (User) criteria.uniqueResult();
    }

    @Transactional
    public boolean checkUserNotExists(final String login) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(User.class);
        criteria.add(Restrictions.eq("login", login));
        return criteria.uniqueResult() == null;
    }
}
