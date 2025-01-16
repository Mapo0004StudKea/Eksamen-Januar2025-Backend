package exam.kea.dk.eksamenjanuar2025backend.config;

import exam.kea.dk.eksamenjanuar2025backend.model.Pizza;
import exam.kea.dk.eksamenjanuar2025backend.model.Station;
import exam.kea.dk.eksamenjanuar2025backend.repository.PizzaRepository;
import exam.kea.dk.eksamenjanuar2025backend.repository.StationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class InitData implements CommandLineRunner {

    private final StationRepository stationRepository;
    private final PizzaRepository PizzaRepository;

    public InitData(StationRepository stationRepository, PizzaRepository pizzaRepository) {
        this.stationRepository = stationRepository;
        this.PizzaRepository = pizzaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (stationRepository.count() == 0) {
            stationRepository.saveAll(List.of(
                    new Station(55.41, 12.34),
                    new Station(85.15, 36.35),
                    new Station(15.85, 12.36)
            ));

            PizzaRepository.saveAll(List.of(
                    new Pizza("Margherita", 72),
                    new Pizza("Pepperoni", 82),
                    new Pizza("Hawaii", 87),
                    new Pizza("Vesuvio", 82),
                    new Pizza("O Solo Mio", 94)
            ));
        }
    }
}
