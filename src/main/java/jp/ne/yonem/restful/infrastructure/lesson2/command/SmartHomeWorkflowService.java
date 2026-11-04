package jp.ne.yonem.restful.infrastructure.lesson2.command;

import java.util.Objects;
import org.springframework.stereotype.Service;

/** Commandパターンを利用してスマート家電の操作ワークフローを実行するサービスです。 */
@Service
public class SmartHomeWorkflowService {

  /**
   * リモコンインベーカーにコマンドを設定し、実行結果を返却します。
   *
   * @param invoker 呼び出し者（リモコン）
   * @param command 実行するコマンド
   * @return 操作結果
   */
  public String execute(RemoteControlInvoker invoker, Command command) {
    var safeInvoker = Objects.requireNonNull(invoker, "invoker must not be null");
    var safeCommand = Objects.requireNonNull(command, "command must not be null");

    safeInvoker.setCommand(safeCommand);
    return safeInvoker.pressButton();
  }
}
