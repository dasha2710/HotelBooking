package com.hotel.dao;

import com.hotel.domain.Client;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * Created by Dasha on 18.04.2015.
 */
public class ClientDao extends AbstractDao<Client> {

    public Client findByNameAndSurname(String surnameAndName) {
        String[] strs = surnameAndName.split(" ");
        Session session = sessionFactory.getCurrentSession();
        return (Client) session.createCriteria(Client.class)
                .add(Restrictions.eq("surname", strs[0]))
                .add(Restrictions.eq("name", strs[1]))
                .list().get(0);
    }


}
