package websocket_test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import websocket_test.entity.MessageEntity;

public interface MessageRepository extends MongoRepository<MessageEntity, String> {
}
