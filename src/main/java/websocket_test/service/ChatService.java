package websocket_test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import websocket_test.DTO.ChatRoomDto;
import websocket_test.entity.ChatRoomEntity;
import websocket_test.entity.MessageEntity;
import websocket_test.repository.ChatRoomRepository;
import websocket_test.repository.MessageRepository;
import websocket_test.repository.TrainerRepository;
import websocket_test.repository.UserRepository;
import websocket_test.DTO.Token;

@Service
public class ChatService {

	private final ChatRoomRepository chatRoomRepository;
	private final MessageRepository messageRepository;
	private final TrainerRepository trainerRepository;
	private final UserRepository userRepository;
	private final ObjectMapper objectMapper;

	public ChatService(ChatRoomRepository chatRoomRepository, MessageRepository messageRepository,
		TrainerRepository trainerRepository, UserRepository userRepository, ObjectMapper objectMapper) {
		this.chatRoomRepository = chatRoomRepository;
		this.messageRepository = messageRepository;
		this.trainerRepository = trainerRepository;
		this.userRepository = userRepository;
		this.objectMapper = objectMapper;
	}

	public ChatRoomEntity createRoom(String name, Token token) {
		String roomId = UUID.randomUUID().toString();
		ChatRoomEntity chatRoom = new ChatRoomEntity(roomId, null, null, name);

		if ("TRAINER".equals(token.role())) {
			chatRoom.setTrainerId(token.id());
		} else if ("USER".equals(token.role())) {
			chatRoom.setUserId(token.id());
		}

		return chatRoomRepository.save(chatRoom);
	}

	public void processMessage(String roomId, Token token, String message) {
		Optional<ChatRoomEntity> chatRoom = chatRoomRepository.findById(roomId);
		if (chatRoom.isPresent()) {
			String sender = resolveSenderName(token);
			saveMessage(roomId, sender, message);
		} else {
			throw new IllegalArgumentException("채팅방을 찾을 수 없습니다.");
		}
	}

	public void saveMessage(String roomId, String sender, String message) {
		MessageEntity messageEntity = new MessageEntity(
			UUID.randomUUID().toString(), roomId, sender, LocalDateTime.now(), message
		);
		messageRepository.save(messageEntity);
	}

	private String resolveSenderName(Token token) {
		if ("TRAINER".equals(token.role())) {
			return trainerRepository.findById(token.id())
				.map(trainer -> trainer.getName() + " 트레이너")
				.orElseThrow(() -> new IllegalArgumentException("트레이너를 찾을 수 없습니다."));
		} else if ("USER".equals(token.role())) {
			return userRepository.findById(token.id())
				.map(user -> user.getName() + " 회원")
				.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
		}
		throw new IllegalArgumentException("잘못된 역할 정보입니다.");
	}

	public ChatRoomDto findRoomById(String roomId) {
		Optional<ChatRoomEntity> chatRoomEntity = chatRoomRepository.findById(roomId);

		if (chatRoomEntity.isPresent()) {
			ChatRoomEntity entity = chatRoomEntity.get();
			return new ChatRoomDto(entity.getId(), entity.getRoomName());
		}

		return null;
	}

	public <T> String convertMessageToJson(T message) {
		try {
			return objectMapper.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}
}
