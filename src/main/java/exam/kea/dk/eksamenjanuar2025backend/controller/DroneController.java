package exam.kea.dk.eksamenjanuar2025backend.controller;

import exam.kea.dk.eksamenjanuar2025backend.model.Drone;
import exam.kea.dk.eksamenjanuar2025backend.service.DroneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drones")
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @GetMapping
    public List<Drone> getAll() {
        return droneService.getAllDrones();
    }

    @PostMapping("/add")
    public ResponseEntity<Drone> addDrone() {
        try {
            Drone drone = droneService.addDrone();
            return ResponseEntity.ok(drone);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
