package com.hotel.service;

import com.hotel.dao.ResponseDao;
import com.hotel.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

/**
 * Created by Admin on 23.05.2015.
 */
@Service
public class ResponseService {

    @Autowired
    private ResponseDao responseDao;

    @Autowired
    private UserService userService;

    @Transactional
    public List<Response> getAllResponses() {
        return responseDao.findAll(Response.class);
    }

    @Transactional
    public void saveResponse(Response response) {
        response.setDateCreated(new Date(System.currentTimeMillis()));
        response.setUser(userService.getCurrentUser());
        responseDao.save(response);
    }
}
