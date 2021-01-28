package com.nikkodasig.springbudgetapi.registration;

import com.nikkodasig.springbudgetapi.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id")
    private User user;

    public ConfirmationToken() { }

    public ConfirmationToken(String token, User user) {
        this.token = token;
        this.createdAt = LocalDateTime.now();
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
