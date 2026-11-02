package jp.ne.yonem.restful.infrastructure.lesson2.command;

import java.util.Objects;

/** 照明を消灯するコマンド */
public class LightOffCommand implements Command {
  private final LightReceiver light;

  public LightOffCommand(LightReceiver light) {
    this.light = Objects.requireNonNull(light, "light must not be null");
  }

  @Override
  public String executeCommand() {
    return light.turnOff();
  }
}
