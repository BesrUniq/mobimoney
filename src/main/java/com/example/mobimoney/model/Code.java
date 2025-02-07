package com.example.mobimoney.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "codes")
@AllArgsConstructor
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private int balance;

    private boolean used = false;

    @ManyToOne
    @JoinColumn(name = "used_by")
    private User usedBy;

    private LocalDateTime usedAt;

    public Code() {
    }

    public Code(String code, int balance) {
        this.code = code;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isUsed() {
        return used;
    }

    public User getUsedBy() {
        return usedBy;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public void setUsedBy(User usedBy) {
        this.usedBy = usedBy;
    }

    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }
}

