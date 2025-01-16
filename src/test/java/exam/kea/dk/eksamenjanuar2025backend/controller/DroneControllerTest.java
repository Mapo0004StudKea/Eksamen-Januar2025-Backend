package exam.kea.dk.eksamenjanuar2025backend.controller;

import exam.kea.dk.eksamenjanuar2025backend.model.Drone;
import exam.kea.dk.eksamenjanuar2025backend.model.DroneStatus;
import exam.kea.dk.eksamenjanuar2025backend.service.DroneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DroneControllerTest {

    @InjectMocks
    private DroneController droneController; // Controlleren, vi tester

    @Mock
    private DroneService droneService; // Mock af service-laget

    @Test
    void addDrone() {
        // Arrange
        Drone mockDrone = new Drone();
        mockDrone.setId(1L);
        mockDrone.setUuid("123e4567-e89b-12d3-a456-426614174000");
        mockDrone.setStatus(DroneStatus.IN_SERVICE);

        Mockito.when(droneService.addDrone()).thenReturn(mockDrone);

        // Act
        ResponseEntity<Drone> response = droneController.addDrone();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void addDrone_shouldReturnBadRequestOnFailure() {
        // Arrange
        Mockito.when(droneService.addDrone()).thenThrow(new IllegalStateException("No stations available"));

        // Act
        ResponseEntity<Drone> response = droneController.addDrone();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void enableDrone() {
        // Arrange
        Long droneId = 1L;
        Drone mockDrone = new Drone();
        mockDrone.setId(droneId);
        mockDrone.setStatus(DroneStatus.IN_SERVICE);

        Mockito.when(droneService.updateDroneStatus(droneId, DroneStatus.IN_SERVICE)).thenReturn(mockDrone);

        // Act
        ResponseEntity<Drone> response = droneController.enableDrone(droneId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(DroneStatus.IN_SERVICE, response.getBody().getStatus());
    }

    @Test
    void disableDrone() {
        // Arrange
        Long droneId = 2L;
        Drone mockDrone = new Drone();
        mockDrone.setId(droneId);
        mockDrone.setStatus(DroneStatus.OUT_OF_SERVICE);

        Mockito.when(droneService.updateDroneStatus(droneId, DroneStatus.OUT_OF_SERVICE)).thenReturn(mockDrone);

        // Act
        ResponseEntity<Drone> response = droneController.disableDrone(droneId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(DroneStatus.OUT_OF_SERVICE, response.getBody().getStatus());
    }

    @Test
    void retireDrone() {
        // Arrange
        Long droneId = 3L;
        Drone mockDrone = new Drone();
        mockDrone.setId(droneId);
        mockDrone.setStatus(DroneStatus.PHASED_OUT);

        Mockito.when(droneService.updateDroneStatus(droneId, DroneStatus.PHASED_OUT)).thenReturn(mockDrone);

        // Act
        ResponseEntity<Drone> response = droneController.retireDrone(droneId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(DroneStatus.PHASED_OUT, response.getBody().getStatus());
    }
}