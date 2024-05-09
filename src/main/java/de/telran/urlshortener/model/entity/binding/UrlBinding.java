package de.telran.urlshortener.model.entity.binding;

import de.telran.urlshortener.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "url_bindings")
@EqualsAndHashCode(exclude = "user")
@ToString(exclude = "user")
public class UrlBinding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_url", nullable = false)
    private String originalUrl;
    @Column(name = "base_url", nullable = false)
    private String baseUrl;
    @Column(name = "path_prefix", nullable = false)
    private String pathPrefix;
    @Column(name = "uid", nullable = false, unique = true)
    private String uid;
    private Long count;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDate creationDate = LocalDate.now();

    @Column(name = "expires_at")
    @Builder.Default
    private LocalDate expirationDate = LocalDate.now().plusDays(30);
    @Column(nullable = false)
    @Builder.Default
    private Boolean isClosed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}