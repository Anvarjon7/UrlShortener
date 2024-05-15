package de.telran.urlshortener.repository;

import de.telran.urlshortener.model.entity.binding.UrlBinding;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UrlBindingRepository extends JpaRepository<UrlBinding, Long> {

    @EntityGraph(value = "UrlBinding.withUser", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select u from UrlBinding u where u.id = :id")
    Optional<UrlBinding> findByIdWithUser(Long id);

    @Query("select u from UrlBinding u where u.uid = :uid and u.isClosed = false and u.expirationDate >=CURRENT_DATE")
    Optional<UrlBinding> findActualByUid(String uid);

}

