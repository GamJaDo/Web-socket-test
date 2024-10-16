package websocket_test.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import websocket_test.DTO.ChatMessage;
import websocket_test.DTO.ChatRoom;
import websocket_test.service.ChatService;

@Slf4j
@RequiredArgsConstructor
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

	private final ObjectMapper objectMapper;
	private final ChatService chatService;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.info("웹소켓 연결된: " + session.getId());
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		log.info("메시지 수신: " + payload);

		// 수신된 메시지를 객체로 변환
		ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);

		// 밤 정보 가져오기
		ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
		if (room != null) {
			room.handleActions(session, chatMessage, chatService);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		log.info("웹소켓 연결 종료: " + session.getId());
	}
	
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.error("웹소켓 오료: " + session.getId() + "에러: " + exception.getMessage());
	}
}
