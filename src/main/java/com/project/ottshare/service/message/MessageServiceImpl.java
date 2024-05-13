package com.project.ottshare.service.message;

import com.project.ottshare.dto.ottShareRoomDto.MessageRequest;
import com.project.ottshare.dto.ottShareRoomDto.MessageResponse;
import com.project.ottshare.dto.ottShareRoomDto.OttShareRoomResponse;
import com.project.ottshare.dto.sharingUserDto.OttRoomMemberResponse;
import com.project.ottshare.entity.Message;
import com.project.ottshare.entity.OttShareRoom;
import com.project.ottshare.entity.SharingUser;
import com.project.ottshare.exception.OttSharingRoomNotFoundException;
import com.project.ottshare.exception.SharingUserNotFoundException;
import com.project.ottshare.repository.MessageRepository;
import com.project.ottshare.repository.OttShareRoomRepository;
import com.project.ottshare.repository.SharingUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;
    private final SharingUserRepository sharingUserRepository;
    private final OttShareRoomRepository ottShareRoomRepository;

    @Override
    @Transactional
    public Long save(MessageRequest messageRequest) {
        Message entity = messageRequest.toEntity();
        messageRepository.save(entity);

        return entity.getId();
    }

    @Override
    public Page<MessageResponse> getMessages(Long roomId, Long sharingUserId, Pageable pageable) {
        Page<Message> messages = messageRepository.findAllByOttShareRoomIdAndSharingUserId(roomId, sharingUserId, pageable);

        OttShareRoom ottShareRoom = ottShareRoomRepository.findById(roomId)
                .orElseThrow(() -> new OttSharingRoomNotFoundException(roomId));

        SharingUser sharingUser = sharingUserRepository.findById(sharingUserId)
                .orElseThrow(() -> new SharingUserNotFoundException(sharingUserId));

        OttShareRoomResponse ottShareRoomResponse = new OttShareRoomResponse(ottShareRoom);
        OttRoomMemberResponse ottRoomMemberResponse = new OttRoomMemberResponse(sharingUser);


        return messages.map(this::convertToDto);
    }

    private MessageResponse convertToDto(Message message) {
        // 예제 구현, 실제 구현에서는 메시지 엔티티를 DTO로 변환하는 로직 추가
        return new MessageResponse(message.getId(), ottShareRoomResponse, message.getSharingUser(), message.getMessage();
    }

}
