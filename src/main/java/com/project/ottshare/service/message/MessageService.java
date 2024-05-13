package com.project.ottshare.service.message;

import com.project.ottshare.dto.ottShareRoomDto.MessageRequest;
import com.project.ottshare.dto.ottShareRoomDto.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {

    Long save(MessageRequest messageRequest);

    Page<MessageResponse> getMessages(Long roomId);
}
