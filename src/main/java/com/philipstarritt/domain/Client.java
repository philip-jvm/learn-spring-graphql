package com.philipstarritt.domain;

import lombok.Builder;

import java.util.UUID;

@Builder
public record Client(
        String id,
        String name,
        UUID advisorId
) {
}
