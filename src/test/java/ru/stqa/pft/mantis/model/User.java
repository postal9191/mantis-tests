package ru.stqa.pft.mantis.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "mantis_user_table")
public class User {
    @Column(name="id")
    @Id
    private int id;

    @Column(name="username")
    public String userName;

    @Column(name="email")
    public String userMail;

    @Column(name="password")
    public String userPassword;

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public String getUserPassword() {
        return userPassword;
    }
}