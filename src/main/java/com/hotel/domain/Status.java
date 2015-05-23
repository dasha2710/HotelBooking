/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.domain;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Dasha
 */
@Entity
@Table(name = "statuses")
@XmlRootElement
public class Status implements Serializable, JSONAware {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "type")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
    private Collection<Order> ordersCollection;

    public final static String CANCELLED_TYPE = "CANCELLED";
    public final static String BOOKED_TYPE = "BOOKED";
    public final static String CLOSED_TYPE = "CLOSED";
    public final static String SETTLED_TYPE = "SETTLED";

    public Status() {}

    public Status(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlTransient
    public Collection<Order> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Order> ordersCollection) {
        this.ordersCollection = ordersCollection;
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
        if (!(object instanceof Status)) {
            return false;
        }
        Status other = (Status) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.hotel.domain.Status[ id=" + id + ", type=" + type+ " ]";
    }

    public String toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("type", type);
        return obj.toJSONString();
    }
}
