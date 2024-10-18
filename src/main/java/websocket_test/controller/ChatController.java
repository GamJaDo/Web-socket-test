package websocket_test.controller;

import org.springframework.web.bind.annotation.*;
import websocket_test.entity.ChatRoomEntity;
import websocket_test.DTO.Token;
import websocket_test.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/create")
    public ChatRoomEntity createRoom(@RequestBody Token token) {
        return chatService.createRoom("채팅방1", token);
    }

    @PostMapping("/send")
    public void sendMessage(
        @RequestParam("roomId") String roomId,
        @RequestParam("role") String role,
        @RequestParam("id") Long id,
        @RequestBody String message
    ) {
        Token token = new Token(role, id);
        chatService.processMessage(roomId, token, message);
    }
}
