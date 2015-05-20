/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Dasha
 */
@Entity
@Table(name = "booking")
@XmlRootElement
public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BookingPK bookingPK;
    @JoinColumn(name = "room_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Room room;
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Order order;

    public Booking() {
    }

    public Booking(BookingPK bookingPK) {
        this.bookingPK = bookingPK;
    }

    public Booking(Date startDate, int roomId) {
        this.bookingPK = new BookingPK(startDate, roomId);
    }

    public BookingPK getBookingPK() {
        return bookingPK;
    }

    public void setBookingPK(BookingPK bookingPK) {
        this.bookingPK = bookingPK;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingPK != null ? bookingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.bookingPK == null && other.bookingPK != null) || (this.bookingPK != null && !this.bookingPK.equals(other.bookingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hotel.domain.Booking[ bookingPK=" + bookingPK + " ]";
    }

}
