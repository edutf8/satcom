package dev.edward.airbus.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record SatelliteResponse(
        UUID uuid,
        String name,
        String orbitType,
        LocalDateTime launchDate,
        ParametersDto parameters
) {}
