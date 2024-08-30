import 'chatMember.dart';
import 'chatRoom.dart';

class MessageRequest {

  final ChatRoom chatRoom;
  final ChatMember writer;
  final String content;

  MessageRequest({
    required this.chatRoom,
    required this.writer,
    required this.content,
  });

  Map<String, dynamic> toJson() {

    return {
      'ottShareRoom': this.chatRoom.toJson(),
      'ottRoomMemberResponse': this.writer.toJson(),
      'message': content,
    };
  }
}