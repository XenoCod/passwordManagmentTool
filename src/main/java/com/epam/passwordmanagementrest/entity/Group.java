package com.epam.passwordmanagementrest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name="userGroups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_fk")
    private User user;

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", user=" + user +
                '}';
    }

    public Group(String name) {
        this.name = name;
    }

    public Group() {
    }

    public Group(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
