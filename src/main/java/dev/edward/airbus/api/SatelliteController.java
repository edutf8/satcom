package dev.edward.airbus.api;

import dev.edward.airbus.api.dto.ParametersDto;
import dev.edward.airbus.api.dto.SatelliteCreateRequest;
import dev.edward.airbus.api.dto.SatelliteResponse;
import dev.edward.airbus.api.dto.SatelliteUpdateRequest;
import dev.edward.airbus.service.SatelliteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/satellites")
public class SatelliteController {

    private final SatelliteService satelliteService;

    public SatelliteController(SatelliteService satelliteService) {
        this.satelliteService = satelliteService;
    }

    @GetMapping
    public List<SatelliteResponse> listAll() {
        return satelliteService.listAll();
    }

    @GetMapping("/{id}")
    public SatelliteResponse get(@PathVariable UUID id) {
        return satelliteService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SatelliteResponse create(@RequestBody @Valid SatelliteCreateRequest satelliteCreateRequest) {
        return satelliteService.create(satelliteCreateRequest);
    }

    @PutMapping("/{id}")
    public SatelliteResponse update(@PathVariable UUID id, @RequestBody @Valid SatelliteUpdateRequest satelliteUpdateRequest) {
        return satelliteService.update(id, satelliteUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        satelliteService.delete(id);
    }

    @GetMapping("/{id}/parameters")
    public ParametersDto parameters(@PathVariable UUID id) {
        return satelliteService.parameters(id);
    }
}
