package jp.ne.yonem.restful.infrastructure.lesson2.mediator;

import java.util.Objects;
import lombok.Getter;

/** 標準的なチャットユーザーを表す具象参加者クラスです。 */
@Getter
public class StandardChatUser extends ChatUser {

  private String lastReceivedMessage = "";

  public StandardChatUser(ChatMediator mediator, String name) {
    super(mediator, name);
  }

  @Override
  public void send(String message) {
    var safeMsg = Objects.requireNonNull(message, "message must not be null");
    mediator.sendMessage(safeMsg, this);
  }

  @Override
  public void receive(String message) {
    this.lastReceivedMessage = Objects.requireNonNull(message, "message must not be null");
  }
}
