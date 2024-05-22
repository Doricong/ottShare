package com.project.ottshare.controller;

import com.project.ottshare.dto.ottShareRoomDto.MessageRequest;
import com.project.ottshare.dto.ottShareRoomDto.MessageResponse;
import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.sharingUserDto.OttRoomMemberResponse;
import com.project.ottshare.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;


@RestController
@Slf4j
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/messages/{roomId}")
    public MessageRequest handleChatMessage(MessageRequest message, @DestinationVariable Long roomId) throws InterruptedException {
        Thread.sleep(1000);
        log.info("message={}", message.getMessage());
        String escapedMessage = HtmlUtils.htmlEscape(message.getMessage());

        messageService.save(message);

        return new MessageRequest(message.getOttShareRoom(), message.getOttRoomMemberResponse(), escapedMessage);
    }

    /**
     * 메시지 조회
     */
    @GetMapping("/chat/{roomId}/messages")
    public Page<MessageResponse> getMessages(
            @PathVariable Long roomId) {

        log.info("/chat/{roomId}/messages 호출");

        return messageService.getMessages(roomId);
    }

}
