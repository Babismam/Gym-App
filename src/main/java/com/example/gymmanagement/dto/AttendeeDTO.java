package com.example.gymmanagement.dto;

import com.example.gymmanagement.entities.User;

public class AttendeeDTO {
    private Long id;
    private String firstName;
    private String lastName;

    public AttendeeDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}