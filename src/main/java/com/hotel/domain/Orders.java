/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dasha
 */
@Entity
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id"),
    @NamedQuery(name = "Orders.findByDateCheckIn", query = "SELECT o FROM Orders o WHERE o.dateCheckIn = :dateCheckIn"),
    @NamedQuery(name = "Orders.findByDateCheckOut", query = "SELECT o FROM Orders o WHERE o.dateCheckOut = :dateCheckOut"),
    @NamedQuery(name = "Orders.findByDateCreated", query = "SELECT o FROM Orders o WHERE o.dateCreated = :dateCreated"),
    @NamedQuery(name = "Orders.findByDateLastModified", query = "SELECT o FROM Orders o WHERE o.dateLastModified = :dateLastModified")})
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_check_in")
    @Temporal(TemporalType.DATE)
    private Date dateCheckIn;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_check_out")
    @Temporal(TemporalType.DATE)
    private Date dateCheckOut;
    @Column(name = "date_created")
    @Temporal(TemporalType.DATE)
    private Date dateCreated;
    @Column(name = "date_last_modified")
    @Temporal(TemporalType.DATE)
    private Date dateLastModified;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Statuses statusId;
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rooms roomId;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Clients clientId;

    public Orders() {
    }

    public Orders(Integer id) {
        this.id = id;
    }

    public Orders(Integer id, Date dateCheckIn, Date dateCheckOut) {
        this.id = id;
        this.dateCheckIn = dateCheckIn;
        this.dateCheckOut = dateCheckOut;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(Date dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
    }

    public Date getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(Date dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(Date dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    public Statuses getStatusId() {
        return statusId;
    }

    public void setStatusId(Statuses statusId) {
        this.statusId = statusId;
    }

    public Rooms getRoomId() {
        return roomId;
    }

    public void setRoomId(Rooms roomId) {
        this.roomId = roomId;
    }

    public Clients getClientId() {
        return clientId;
    }

    public void setClientId(Clients clientId) {
        this.clientId = clientId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hotel.domain.Orders[ id=" + id + " ]";
    }
    
}
