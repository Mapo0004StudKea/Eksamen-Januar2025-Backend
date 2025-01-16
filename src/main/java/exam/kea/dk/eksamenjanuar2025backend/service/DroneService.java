package exam.kea.dk.eksamenjanuar2025backend.service;

import exam.kea.dk.eksamenjanuar2025backend.model.Drone;
import exam.kea.dk.eksamenjanuar2025backend.model.DroneStatus;
import exam.kea.dk.eksamenjanuar2025backend.model.Station;
import exam.kea.dk.eksamenjanuar2025backend.repository.DroneRepository;
import exam.kea.dk.eksamenjanuar2025backend.repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class DroneService {

    private final DroneRepository DroneRepository;
    private final StationRepository StationRepository;

    public DroneService(DroneRepository DroneRepository, StationRepository StationRepository) {
        this.DroneRepository = DroneRepository;
        this.StationRepository = StationRepository;
    }

    public List<Drone> getAllDrones() {
        return DroneRepository.findAll();
    }

    public Drone addDrone() {
        Station station = StationRepository.findAll()
                .stream()
                .min(Comparator.comparingInt(s -> s.getDrones().size()))
                .orElseThrow(() -> new IllegalStateException("No stations available"));

        Drone drone = new Drone();
        drone.setUuid(UUID.randomUUID().toString());
        drone.setStatus(DroneStatus.IN_SERVICE);
        drone.setStation(station);

        return DroneRepository.save(drone);
    }
}
