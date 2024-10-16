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

	private MessageType type;
	private String roomId;
	private String sender;
	private String message;

	public enum MessageType {
		ENTER, TALK
	}
}
