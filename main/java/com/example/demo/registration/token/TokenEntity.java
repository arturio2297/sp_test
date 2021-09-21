package com.example.demo.registration.token;

import com.example.demo.users.UserEntity;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;

@Service
@Entity
public class TokenEntity {

    @SequenceGenerator(
            name = "token_id_sequence",
            sequenceName = "token_id_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_id_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    @Column(nullable = false)
    private LocalDateTime expiresTime;

    @Column
    private LocalDateTime confirmedTime;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "user_id"
    )
    private UserEntity user;

    public TokenEntity(String token,
                       LocalDateTime createdTime,
                       LocalDateTime expiresTime,
                       UserEntity user) {
        this.token = token;
        this.createdTime = createdTime;
        this.expiresTime = expiresTime;
        this.user = user;
    }

    public TokenEntity() { }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(LocalDateTime expiresTime) {
        this.expiresTime = expiresTime;
    }

    public LocalDateTime getConfirmedTime() {
        return confirmedTime;
    }

    public void setConfirmedTime(LocalDateTime confirmedTime) {
        this.confirmedTime = confirmedTime;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
