package jp.ne.yonem.restful.infrastructure.lesson2.command;

import java.util.Objects;

/** コマンドを受け取って実行を指示する呼び出し者（Invoker / リモコン）です。 */
public class RemoteControlInvoker {
  private Command command;

  public void setCommand(Command command) {
    this.command = Objects.requireNonNull(command, "command must not be null");
  }

  public String pressButton() {
    if (command == null) {
      return "No command set";
    }
    return command.executeCommand();
  }
}
