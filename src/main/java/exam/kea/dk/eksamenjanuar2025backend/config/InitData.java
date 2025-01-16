package exam.kea.dk.eksamenjanuar2025backend.config;

import exam.kea.dk.eksamenjanuar2025backend.model.Drone;
import exam.kea.dk.eksamenjanuar2025backend.model.DroneStatus;
import exam.kea.dk.eksamenjanuar2025backend.model.Pizza;
import exam.kea.dk.eksamenjanuar2025backend.model.Station;
import exam.kea.dk.eksamenjanuar2025backend.repository.DroneRepository;
import exam.kea.dk.eksamenjanuar2025backend.repository.PizzaRepository;
import exam.kea.dk.eksamenjanuar2025backend.repository.StationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static exam.kea.dk.eksamenjanuar2025backend.model.DroneStatus.IN_SERVICE;

@Component
public class InitData implements CommandLineRunner {

    private final StationRepository stationRepository;
    private final PizzaRepository PizzaRepository;
    private final DroneRepository DroneRepository;

    public InitData(StationRepository stationRepository, PizzaRepository pizzaRepository, DroneRepository droneRepository) {
        this.stationRepository = stationRepository;
        this.PizzaRepository = pizzaRepository;
        this.DroneRepository = droneRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (stationRepository.count() == 0) {
            Station station1 = new Station(55.41, 12.34);
            Station station2 = new Station(85.15, 36.35);
            Station station3 = new Station(15.85, 12.36);
            stationRepository.saveAll(List.of(station1, station2, station3));

            if (DroneRepository.count() == 0) {
                Drone drone1 = new Drone();
                drone1.setUuid(UUID.randomUUID().toString());
                drone1.setStatus(DroneStatus.IN_SERVICE);
                drone1.setStation(station1);

                Drone drone2 = new Drone();
                drone2.setUuid(UUID.randomUUID().toString());
                drone2.setStatus(DroneStatus.OUT_OF_SERVICE);
                drone2.setStation(station2);

                Drone drone3 = new Drone();
                drone3.setUuid(UUID.randomUUID().toString());
                drone3.setStatus(DroneStatus.PHASED_OUT);
                drone3.setStation(station3);

                DroneRepository.saveAll(List.of(drone1, drone2, drone3));

                if (PizzaRepository.count() == 0) {
                    PizzaRepository.saveAll(List.of(
                            new Pizza("Margherita", 72,"Med tomat og mozzarellaost"),
                            new Pizza("Pepperoni", 82,"Med tomat, mozzarellaost og pepperoni"),
                            new Pizza("Hawaii", 87,"Med tomat, mozzarellaost, skinke og ananas"),
                            new Pizza("Vesuvio", 82,"Med tomat, mozzarellaost og skinke"),
                            new Pizza("O Solo Mio", 94,"Med tomat, mozzarellaost, skinke og pepperoni")
                    ));
                }
            }
        }
    }
}
