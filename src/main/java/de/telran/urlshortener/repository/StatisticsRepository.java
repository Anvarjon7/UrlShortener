package de.telran.urlshortener.repository;

import de.telran.urlshortener.dto.statistics.TopBindingStatisticsResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StatisticsRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<TopBindingStatisticsResponse> getTopBinding(int topParam) {
        return entityManager.createQuery(
                        "SELECT new com.example.TopBindingStatisticsResponse(u.baseUrl, COUNT(u)) " +
                                "FROM UrlBinding u " +
                                "GROUP BY u.uid " +
                                "ORDER BY COUNT(u) DESC", TopBindingStatisticsResponse.class)
                .setMaxResults(topParam)
                .getResultList(); //todo написать запрос топ связок по кол-ву переходов

    }

}
