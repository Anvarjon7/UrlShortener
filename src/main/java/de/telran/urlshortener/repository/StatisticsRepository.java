package de.telran.urlshortener.repository;

import de.telran.urlshortener.dto.statistics.TopBindingStatisticsResponse;
import de.telran.urlshortener.dto.statistics.TopRecord;
import de.telran.urlshortener.model.entity.binding.UrlBinding;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StatisticsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public TopBindingStatisticsResponse getTopUrlBinding(int limit) {
        List<Tuple> results = entityManager.createQuery(
                "SELECT ub.id as bindingId, u.id as userId, ub.count as count " +
                        "FROM UrlBinding ub JOIN ub.user u " +
                        "WHERE ub.count IS NOT NULL " +
                        "ORDER BY ub.count DESC " , Tuple.class)
                .setMaxResults(limit)
                .getResultList();

        List<TopRecord> topRecords = results.stream()
                .map(tuple -> new TopRecord(
                        tuple.get("bindingId", Long.class),
                        tuple.get("userId", Long.class),
                        tuple.get("count", Long.class)))
                .collect(Collectors.toList());

        return new TopBindingStatisticsResponse(topRecords);
    }
}
