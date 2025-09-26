package dev.edward.airbus.service;

import dev.edward.airbus.api.dto.ParametersDto;
import dev.edward.airbus.api.dto.SatelliteCreateRequest;
import dev.edward.airbus.domain.OrbitType;
import dev.edward.airbus.repo.SatelliteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SatelliteServiceTest {

    private SatelliteRepository satelliteRepository;
    private SatelliteService satelliteService;

    @BeforeEach
    void setUp() {
        satelliteRepository = Mockito.mock(SatelliteRepository.class);
        satelliteService = new SatelliteService(satelliteRepository);
    }

    @Test
    void createRejectsDuplicateName() {
        var request = new SatelliteCreateRequest();
        request.setName("DuplicateSatellite");
        request.setOrbitType(OrbitType.LEO);
        request.setLaunchDate(LocalDateTime.parse("2023-01-01T00:00:00"));
        var parameters = new ParametersDto();
        parameters.setAltitude(500.0);
        parameters.setLatitude(0.0);
        parameters.setLongitude(90.0);
        request.setParameters(parameters);

        when(satelliteRepository.findByNameIgnoreCase("DuplicateSatellite"))
                .thenReturn(java.util.Optional.of(new dev.edward.airbus.domain.Satellite()));

        var ex = assertThrows(IllegalArgumentException.class, () -> satelliteService.create(request));
        assertTrue(ex.getMessage().contains("already exists"));
    }

}
