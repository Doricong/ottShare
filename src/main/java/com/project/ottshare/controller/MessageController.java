package com.project.ottshare.controller;

import com.project.ottshare.dto.ottShareRoomDto.MessageRequest;
import com.project.ottshare.dto.ottShareRoomDto.MessageResponse;
import com.project.ottshare.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/messages/{roomId}")
    public MessageRequest sendChatMessage(@Valid @RequestBody MessageRequest message,
                                            @DestinationVariable Long roomId) {
        log.info("message={}", message.getMessage());
        String escapedMessage = HtmlUtils.htmlEscape(message.getMessage());

        messageService.createMessage(message);

        return new MessageRequest(message.getOttShareRoom(), message.getOttRoomMemberResponse(), escapedMessage);
    }

    /**
     * 메시지 조회
     */
    @GetMapping("/{roomId}")
    public Page<MessageResponse> getMessages(@PathVariable Long roomId) {
        return messageService.getMessages(roomId);
    }

}
