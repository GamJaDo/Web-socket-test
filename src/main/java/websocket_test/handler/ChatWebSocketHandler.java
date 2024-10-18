package websocket_test.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import websocket_test.DTO.ChatMessage;
import websocket_test.DTO.ChatRoomDto;
import websocket_test.service.ChatService;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(ChatWebSocketHandler.class);
	private final ObjectMapper objectMapper;
	private final ChatService chatService;

	public ChatWebSocketHandler(ObjectMapper objectMapper, ChatService chatService) {
		this.objectMapper = objectMapper;
		this.chatService = chatService;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		logger.info("WebSocket 연결됨: " + session.getId());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		logger.info("수신된 메시지: " + payload);

		ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
		ChatRoomDto room = chatService.findRoomById(chatMessage.getRoomId());
		if (room != null) {
			chatService.saveMessage(chatMessage.getRoomId(), chatMessage.getSender(), chatMessage.getMessage());
			room.sendMessage(chatMessage, chatService);
		} else {
			logger.error("채팅방을 찾을 수 없습니다. roomId: " + chatMessage.getRoomId());
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
		logger.info("WebSocket 연결 종료됨: " + session.getId());
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) {
		logger.error("WebSocket 오류: " + session.getId() + ", 에러: " + exception.getMessage());
	}
}
