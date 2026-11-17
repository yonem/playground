package jp.ne.yonem.restful.infrastructure.lesson2.mediator;

import lombok.Getter;

/** チャット参加者（Colleague）の抽象クラスです。 */
public abstract class ChatUser {
  protected final ChatMediator mediator;
  @Getter protected final String name;

  protected ChatUser(ChatMediator mediator, String name) {
    this.mediator = java.util.Objects.requireNonNull(mediator, "mediator must not be null");
    this.name = java.util.Objects.requireNonNull(name, "name must not be null");
  }

  public abstract void send(String message);

  public abstract void receive(String message);
}
