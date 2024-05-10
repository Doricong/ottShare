package com.project.ottshare.controller;

import com.project.ottshare.dto.ottShareRoomDto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;


@Controller
@Slf4j
public class MessageController {

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/messages/{roomId}")
    public Message handleChatMessage(Message message, @DestinationVariable Long roomId) throws InterruptedException {
        Thread.sleep(1000);
        log.info("message={}", message.getContent());
        // 메시지 처리 로직 (예: 저장, 로깅 등)
        return new Message(HtmlUtils.htmlEscape(message.getContent()));
    }
}
