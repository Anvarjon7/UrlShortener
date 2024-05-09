package de.telran.urlshortener.repository;

import de.telran.urlshortener.model.entity.binding.UrlBinding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlBindingRepository extends JpaRepository<UrlBinding, Long> {

    Optional<UrlBinding> findByUid(String uid);
}
