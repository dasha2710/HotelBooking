package com.hotel.dao;

import com.hotel.domain.Client;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Dasha on 18.04.2015.
 */
public class ClientDao extends AbstractDao<Client> {

    public List<Client> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(Client.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    public Client findByNameAndSurname(String surnameAndName) {
        String[] strs = surnameAndName.split(" ");
        Session session = sessionFactory.getCurrentSession();
        return (Client) session.createCriteria(Client.class)
                .add(Restrictions.eq("surname", strs[0]))
                .add(Restrictions.eq("name", strs[1]))
                .list().get(0);
    }


}
