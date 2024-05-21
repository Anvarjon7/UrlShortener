package de.telran.urlshortener.dto.statistics;

import java.util.Map;

public record UserStatisticsResponse(Map<Long, Long> bindingIdToCount) {
}
