package jp.ne.yonem.restful.infrastructure.lesson2.observer;

import java.util.Objects;

/** 在庫変動を表す不変イベント（record）です。 */
public record StockChangedEvent(String itemCode, int remainingQuantity) {
  public StockChangedEvent {
    Objects.requireNonNull(itemCode, "itemCode must not be null");
  }
}
