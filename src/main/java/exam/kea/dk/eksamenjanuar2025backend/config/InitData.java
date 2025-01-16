package exam.kea.dk.eksamenjanuar2025backend.config;

import exam.kea.dk.eksamenjanuar2025backend.model.*;
import exam.kea.dk.eksamenjanuar2025backend.repository.DeliveryRepository;
import exam.kea.dk.eksamenjanuar2025backend.repository.DroneRepository;
import exam.kea.dk.eksamenjanuar2025backend.repository.PizzaRepository;
import exam.kea.dk.eksamenjanuar2025backend.repository.StationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class InitData implements CommandLineRunner {

    private final StationRepository stationRepository;
    private final PizzaRepository PizzaRepository;
    private final DroneRepository DroneRepository;
    private final DeliveryRepository DeliveryRepository;

    public InitData(StationRepository stationRepository, PizzaRepository pizzaRepository, DroneRepository droneRepository, DeliveryRepository deliveryRepository) {
        this.stationRepository = stationRepository;
        this.PizzaRepository = pizzaRepository;
        this.DroneRepository = droneRepository;
        this.DeliveryRepository = deliveryRepository;
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
                    Pizza pizza1 = new Pizza("Margherita", 72, "Med tomat og mozzarellaost");
                    Pizza pizza2 = new Pizza("Pepperoni", 82, "Med tomat, mozzarellaost og pepperoni");
                    Pizza pizza3 = new Pizza("Hawaii", 87, "Med tomat, mozzarellaost, skinke og ananas");
                    Pizza pizza4 = new Pizza("Vesuvio", 82, "Med tomat, mozzarellaost og skinke");
                    Pizza pizza5 = new Pizza("O Solo Mio", 94, "Med tomat, mozzarellaost, skinke og pepperoni");

                    PizzaRepository.saveAll(List.of(pizza1, pizza2, pizza3, pizza4, pizza5));

                    if (DeliveryRepository.count() == 0) {
                        LocalDateTime expectedDeliveryTime = LocalDateTime.of(2025, 1, 16, 18, 0); // Eksempel med tidspunkt 18:00
                        Delivery delivery1 = new Delivery("Test Street 1000", expectedDeliveryTime, null, pizza1, drone1);
                        DeliveryRepository.save(delivery1);
                    }
                }
            }
        }
    }
}
