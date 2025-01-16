package exam.kea.dk.eksamenjanuar2025backend.repository;

import exam.kea.dk.eksamenjanuar2025backend.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
}
