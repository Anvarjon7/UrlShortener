package de.telran.urlshortener.model.entity.user;

import de.telran.urlshortener.model.entity.binding.UrlBinding;
import de.telran.urlshortener.model.entity.subscription.Subscription;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor (access = AccessLevel.PRIVATE)
@Builder
@EqualsAndHashCode(exclude = {"bindings","subscriptions"})
@ToString(exclude = {"bindings","subscriptions"})
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "User.withUrlBindings",
                attributeNodes = @NamedAttributeNode("bindings")
        ),
        @NamedEntityGraph(
                name = "User.withSubscriptions",
                attributeNodes = @NamedAttributeNode("subscriptions")
        ),
        @NamedEntityGraph(
                name = "User.withUrlBindingsAndSubscriptions",
                attributeNodes = {@NamedAttributeNode("bindings"),@NamedAttributeNode("subscriptions")}
        )
})
@Table(name = "user_")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email",nullable = false,unique = true)
    private String email;

    @Column(name = "password")
    private String password;


    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Subscription> subscriptions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<UrlBinding> bindings;




}
