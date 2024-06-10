package com.hse.software.construction.ticketsapp.service;


import com.hse.software.construction.ticketsapp.model.Station;
import com.hse.software.construction.ticketsapp.repository.StationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class StationService {
    private StationRepository stationRepository;

    public void createStation(Station station) throws IllegalArgumentException {
        if (stationRepository.findByStation(station.getStation()).isPresent()) {
            throw new IllegalArgumentException("Station already exists");
        }
        stationRepository.save(station);
    }

    public void deleteStation(Long id) {
        stationRepository.deleteById(id);
    }

    public Station getStationById(Long id) {

        Optional<Station> station = stationRepository.findById(id);
        log.info("find " + station);
        return station.orElse(null);
    }
}
