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

    @GetMapping("/getAll")
    public List<Delivery> getAllDeliveries() {
        return deliveryService.getAllDeliveries();
    }

    @PostMapping("/add")
    public ResponseEntity<Delivery> addDelivery(@RequestParam Long pizzaId, @RequestParam String address) {
        try {
            Delivery delivery = deliveryService.addDelivery(pizzaId, address);
            // Returner HTTP 201 Created
            return ResponseEntity.status(HttpStatus.CREATED).body(delivery);
        } catch (IllegalArgumentException e) {
            // Returner HTTP 400 Bad Request
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            // Returner HTTP 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/queue")
    public ResponseEntity<List<Delivery>> getUnassignedDeliveries() {
        List<Delivery> deliveries = deliveryService.getUnassignedDeliveries();
        if (deliveries.isEmpty()) {
            // Returner HTTP 204 No Content, hvis listen er tom
            return ResponseEntity.noContent().build();
        }
        // Returner HTTP 200 OK
        return ResponseEntity.ok(deliveries);
    }

    @PostMapping("/schedule")
    public ResponseEntity<Delivery> scheduleDelivery(@RequestParam Long deliveryId, @RequestParam(required = false) Long droneId) {
        try {
            Delivery delivery = deliveryService.scheduleDelivery(deliveryId, droneId);
            // Returner HTTP 200 OK
            return ResponseEntity.ok(delivery);
        } catch (IllegalArgumentException e) {
            // Returner HTTP 400 Bad Request
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            // Returner HTTP 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            // Returner HTTP 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/finish")
    public ResponseEntity<Delivery> finishDelivery(@RequestParam Long deliveryId) {
        try {
            Delivery delivery = deliveryService.finishDelivery(deliveryId);
            // Returner HTTP 200 OK
            return ResponseEntity.ok(delivery);
        } catch (IllegalArgumentException e) {
            // Returner HTTP 400 Bad Request
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            // Returner HTTP 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } catch (Exception e) {
            // Returner HTTP 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
