package de.telran.urlshortener.repository;

import de.telran.urlshortener.model.entity.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(value = "User.withSubscriptions", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select u from User u where u.id = :id")
    Optional<User> findByIdWithSubscriptions(Long id);

    @EntityGraph(value = "User.withUrlBindings", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select u from User u where u.id = :id")
    Optional<User> findByIdWithBindings(Long id);

    @EntityGraph(value = "User.withUrlBindingsAndSubscriptions", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select u from User u where u.id = :id")
    Optional<User> findByIdWithUrlBindingsAndSubscriptions(Long id);
}
