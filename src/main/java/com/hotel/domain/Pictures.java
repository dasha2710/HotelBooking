/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hotel.domain;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dasha
 */
@Entity
@Table(name = "pictures")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pictures.findAll", query = "SELECT p FROM Pictures p"),
    @NamedQuery(name = "Pictures.findById", query = "SELECT p FROM Pictures p WHERE p.id = :id"),
    @NamedQuery(name = "Pictures.findByPath", query = "SELECT p FROM Pictures p WHERE p.path = :path"),
    @NamedQuery(name = "Pictures.findByIsCentral", query = "SELECT p FROM Pictures p WHERE p.isCentral = :isCentral")})
public class Pictures implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "path")
    private String path;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_central")
    private int isCentral;
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Categories categoryId;

    public Pictures() {
    }

    public Pictures(Integer id) {
        this.id = id;
    }

    public Pictures(Integer id, String path, int isCentral) {
        this.id = id;
        this.path = path;
        this.isCentral = isCentral;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getIsCentral() {
        return isCentral;
    }

    public void setIsCentral(int isCentral) {
        this.isCentral = isCentral;
    }

    public Categories getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Categories categoryId) {
        this.categoryId = categoryId;
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
        if (!(object instanceof Pictures)) {
            return false;
        }
        Pictures other = (Pictures) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.hotel.domain.Pictures[ id=" + id + " ]";
    }
    
}
