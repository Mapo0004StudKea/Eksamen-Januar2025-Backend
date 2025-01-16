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
import java.util.Random;

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

    public Delivery addDelivery(Long pizzaId, String address) {
        // Find pizza fra databasen
        Pizza pizza = pizzaRepository.findById(pizzaId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid pizza ID"));

        // Valider adressen
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address must not be null or empty");
        }

        // Opret ny levering
        Delivery delivery = new Delivery();
        delivery.setPizza(pizza);
        delivery.setAddress(address.trim());
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
            // Hent alle droner med status "IN_SERVICE"
            List<Drone> availableDrones = droneRepository.findAllByStatus(DroneStatus.IN_SERVICE);
            if (availableDrones.isEmpty()) {
                throw new IllegalStateException("No drones available");
            }

            // Vælg en tilfældig drone
            int randomIndex = new Random().nextInt(availableDrones.size());
            drone = availableDrones.get(randomIndex);
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

    public Delivery finishDelivery(Long deliveryId) {
        // Find leveringen
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid delivery ID"));

        // Tjek, om leveringen har en drone
        if (delivery.getDrone() == null) {
            throw new IllegalStateException("Delivery has no drone assigned");
        }

        // Markér leveringen som afsluttet med nuværende tidspunkt
        delivery.setActualDeliveryTime(LocalDateTime.now());
        return deliveryRepository.save(delivery);
    }
}
