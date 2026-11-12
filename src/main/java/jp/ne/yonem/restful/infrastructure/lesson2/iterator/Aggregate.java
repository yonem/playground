package jp.ne.yonem.restful.infrastructure.lesson2.iterator;

/** イテレーターを生成する集約（Aggregate）インターフェースです。 */
public interface Aggregate<T> {
  Iterator<T> createIterator();
}
