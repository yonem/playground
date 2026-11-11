package jp.ne.yonem.restful.infrastructure.lesson2.iterator;

/** 走査を行うイテレーターインターフェースです。 */
public interface Iterator<T> {
  boolean hasNext();

  T next();
}
