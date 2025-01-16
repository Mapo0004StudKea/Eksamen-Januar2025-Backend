package exam.kea.dk.eksamenjanuar2025backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String address;
    private LocalDateTime expectedDeliveryTime;
    private LocalDateTime actualDeliveryTime;

    @ManyToOne
    @JsonBackReference
    private Pizza pizza;

    @ManyToOne
    private Drone drone;

    public Delivery(String address, LocalDateTime expectedDeliveryTime, LocalDateTime actualDeliveryTime, Pizza pizza, Drone drone) {
        this.address = address;
        this.expectedDeliveryTime = expectedDeliveryTime;
        this.actualDeliveryTime = actualDeliveryTime;
        this.pizza = pizza;
        this.drone = drone;
    }
}
