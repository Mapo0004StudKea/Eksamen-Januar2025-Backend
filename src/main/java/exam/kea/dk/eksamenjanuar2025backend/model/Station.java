package exam.kea.dk.eksamenjanuar2025backend.model;

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
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;

    @OneToMany(mappedBy = "station")
    private List<Drone> drones;

    public Station(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
