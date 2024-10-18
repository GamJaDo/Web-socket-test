package websocket_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import websocket_test.entity.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}
