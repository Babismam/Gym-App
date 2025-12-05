package com.example.gymmanagement.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;


@Entity
@Immutable
@Table(name = "member_full_info")
public class MemberFullInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", columnDefinition = "SERIAL")
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
    @Column(name = "phone", length = 20)
    private String phone;

    @Size(max = 50)
    @Column(name = "username", length = 50)
    private String username;

    @Size(max = 20)
    @Column(name = "role", length = 20)
    private String role;

    @Column(name = "enrolled_programs")
    private Long enrolledPrograms;

    @Column(name = "total_sessions")
    private Long totalSessions;

    @Column(name = "present_sessions")
    private Long presentSessions;

    @Column(name = "attendance_percentage")
    private BigDecimal attendancePercentage;

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

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public Long getEnrolledPrograms() {
        return enrolledPrograms;
    }

    public Long getTotalSessions() {
        return totalSessions;
    }

    public Long getPresentSessions() {
        return presentSessions;
    }

    public BigDecimal getAttendancePercentage() {
        return attendancePercentage;
    }

    protected MemberFullInfo() {
    }
}