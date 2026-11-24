package jp.ne.yonem.restful.infrastructure.lesson2.memento;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/** スナップショット履歴を管理する管理者（Caretaker）です。 */
public class HistoryCaretaker {

  private final Deque<DocumentSnapshot> history = new ArrayDeque<>();

  public void push(DocumentSnapshot snapshot) {
    history.push(Objects.requireNonNull(snapshot, "snapshot must not be null"));
  }

  public DocumentSnapshot pop() {
    if (history.isEmpty()) {
      throw new IllegalStateException("No snapshot available to restore");
    }
    return history.pop();
  }

  public boolean hasHistory() {
    return !history.isEmpty();
  }
}
