package websocket_test.entity;

import jakarta.persistence.Id;
import java.util.UUID;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import websocket_test.DTO.ChatMessage;

@Document(collection = "messages")
public class MessageEntity {

    @Id
    private String messageId;

    private String roomId;
    private String sender;
    private LocalDateTime sendTime;
    private String message;

    public MessageEntity(String messageId, String roomId, String sender, LocalDateTime sendTime, String message) {
        this.messageId = messageId;
        this.roomId = roomId;
        this.sender = sender;
        this.sendTime = sendTime;
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getSender() {
        return sender;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public String getMessage() {
        return message;
    }

    public ChatMessage toDto() {
        return ChatMessage.builder()
            .roomId(this.roomId)
            .sender(this.sender)
            .message(this.message)
            .build();
    }

    public static MessageEntity toEntity(ChatMessage chatMessage) {
        return new MessageEntity(
            UUID.randomUUID().toString(),
            chatMessage.getRoomId(),
            chatMessage.getSender(),
            LocalDateTime.now(),
            chatMessage.getMessage()
        );
    }
}
