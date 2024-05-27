package de.telran.urlshortener.dto.statistics;

import java.util.Map;

public record UserStatisticsResponse(Long id, Map<Long, Long> bindingIdToCount) {
}
