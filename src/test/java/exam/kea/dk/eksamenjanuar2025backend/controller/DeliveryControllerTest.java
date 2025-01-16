package exam.kea.dk.eksamenjanuar2025backend.controller;

import exam.kea.dk.eksamenjanuar2025backend.model.Delivery;
import exam.kea.dk.eksamenjanuar2025backend.model.Drone;
import exam.kea.dk.eksamenjanuar2025backend.service.DeliveryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeliveryControllerTest {

    @InjectMocks
    private DeliveryController deliveryController;

    @Mock
    private DeliveryService deliveryService;

    @Test
    void addDelivery() {
        // Arrange
        Long pizzaId = 1L;
        String address = "Nørrebrogade 45, 2200 København";
        Delivery mockDelivery = new Delivery();
        mockDelivery.setId(1L);
        mockDelivery.setAddress(address);

        Mockito.when(deliveryService.addDelivery(pizzaId, address)).thenReturn(mockDelivery);

        // Act
        Delivery response = deliveryController.addDelivery(pizzaId, address);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(address, response.getAddress());
    }

    @Test
    void scheduleDelivery() {
        // Arrange
        Long deliveryId = 1L;
        Long droneId = 2L;
        Delivery mockDelivery = new Delivery();
        mockDelivery.setId(deliveryId);
        Drone mockDrone = new Drone();
        mockDrone.setId(droneId);
        mockDelivery.setDrone(mockDrone);

        Mockito.when(deliveryService.scheduleDelivery(deliveryId, droneId)).thenReturn(mockDelivery);

        // Act
        Delivery response = deliveryController.scheduleDelivery(deliveryId, droneId);

        // Assert
        assertNotNull(response);
        assertEquals(deliveryId, response.getId());
        assertNotNull(response.getDrone());
        assertEquals(droneId, response.getDrone().getId());
    }

    @Test
    void finishDelivery() {
        // Arrange
        Long deliveryId = 1L;
        Delivery mockDelivery = new Delivery();
        mockDelivery.setId(deliveryId);
        mockDelivery.setActualDeliveryTime(LocalDateTime.now());

        Mockito.when(deliveryService.finishDelivery(deliveryId)).thenReturn(mockDelivery);

        // Act
        Delivery response = deliveryController.finishDelivery(deliveryId);

        // Assert
        assertNotNull(response);
        assertEquals(deliveryId, response.getId());
        assertNotNull(response.getActualDeliveryTime());
    }
}