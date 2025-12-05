package com.example.gymmanagement.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name = "user_permissions")
public class UserPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Size(max = 100)
    @Column(name = "first_name", length = 100)
    private String firstName;

    @Size(max = 100)
    @Column(name = "last_name", length = 100)
    private String lastName;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 20)
    @Column(name = "role", length = 20)
    private String role;

    @Size(max = 50)
    @Column(name = "permission", length = 50)
    private String permission;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;

    public Integer getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    protected UserPermission() {
    }
}