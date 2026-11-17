package jp.ne.yonem.restful.infrastructure.lesson2.mediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** チャットルームを表す具象メディエータークラスです。 */
public class ChatRoomMediator implements ChatMediator {

  private final List<ChatUser> users = new ArrayList<>();

  @Override
  public void addUser(ChatUser user) {
    users.add(Objects.requireNonNull(user, "user must not be null"));
  }

  @Override
  public void sendMessage(String message, ChatUser sender) {
    var safeMessage = Objects.requireNonNull(message, "message must not be null");
    var safeSender = Objects.requireNonNull(sender, "sender must not be null");

    // 送信者以外の参加者にメッセージを配信
    for (ChatUser user : users) {
      if (user != safeSender) {
        user.receive("[%s says]: %s".formatted(safeSender.getName(), safeMessage));
      }
    }
  }
}
