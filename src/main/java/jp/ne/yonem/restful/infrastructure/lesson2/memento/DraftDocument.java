package jp.ne.yonem.restful.infrastructure.lesson2.memento;

import java.util.Objects;
import lombok.Getter;

/** 状態を持ち、スナップショットの作成と復元を行う作成者（Originator）です。 */
@Getter
public class DraftDocument {

  private String content;

  public DraftDocument(String initialContent) {
    this.content = Objects.requireNonNull(initialContent, "initialContent must not be null");
  }

  public void edit(String newContent) {
    this.content = Objects.requireNonNull(newContent, "newContent must not be null");
  }

  public DocumentSnapshot save() {
    return new DocumentSnapshot(this.content);
  }

  public void restore(DocumentSnapshot snapshot) {
    var safeSnapshot = Objects.requireNonNull(snapshot, "snapshot must not be null");
    this.content = safeSnapshot.content();
  }
}
