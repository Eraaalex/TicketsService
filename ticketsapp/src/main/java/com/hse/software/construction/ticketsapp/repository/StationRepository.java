package com.hse.software.construction.ticketsapp.repository;

import com.hse.software.construction.ticketsapp.model.Station;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends CrudRepository<Station, Long> {
    Optional<Station> findById(Long id);

    Optional<Station> findByStation(String station);
}
