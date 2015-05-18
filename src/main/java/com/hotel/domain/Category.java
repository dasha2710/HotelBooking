/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.domain;


import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author Dasha
 */
@Entity
@Table(name = "categories")
@XmlRootElement
public class Category implements Serializable, JSONAware {
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
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacity")
    private int capacity;
    @Size(max = 1000)
    @Column(name = "description")
    private String description;
    @Size(max = 5000)
    @Column(name = "details")
    private String details;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private int price;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
    private Collection<Room> roomsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.EAGER)
    private List<Picture> picturesCollection;
    @Transient
    private Picture mainPicture;

    public Category() {}

    public Category(String type, int capacity, int price) {
        this.type = type;
        this.capacity = capacity;
        this.price = price;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDetails() { return details; }

    public void setDetails(String details) { this.details = details; }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @XmlTransient
    public Collection<Room> getRoomsCollection() {
        return roomsCollection;
    }

    public void setRoomsCollection(Collection<Room> roomsCollection) {
        this.roomsCollection = roomsCollection;
    }

    @XmlTransient
    public List<Picture> getPicturesCollection() {
        return picturesCollection;
    }

    public void setPicturesCollection(List<Picture> picturesCollection) {
        this.picturesCollection = picturesCollection;
    }

    public Picture getMainPicture() {
        if (mainPicture != null ) {
            return mainPicture;
        }
        try {
            Picture mainPicture = Iterables.find(picturesCollection, new Predicate<Picture>() {
                @Override
                public boolean apply(Picture picture) {
                    return picture.isCentral();
                }
            });
            this.mainPicture = mainPicture;
            return mainPicture;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public void setMainPicture(Picture mainPicture) {
        this.mainPicture = mainPicture;
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
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.hotel.domain.Categories[ id=" + id + " ]";
    }

    public String toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("type", type);
        obj.put("capacity", capacity);
        obj.put("description", description);
        obj.put("price", price);
        obj.put("mainPicture", mainPicture);
        return obj.toString();
    }
}
