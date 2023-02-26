package com.philipstarritt.fitness.domain;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record FitnessClass(
        UUID id,
        Coach coach,
        LocalDateTime startsAt,
        LocalDateTime endsAt,
        Difficulty difficulty
) {

}
