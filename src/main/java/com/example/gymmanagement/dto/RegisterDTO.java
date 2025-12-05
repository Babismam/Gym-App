package com.example.gymmanagement.dto;

import java.time.LocalDate;

public class RegisterDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String username;
    private String password;
    private String role;
    private LocalDate dateOfBirth;
    private String membershipType;
    private String membershipDuration; // e.g., "3-MONTH", "6-MONTH", "CUSTOM"
    private LocalDate customStartDate;
    private LocalDate customEndDate;

    public RegisterDTO() {}

    public String getMembershipDuration() { return membershipDuration; }
    public void setMembershipDuration(String membershipDuration) { this.membershipDuration = membershipDuration; }
    public LocalDate getCustomStartDate() { return customStartDate; }
    public void setCustomStartDate(LocalDate customStartDate) { this.customStartDate = customStartDate; }
    public LocalDate getCustomEndDate() { return customEndDate; }
    public void setCustomEndDate(LocalDate customEndDate) { this.customEndDate = customEndDate; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
}