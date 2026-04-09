package com.bookstore.profilemanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String location;
    private String dept;
    @Column(name = "is_admin")
    private boolean isAdmin;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    public User() {}
        public int getId() {
            return id;
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
        public String getPassword() {
            return password;
        }
        public String getLocation() {
            return location;
        }
        public String getDept() {
            return dept;
        }
        public boolean isAdmin() {
            return isAdmin;
        }
        public LocalDateTime getCreatedAt() {
            return createdAt;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public void setPassword(String password) {
            this.password = password;
        }
        public void setLocation(String location) {
            this.location = location;
        }
        public void setDept(String dept) {
            this.dept = dept;
        }
        public void setAdmin(boolean admin) {
            isAdmin = admin;
        }
        public void setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
        }
}
