package exam.kea.dk.eksamenjanuar2025backend.controller;

import exam.kea.dk.eksamenjanuar2025backend.model.Drone;
import exam.kea.dk.eksamenjanuar2025backend.model.DroneStatus;
import exam.kea.dk.eksamenjanuar2025backend.service.DroneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/enable")
    public ResponseEntity<Drone> enableDrone(@RequestParam Long droneId) {
        Drone updatedDrone = droneService.updateDroneStatus(droneId, DroneStatus.IN_SERVICE);
        return new ResponseEntity<>(updatedDrone, HttpStatus.OK);
    }

    @PostMapping("/disable")
    public ResponseEntity<Drone> disableDrone(@RequestParam Long droneId) {
        Drone updatedDrone = droneService.updateDroneStatus(droneId, DroneStatus.OUT_OF_SERVICE);
        return new ResponseEntity<>(updatedDrone, HttpStatus.OK);
    }

    @PostMapping("/retire")
    public ResponseEntity<Drone> retireDrone(@RequestParam Long droneId) {
        Drone updatedDrone = droneService.updateDroneStatus(droneId, DroneStatus.PHASED_OUT);
        return new ResponseEntity<>(updatedDrone, HttpStatus.OK);
    }
}
