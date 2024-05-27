package de.telran.urlshortener.dto;

import de.telran.urlshortener.model.entity.subscription.Status;
import de.telran.urlshortener.model.entity.user.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class SubscriptionRequestDto {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate creationDate;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate expirationDate;

    @NotNull
    private Status status;

    @NotNull
    private User user;

    public SubscriptionRequestDto(LocalDate creationDate, LocalDate expirationDate, Status status, User user) {
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.status = status;
        this.user = user;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public Status getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setUser(User user) {
        this.user = user;
    }
}




