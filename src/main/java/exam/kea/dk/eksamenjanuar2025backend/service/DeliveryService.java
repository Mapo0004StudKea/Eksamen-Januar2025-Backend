package exam.kea.dk.eksamenjanuar2025backend.service;

import exam.kea.dk.eksamenjanuar2025backend.model.Delivery;
import exam.kea.dk.eksamenjanuar2025backend.model.Drone;
import exam.kea.dk.eksamenjanuar2025backend.model.DroneStatus;
import exam.kea.dk.eksamenjanuar2025backend.model.Pizza;
import exam.kea.dk.eksamenjanuar2025backend.repository.DeliveryRepository;
import exam.kea.dk.eksamenjanuar2025backend.repository.DroneRepository;
import exam.kea.dk.eksamenjanuar2025backend.repository.PizzaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final PizzaRepository pizzaRepository;
    private final DroneRepository droneRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, PizzaRepository pizzaRepository, DroneRepository droneRepository) {
        this.deliveryRepository = deliveryRepository;
        this.pizzaRepository = pizzaRepository;
        this.droneRepository = droneRepository;
    }

    public List<Delivery> getPendingDeliveries() {
        // Hent alle leveringer, der ikke er afsluttet (actualDeliveryTime er null)
        return deliveryRepository.findByActualDeliveryTimeIsNullOrderByExpectedDeliveryTimeAsc();
    }

    public Delivery addDelivery(Long pizzaId) {
        // Find pizza fra databasen
        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pizza ID"));

        // Opret ny levering
        Delivery delivery = new Delivery();
        delivery.setPizza(pizza);
        delivery.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));

        return deliveryRepository.save(delivery);
    }

    public List<Delivery> getUnassignedDeliveries() {
        // Hent alle leveringer uden en tildelt drone
        return deliveryRepository.findByDroneIsNull();
    }

    public Delivery scheduleDelivery(Long deliveryId, Long droneId) {
        // Find leveringen
        Optional<Delivery> optionalDelivery = deliveryRepository.findById(deliveryId);
        if (optionalDelivery.isEmpty()) {
            throw new IllegalArgumentException("Invalid delivery ID");
        }
        Delivery delivery = optionalDelivery.get();

        // Tjek, om leveringen allerede har en drone
        if (delivery.getDrone() != null) {
            throw new IllegalStateException("Delivery already has a drone assigned");
        }

        // Find en drone (enten ud fra ID eller tilfældig)
        Drone drone;
        if (droneId != null) {
            Optional<Drone> optionalDrone = droneRepository.findById(droneId);
            if (optionalDrone.isEmpty()) {
                throw new IllegalArgumentException("Invalid drone ID");
            }
            drone = optionalDrone.get();
        } else {
            Optional<Drone> optionalDrone = droneRepository.findFirstByStatus(DroneStatus.IN_SERVICE);
            if (optionalDrone.isEmpty()) {
                throw new IllegalStateException("No drones available");
            }
            drone = optionalDrone.get();
        }

        // Tjek, om dronen er "i drift"
        if (drone.getStatus() != DroneStatus.IN_SERVICE) {
            throw new IllegalStateException("Drone is not operational");
        }

        // Tildel dronen til leveringen
        delivery.setDrone(drone);

        // Gem ændringerne i databasen
        return deliveryRepository.save(delivery);
    }
}
