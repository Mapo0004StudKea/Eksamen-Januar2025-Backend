package exam.kea.dk.eksamenjanuar2025backend.service;

import exam.kea.dk.eksamenjanuar2025backend.model.Delivery;
import exam.kea.dk.eksamenjanuar2025backend.model.Pizza;
import exam.kea.dk.eksamenjanuar2025backend.repository.DeliveryRepository;
import exam.kea.dk.eksamenjanuar2025backend.repository.PizzaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final PizzaRepository pizzaRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, PizzaRepository pizzaRepository) {
        this.deliveryRepository = deliveryRepository;
        this.pizzaRepository = pizzaRepository;
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
        delivery.setPizza(pizza); // Sørg for at tilknytte pizzaen her
        delivery.setExpectedDeliveryTime(LocalDateTime.now().plusMinutes(30));

        return deliveryRepository.save(delivery);
    }
}
