package de.telran.urlshortener.dto.statistics;

import lombok.Data;

import java.util.Map;
public record UserStatisticsResponse (Map<Long, Long> bindingIdToCount) {}
