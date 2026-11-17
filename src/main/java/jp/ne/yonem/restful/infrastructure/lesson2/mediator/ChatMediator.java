package jp.ne.yonem.restful.infrastructure.lesson2.mediator;

/** チャット仲介者（Mediator）のインターフェースです。 */
public interface ChatMediator {
  void sendMessage(String message, ChatUser sender);

  void addUser(ChatUser user);
}
