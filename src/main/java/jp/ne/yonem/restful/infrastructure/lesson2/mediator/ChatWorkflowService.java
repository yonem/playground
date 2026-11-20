package jp.ne.yonem.restful.infrastructure.lesson2.mediator;

import java.util.Objects;
import org.springframework.stereotype.Service;

/** Mediatorパターンを利用してチャットメッセージのやり取りを実行するサービスです。 */
@Service
public class ChatWorkflowService {

  /**
   * チャットルームにユーザーを参加させ、メッセージ送信と受信結果を処理します。
   *
   * @param mediator チャット仲介者
   * @param sender 送信ユーザー
   * @param receiver 受信ユーザー
   * @param message 送信メッセージ
   * @return 受信側が受け取ったメッセージ
   */
  public String execute(ChatMediator mediator, ChatUser sender, ChatUser receiver, String message) {
    var safeMediator = Objects.requireNonNull(mediator, "mediator must not be null");
    var safeSender = Objects.requireNonNull(sender, "sender must not be null");
    var safeReceiver = Objects.requireNonNull(receiver, "receiver must not be null");
    var safeMessage = Objects.requireNonNull(message, "message must not be null");

    safeMediator.addUser(safeSender);
    safeMediator.addUser(safeReceiver);

    safeSender.send(safeMessage);

    if (safeReceiver instanceof StandardChatUser stdReceiver) {
      return stdReceiver.getLastReceivedMessage();
    }
    return "Message broadcasted";
  }
}
