package de.telran.urlshortener.repository;

import de.telran.urlshortener.model.entity.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    @EntityGraph(value = "User.withSubscriptions", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findByIdWithSubscriptions(Long id);
    @EntityGraph(value = "User.withUrlBindings", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findByIdWithBindings(Long id);
    @EntityGraph(value = "User.withUrlBindingsAndSubscriptions", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findByIdWithUrlBindingsAndSubscriptions(Long id);
}
