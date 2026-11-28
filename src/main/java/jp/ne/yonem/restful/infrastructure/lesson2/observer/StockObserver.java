package jp.ne.yonem.restful.infrastructure.lesson2.observer;

/** 在庫変動イベントを受け取る監視者（Observer）インターフェースです。 */
public interface StockObserver {
  String onStockChanged(StockChangedEvent event);
}
