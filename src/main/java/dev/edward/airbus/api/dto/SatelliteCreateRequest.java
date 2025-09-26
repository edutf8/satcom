package dev.edward.airbus.api.dto;

import dev.edward.airbus.domain.OrbitType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class SatelliteCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    private OrbitType orbitType;

    @NotNull
    private LocalDateTime launchDate;

    @NotNull @Valid
    private ParametersDto parameters;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrbitType getOrbitType() {
        return orbitType;
    }

    public void setOrbitType(OrbitType orbitType) {
        this.orbitType = orbitType;
    }

    public LocalDateTime getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDateTime launchDate) {
        this.launchDate = launchDate;
    }

    public ParametersDto getParameters() {
        return parameters;
    }

    public void setParameters(ParametersDto parameters) {
        this.parameters = parameters;
    }
}
