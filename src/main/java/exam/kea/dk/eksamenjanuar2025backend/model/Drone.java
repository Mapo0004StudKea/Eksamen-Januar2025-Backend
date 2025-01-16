package exam.kea.dk.eksamenjanuar2025backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uuid;

    @Enumerated(EnumType.STRING)
    private DroneStatus status;

    @ManyToOne
    private Station station;

    @OneToMany(mappedBy = "drone")
    @JsonBackReference
    private List<Delivery> deliveries;

    public Drone(String uuid, DroneStatus status, Station station) {
        this.uuid = uuid;
        this.status = status;
        this.station = station;
    }
}
