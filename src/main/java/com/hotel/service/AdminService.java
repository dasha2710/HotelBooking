package com.hotel.service;

import com.hotel.domain.Admin;
import org.springframework.stereotype.Service;

/**
 * Created by Dasha on 14.04.2015.
 */
@Service
public interface AdminService {
    public void save(Admin admin);
}
