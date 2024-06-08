package de.telran.urlshortener.repository;

import de.telran.urlshortener.model.entity.binding.UrlBinding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface UrlBindingRepository extends JpaRepository<UrlBinding, Long> {

    @Query("select u from UrlBinding u where u.uid = :uid and u.isClosed = false and u.expirationDate >=CURRENT_DATE")
    Optional<UrlBinding> findActualByUid(String uid);

    @Query("select u from UrlBinding u where u.uid = :uid")
    Optional<UrlBinding> findByUid(String uid);

    Set<UrlBinding> findByUser_Id(Long userId);

    //    @Query("select u from UrlBinding u where u.baseUrl || u.pathPrefix || u.uid = :shortUrl")
    @Query("select u from UrlBinding u where u.pathPrefix || u.uid = :shortUrl")
    Optional<UrlBinding> findByShortUrl(String shortUrl);
}

