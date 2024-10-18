package websocket_test.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import websocket_test.entity.ChatRoomEntity;

public interface ChatRoomRepository extends MongoRepository<ChatRoomEntity, String> {

}
