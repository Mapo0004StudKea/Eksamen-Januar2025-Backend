package exam.kea.dk.eksamenjanuar2025backend.repository;

import exam.kea.dk.eksamenjanuar2025backend.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByActualDeliveryTimeIsNullOrderByExpectedDeliveryTimeAsc();
}
