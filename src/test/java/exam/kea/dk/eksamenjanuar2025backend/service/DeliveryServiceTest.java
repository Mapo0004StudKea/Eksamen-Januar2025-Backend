package exam.kea.dk.eksamenjanuar2025backend.service;

import exam.kea.dk.eksamenjanuar2025backend.model.Delivery;
import exam.kea.dk.eksamenjanuar2025backend.model.Drone;
import exam.kea.dk.eksamenjanuar2025backend.model.DroneStatus;
import exam.kea.dk.eksamenjanuar2025backend.model.Pizza;
import exam.kea.dk.eksamenjanuar2025backend.repository.DeliveryRepository;
import exam.kea.dk.eksamenjanuar2025backend.repository.DroneRepository;
import exam.kea.dk.eksamenjanuar2025backend.repository.PizzaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Service test
 * bruger til at teste forretningslogikken uafhængigt af controlleren.
 */

@ExtendWith(MockitoExtension.class)
class DeliveryServiceTest {

    @InjectMocks
    private DeliveryService deliveryService;

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private PizzaRepository pizzaRepository;

    @Mock
    private DroneRepository droneRepository;

    @Test
    // En test for at tilføje en delivery
    void addDelivery_shouldCreateNewDelivery() {
        // Arrange
        Long pizzaId = 1L;
        String address = "Nørrebrogade 45, 2200 København";

        Pizza mockPizza = new Pizza();
        mockPizza.setId(pizzaId);

        Delivery mockDelivery = new Delivery();
        mockDelivery.setId(1L);
        mockDelivery.setAddress(address);
        mockDelivery.setPizza(mockPizza);

        Mockito.when(pizzaRepository.findById(pizzaId)).thenReturn(Optional.of(mockPizza));
        Mockito.when(deliveryRepository.save(Mockito.any(Delivery.class))).thenReturn(mockDelivery);

        // Act
        Delivery result = deliveryService.addDelivery(pizzaId, address);

        // Assert
        assertNotNull(result);
        assertEquals(address, result.getAddress());
        assertEquals(pizzaId, result.getPizza().getId());
    }

    @Test
    // n test for at tilføje en delivery  - kaster en exception når den ikke kan finde pizzaer
    void addDelivery_shouldThrowExceptionWhenPizzaNotFound() {
        // Arrange
        Long pizzaId = 1L;
        String address = "Nørrebrogade 45, 2200 København";

        Mockito.when(pizzaRepository.findById(pizzaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> deliveryService.addDelivery(pizzaId, address));
    }

    @Test
    // en test for at schedule en delivery  -  tilføjer drone til en delivery
    void scheduleDelivery_shouldAssignDroneToDelivery() {
        // Arrange
        Long deliveryId = 1L;
        Long droneId = 2L;

        Delivery mockDelivery = new Delivery();
        mockDelivery.setId(deliveryId);

        Drone mockDrone = new Drone();
        mockDrone.setId(droneId);
        mockDrone.setStatus(DroneStatus.IN_SERVICE);

        Mockito.when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(mockDelivery));
        Mockito.when(droneRepository.findById(droneId)).thenReturn(Optional.of(mockDrone));
        Mockito.when(deliveryRepository.save(Mockito.any(Delivery.class))).thenReturn(mockDelivery);

        // Act
        Delivery result = deliveryService.scheduleDelivery(deliveryId, droneId);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getDrone());
        assertEquals(droneId, result.getDrone().getId());
    }

    @Test
    // en test for at schedule en delivery  -  kaster en Exception når der ikke er en drone tilgængelig
    void scheduleDelivery_shouldThrowExceptionWhenDroneNotAvailable() {
        // Arrange
        Long deliveryId = 1L;
        Long droneId = 2L;

        Delivery mockDelivery = new Delivery();
        mockDelivery.setId(deliveryId);

        Drone mockDrone = new Drone();
        mockDrone.setId(droneId);
        mockDrone.setStatus(DroneStatus.OUT_OF_SERVICE); // Ikke "IN_SERVICE"

        Mockito.when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(mockDelivery));
        Mockito.when(droneRepository.findById(droneId)).thenReturn(Optional.of(mockDrone));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> deliveryService.scheduleDelivery(deliveryId, droneId));
    }

    @Test
    // en test for at færdiggøre en delivery
    void finishDelivery_shouldMarkDeliveryAsCompleted() {
        // Arrange
        Long deliveryId = 1L;

        Delivery mockDelivery = new Delivery();
        mockDelivery.setId(deliveryId);
        mockDelivery.setDrone(new Drone());

        Mockito.when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(mockDelivery));
        Mockito.when(deliveryRepository.save(Mockito.any(Delivery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Delivery result = deliveryService.finishDelivery(deliveryId);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getActualDeliveryTime());
    }


    @Test
    // en test for at færdiggøre en delivery - kaster en Exception når der ikke er en drone tildelt.
    void finishDelivery_shouldThrowExceptionWhenNoDroneAssigned() {
        // Arrange
        Long deliveryId = 1L;

        Delivery mockDelivery = new Delivery();
        mockDelivery.setId(deliveryId);

        Mockito.when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(mockDelivery));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> deliveryService.finishDelivery(deliveryId));
    }
}