package com.epam.passwordmanagementrest.dto;

import javax.validation.constraints.Pattern;

public class GroupDTO {
    private int id;

    @Pattern(regexp = "(?=.*[A-Z])(?=\\S+$).{5,20}$", message = "Size:{5, 20}, Atleast 1 {upper}!")
    private String name;

    public GroupDTO(String name) {
        this.name = name;
    }

    public GroupDTO() {
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

    @Override
    public String toString() {
        return "GroupDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}

