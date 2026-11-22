package jp.ne.yonem.restful.infrastructure.lesson2.memento;

import java.util.Objects;

/** ドキュメントの状態を保持する不変スナップショット（Memento）です。 */
public record DocumentSnapshot(String content) {
  public DocumentSnapshot {
    Objects.requireNonNull(content, "content must not be null");
  }
}
