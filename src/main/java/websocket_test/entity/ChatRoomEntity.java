package websocket_test.entity;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import websocket_test.DTO.ChatRoomDto;

@Document(collection = "chatRooms")
public class ChatRoomEntity {

    @Id
    private String id;

    private Long trainerId;
    private Long userId;
    private String roomName;

    public ChatRoomEntity(String id, Long trainerId, Long userId, String roomName) {
        this.id = id;
        this.trainerId = trainerId;
        this.userId = userId;
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getId() {
        return id;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ChatRoomDto toDto() {
        return new ChatRoomDto(this.id, this.roomName);
    }

    public static ChatRoomEntity toEntity(ChatRoomDto chatRoomDto, Long trainerId, Long userId) {
        return new ChatRoomEntity(chatRoomDto.getRoomId(), trainerId, userId,
            chatRoomDto.getName());
    }
}
