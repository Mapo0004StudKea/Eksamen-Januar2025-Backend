package exam.kea.dk.eksamenjanuar2025backend.controller;

import exam.kea.dk.eksamenjanuar2025backend.model.Delivery;
import exam.kea.dk.eksamenjanuar2025backend.service.DeliveryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public List<Delivery> getPendingDeliveries() {
        // Returner alle leveringer, der endnu ikke er afsluttet
        return deliveryService.getPendingDeliveries();
    }

    @PostMapping("/add")
    public Delivery addDelivery(@RequestParam Long pizzaId, @RequestParam String address) {
        return deliveryService.addDelivery(pizzaId, address);
    }

    @GetMapping("/queue")
    public List<Delivery> getUnassignedDeliveries() {
        // Returner alle leveringer, der mangler en drone
        return deliveryService.getUnassignedDeliveries();
    }

    @PostMapping("/schedule")
    public Delivery scheduleDelivery(@RequestParam Long deliveryId, @RequestParam(required = false) Long droneId) {
        // Tildel en drone til en levering
        return deliveryService.scheduleDelivery(deliveryId, droneId);
    }

    @PostMapping("/finish")
    public Delivery finishDelivery(@RequestParam Long deliveryId) {
        // Markér en levering som afsluttet
        return deliveryService.finishDelivery(deliveryId);
    }
}
