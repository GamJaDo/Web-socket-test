package websocket_test.DTO;

import java.util.HashSet;
import java.util.Set;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import com.fasterxml.jackson.annotation.JsonIgnore;
import websocket_test.service.ChatService;

public class ChatRoomDto {

	private final String roomId;
	private final String name;

	@JsonIgnore
	private final Set<WebSocketSession> sessions = new HashSet<>();

	public ChatRoomDto(String roomId, String name) {
		this.roomId = roomId;
		this.name = name;
	}

	public String getRoomId() {
		return roomId;
	}

	public String getName() {
		return name;
	}

	public Set<WebSocketSession> getSessions() {
		return sessions;
	}

	public void addSession(WebSocketSession session) {
		sessions.add(session);
	}

	public <T> void sendMessage(T message, ChatService chatService) {
		sessions.forEach(session -> {
			try {
				session.sendMessage(new TextMessage(chatService.convertMessageToJson(message)));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
