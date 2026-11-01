package jp.ne.yonem.restful.infrastructure.lesson2.command;

import java.util.Objects;

/** 照明を点灯するコマンド */
public class LightOnCommand implements Command {
  private final LightReceiver light;

  public LightOnCommand(LightReceiver light) {
    this.light = Objects.requireNonNull(light, "light must not be null");
  }

  @Override
  public String executeCommand() {
    return light.turnOn();
  }
}
