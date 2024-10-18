package websocket_test.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ChatMessage {

	private MessageType type;  // 메시지 타입 (ENTER, TALK 등)
	private String roomId;     // 채팅방 ID
	private String sender;     // 메시지 발신자
	private String message;    // 메시지 내용

	public enum MessageType {
		ENTER, TALK
	}
}
