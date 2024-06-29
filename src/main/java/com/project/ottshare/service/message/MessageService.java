package com.project.ottshare.service.message;

import com.project.ottshare.dto.ottShareRoomDto.MessageRequest;
import com.project.ottshare.dto.ottShareRoomDto.MessageResponse;
import org.springframework.data.domain.Page;

public interface MessageService {

    void createMessage(MessageRequest messageRequest);

    Page<MessageResponse> getMessages(Long roomId);
}
