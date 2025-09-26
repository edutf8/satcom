package dev.edward.airbus.service;

import dev.edward.airbus.api.dto.ParametersDto;
import dev.edward.airbus.api.dto.SatelliteCreateRequest;
import dev.edward.airbus.api.dto.SatelliteResponse;
import dev.edward.airbus.api.dto.SatelliteUpdateRequest;
import dev.edward.airbus.domain.Satellite;
import dev.edward.airbus.domain.SatelliteParameters;
import dev.edward.airbus.exception.NotFoundException;
import dev.edward.airbus.repo.SatelliteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SatelliteService {

    private final SatelliteRepository satelliteRepository;

    public SatelliteService(SatelliteRepository satelliteRepository) {
        this.satelliteRepository = satelliteRepository;
    }

    public List<SatelliteResponse> listAll() {
        return satelliteRepository.findAll().stream().map(this::toResponse).toList();
    }

    public SatelliteResponse getById(UUID uuid) {
        return toResponse(fetch(uuid));
    }

    public SatelliteResponse create(SatelliteCreateRequest satelliteCreateRequest) {
        satelliteRepository.findByNameIgnoreCase(satelliteCreateRequest.getName()).ifPresent(satellite -> {
            throw new IllegalArgumentException("Satellite with name already exists: " + satelliteCreateRequest.getName());
        });

        SatelliteParameters satelliteParameters = new SatelliteParameters();
        satelliteParameters.setAltitude(satelliteCreateRequest.getParameters().getAltitude());
        satelliteParameters.setLatitude(satelliteCreateRequest.getParameters().getLatitude());
        satelliteParameters.setLongitude(satelliteCreateRequest.getParameters().getLongitude());

        Satellite satellite = new Satellite();
        satellite.setName(satelliteCreateRequest.getName().trim());
        satellite.setOrbitType(satelliteCreateRequest.getOrbitType());
        satellite.setLaunchDate(satelliteCreateRequest.getLaunchDate());
        satellite.setSatelliteParameters(satelliteParameters);

        return toResponse(satelliteRepository.save(satellite));
    }

    public SatelliteResponse update(UUID uuid, SatelliteUpdateRequest satelliteUpdateRequest) {
        Satellite satellite = fetch(uuid);
        satellite.setName(satelliteUpdateRequest.getName().trim());
        satellite.setOrbitType(satelliteUpdateRequest.getOrbitType());
        satellite.setLaunchDate(satelliteUpdateRequest.getLaunchDate());

        SatelliteParameters satelliteParameters = new SatelliteParameters();
        satelliteParameters.setAltitude(satelliteUpdateRequest.getParameters().getAltitude());
        satelliteParameters.setLatitude(satelliteUpdateRequest.getParameters().getLatitude());
        satelliteParameters.setLongitude(satelliteUpdateRequest.getParameters().getLongitude());
        satellite.setSatelliteParameters(satelliteParameters);

        return toResponse(satellite);
    }

    public void delete(UUID uuid) {
        if (!satelliteRepository.existsById(uuid)) throw new NotFoundException("Satellite with id " + uuid + " does not exist");
        satelliteRepository.deleteById(uuid);
    }

    public ParametersDto parameters(UUID uuid) {
        Satellite satellite = fetch(uuid);
        ParametersDto parametersDto = new ParametersDto();
        parametersDto.setAltitude(satellite.getSatelliteParameters().getAltitude());
        parametersDto.setLatitude(satellite.getSatelliteParameters().getLatitude());
        parametersDto.setLongitude(satellite.getSatelliteParameters().getLongitude());
        return parametersDto;
    }

    private Satellite fetch(UUID id) {
        return satelliteRepository.findById(id).orElseThrow(() -> new NotFoundException("Satellite not found: " + id));
    }

    private SatelliteResponse toResponse(Satellite satellite) {
        ParametersDto parametersDto = new ParametersDto();
        parametersDto.setAltitude(satellite.getSatelliteParameters().getAltitude());
        parametersDto.setLatitude(satellite.getSatelliteParameters().getLatitude());
        parametersDto.setLongitude(satellite.getSatelliteParameters().getLongitude());
        return new SatelliteResponse(satellite.getUuid(), satellite.getName(), satellite.getOrbitType().name(), satellite.getLaunchDate(), parametersDto);
    }

}
