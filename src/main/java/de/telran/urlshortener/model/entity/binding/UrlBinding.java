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
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "UrlBindings.withUser",
                attributeNodes = @NamedAttributeNode("user")
        )
})
public class UrlBinding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;
    @Column(name = "original_url", nullable = false)
    @Getter @Setter
    private String originalUrl;
    @Column(name = "base_url", nullable = false)
    @Getter @Setter
    private String baseUrl;
    @Column(name = "path_prefix", nullable = false)
    @Getter @Setter
    private String pathPrefix;
    @Column(name = "uid", nullable = false, unique = true)
    @Getter @Setter
    private String uid;

    private Long count = 0L;

    @Column(name = "created_at")
    @Builder.Default
    @Getter @Setter
    private LocalDate creationDate = LocalDate.now();

    @Column(name = "expires_at")
    @Builder.Default
    private LocalDate expirationDate = LocalDate.now().plusDays(30);
    @Column(nullable = false)
    @Builder.Default
    private Boolean isClosed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter @Setter
    private User user;

    public String getShort(){
        return this.getBaseUrl()+this.getPathPrefix()+this.getUid();
    }
}