package com.example.gymmanagement.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 50)
    @Column(name = "last_name")
    private String lastName;

    @Email
    @NotBlank
    @Column(unique = true, nullable = false)
    private String email;

    private String phone;

    @Column(length = 1024)
    private String bio;

    private String specialties;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @JsonIgnore
    @NotBlank
    @Size(min = 8)
    private String password;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private UserRole role;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "membership_type")
    private String membershipType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "membership_start_date")
    private LocalDate membershipStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "membership_end_date")
    private LocalDate membershipEndDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_status")
    private MembershipStatus membershipStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "pause_end_date")
    private LocalDate pauseEndDate;

    @Column(name = "paused_by_admin")
    private Boolean pausedByAdmin = false;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public User() {}

    public User(String firstName, String lastName, String email, String phone, String password, String username, UserRole role, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        setPassword(password);
        this.username = username;
        this.role = role;
        this.isActive = true;
        this.dateOfBirth = dateOfBirth;
        if (role == UserRole.MEMBER) {
            this.membershipStatus = MembershipStatus.ACTIVE;
        }
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (isActive == null) isActive = true;
        if (pausedByAdmin == null) pausedByAdmin = false;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getSpecialties() { return specialties; }
    public void setSpecialties(String specialties) { this.specialties = specialties; }
    public String getProfileImageUrl() { return profileImageUrl; }
    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }
    public MembershipStatus getMembershipStatus() { return membershipStatus; }
    public void setMembershipStatus(MembershipStatus membershipStatus) { this.membershipStatus = membershipStatus; }
    public LocalDate getPauseEndDate() { return pauseEndDate; }
    public void setPauseEndDate(LocalDate pauseEndDate) { this.pauseEndDate = pauseEndDate; }
    public Boolean getPausedByAdmin() { return pausedByAdmin; }
    public void setPausedByAdmin(Boolean pausedByAdmin) { this.pausedByAdmin = pausedByAdmin; }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
    @JsonIgnore public String getPassword() { return password; }
    public void setPassword(String rawPassword) { if (rawPassword != null && !rawPassword.trim().isEmpty()) { this.password = BCrypt.hashpw(rawPassword, BCrypt.gensalt(12)); } }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }
    public LocalDate getMembershipStartDate() { return membershipStartDate; }
    public void setMembershipStartDate(LocalDate membershipStartDate) { this.membershipStartDate = membershipStartDate; }
    public LocalDate getMembershipEndDate() { return membershipEndDate; }
    public void setMembershipEndDate(LocalDate membershipEndDate) { this.membershipEndDate = membershipEndDate; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    public boolean verifyPassword(String rawPassword) { if (rawPassword == null || password == null) return false; return BCrypt.checkpw(rawPassword, password); }
    public String getFullName() { return firstName + " " + lastName; }
    public boolean isMembershipActive() {
        if (!isActive || !isMember()) return false;
        return membershipStatus == MembershipStatus.ACTIVE && (membershipEndDate == null || !LocalDate.now().isAfter(membershipEndDate));
    }
    public boolean isAdmin() { return UserRole.ADMIN.equals(role); }
    public boolean isTrainer() { return UserRole.TRAINER.equals(role); }
    public boolean isMember() { return UserRole.MEMBER.equals(role); }
    public long getDaysUntilMembershipExpiry() { if (membershipEndDate == null) return -1; return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), membershipEndDate); }
    public boolean isMembershipExpiring() { long daysLeft = getDaysUntilMembershipExpiry(); return daysLeft >= 0 && daysLeft <= 7; }
    @Override public String toString() { return "User{" + "id=" + id + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", username='" + username + '\'' + ", role='" + role + '\'' + ", membershipType='" + membershipType + '\'' + ", isActive=" + isActive + '}'; }
    @Override public boolean equals(Object o) { if (this == o) return true; if (!(o instanceof User)) return false; User u = (User) o; return id != null && id.equals(u.id); }
    @Override public int hashCode() { return id != null ? id.hashCode() : 0; }
}