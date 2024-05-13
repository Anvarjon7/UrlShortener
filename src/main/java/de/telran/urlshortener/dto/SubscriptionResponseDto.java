package de.telran.urlshortener.dto;

import de.telran.urlshortener.model.entity.subscription.Status;
import de.telran.urlshortener.model.entity.user.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
/*@NoArgsConstructor
@AllArgsConstructor*/
public class SubscriptionResponseDto {
    private Long id;
    private LocalDate creationDate ;
    private LocalDate expirationDate ;
    private Status status;
    private User user;

}
