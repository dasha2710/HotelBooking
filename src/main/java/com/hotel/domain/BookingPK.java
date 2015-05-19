/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Dasha
 */
@Embeddable
public class BookingPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "day_date")
    @Temporal(TemporalType.DATE)
    private Date dayDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "room_id")
    private int roomId;

    public BookingPK() {
    }

    public BookingPK(Date dayDate, int roomId) {
        this.dayDate = dayDate;
        this.roomId = roomId;
    }

    public Date getdayDate() {
        return dayDate;
    }

    public void setdayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dayDate != null ? dayDate.hashCode() : 0);
        hash += (int) roomId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookingPK)) {
            return false;
        }
        BookingPK other = (BookingPK) object;
        if ((this.dayDate == null && other.dayDate != null) || (this.dayDate != null && !this.dayDate.equals(other.dayDate))) {
            return false;
        }
        if (this.roomId != other.roomId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hotel.domain.BookingPK[ dayDate=" + dayDate + ", roomId=" + roomId + " ]";
    }

}