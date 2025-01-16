package exam.kea.dk.eksamenjanuar2025backend.service;

import exam.kea.dk.eksamenjanuar2025backend.dto.StationDTO;
import exam.kea.dk.eksamenjanuar2025backend.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {

    private final StationRepository stationRepository;

    public StationService(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    public List<StationDTO> getAllStations() {
        return stationRepository.findAll().stream()
                .map(station -> new StationDTO(
                        station.getId(),
                        station.getLatitude(),
                        station.getLongitude()
                ))
                .collect(Collectors.toList());
    }
}
