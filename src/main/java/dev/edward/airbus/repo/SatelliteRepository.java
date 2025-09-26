package dev.edward.airbus.repo;

import dev.edward.airbus.domain.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SatelliteRepository extends JpaRepository<Satellite, UUID> {
    Optional<Satellite> findByNameIgnoreCase(String name);
}
