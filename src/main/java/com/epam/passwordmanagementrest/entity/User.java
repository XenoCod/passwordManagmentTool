package com.epam.passwordmanagementrest.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class User {
    @Id
    @Column(name = "userName")
    private String userName;

    @Column(name = "accountName")
    private String accountName;

    @Column(name = "password")
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", accountName='" + accountName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public Collection<Group> getGroup() {
        return group;
    }

    public void setGroup(Collection<Group> group) {
        this.group = group;
    }

    @OneToMany(mappedBy = "user")
    private Collection<Group> group;


    public User(){

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public User(String userName, String password){
        this.userName= userName;
        this.password=password;
    }

    public User(String accountName, String userName, String password){
        this.userName= userName;
        this.password= password;
        this.accountName= accountName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
