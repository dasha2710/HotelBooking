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
import java.util.Date;

/**
 *
 * @author Dasha
 */
@Entity
@Table(name = "users")
@XmlRootElement
public class User implements Serializable, JSONAware {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "password")
    private String password;
    @Transient
    private String matchingPassword;
    @Column(name = "date_registered")
    @Temporal(TemporalType.DATE)
    private Date dateRegistered;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Response> responsesCollection;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column
    private Role role;
    @OneToOne(optional = false, mappedBy = "user")
    private Client client;

    public User(){}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    @XmlTransient
    public Collection<Response> getResponsesCollection() {
        return responsesCollection;
    }

    public void setResponsesCollection(Collection<Response> responsesCollection) {
        this.responsesCollection = responsesCollection;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client clientId) {
        this.client = clientId;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.hotel.domain.Users[ id=" + id + " ]";
    }

    public String toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("id", id);
        obj.put("login", login);
        obj.put("password", password);
        obj.put("role", role.toString());
        obj.put("dateRegistered", dateRegistered.toString());
        return obj.toJSONString();
    }
}
