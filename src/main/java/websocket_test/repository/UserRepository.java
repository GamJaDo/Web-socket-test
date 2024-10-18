package websocket_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import websocket_test.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
