package exam.kea.dk.eksamenjanuar2025backend.repository;

import exam.kea.dk.eksamenjanuar2025backend.model.Drone;
import exam.kea.dk.eksamenjanuar2025backend.model.DroneStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    List<Drone> findAllByStatus(DroneStatus droneStatus);
}
