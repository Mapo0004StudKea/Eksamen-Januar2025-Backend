package exam.kea.dk.eksamenjanuar2025backend.controller;

import exam.kea.dk.eksamenjanuar2025backend.model.Delivery;
import exam.kea.dk.eksamenjanuar2025backend.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Delivery> addDelivery(@RequestParam Long pizzaId, @RequestParam String address) {
        Delivery delivery = deliveryService.addDelivery(pizzaId, address);
        return new ResponseEntity<>(delivery, HttpStatus.CREATED);
    }

    @GetMapping("/queue")
    public ResponseEntity<List<Delivery>> getUnassignedDeliveries() {
        List<Delivery> deliveries = deliveryService.getUnassignedDeliveries();
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }

    @PostMapping("/schedule")
    public ResponseEntity<Delivery> scheduleDelivery(@RequestParam Long deliveryId, @RequestParam(required = false) Long droneId) {
        Delivery delivery = deliveryService.scheduleDelivery(deliveryId, droneId);
        return new ResponseEntity<>(delivery, HttpStatus.OK);
    }

    @PostMapping("/finish")
    public ResponseEntity<Delivery> finishDelivery(@RequestParam Long deliveryId) {
        Delivery delivery = deliveryService.finishDelivery(deliveryId);
        return new ResponseEntity<>(delivery, HttpStatus.OK);
    }
}
