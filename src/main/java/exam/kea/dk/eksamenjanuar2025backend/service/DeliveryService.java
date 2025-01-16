package exam.kea.dk.eksamenjanuar2025backend.service;

import exam.kea.dk.eksamenjanuar2025backend.model.Delivery;
import exam.kea.dk.eksamenjanuar2025backend.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public List<Delivery> getPendingDeliveries() {
        // Hent alle leveringer, der ikke er afsluttet (actualDeliveryTime er null)
        return deliveryRepository.findByActualDeliveryTimeIsNullOrderByExpectedDeliveryTimeAsc();
    }
}
