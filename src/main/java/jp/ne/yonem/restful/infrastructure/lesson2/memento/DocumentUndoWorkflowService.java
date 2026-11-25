package jp.ne.yonem.restful.infrastructure.lesson2.memento;

import java.util.Objects;
import org.springframework.stereotype.Service;

/** Mementoパターンを利用してドキュメント編集と復元を実行するサービスです。 */
@Service
public class DocumentUndoWorkflowService {

  /**
   * 現在の状態を保存して編集を行い、その後直前の状態へ復元（Undo）します。
   *
   * @param document 対象ドキュメント
   * @param caretaker 履歴管理者
   * @param nextContent 編集後の内容
   * @return 復元後のドキュメント内容
   */
  public String execute(DraftDocument document, HistoryCaretaker caretaker, String nextContent) {
    var safeDocument = Objects.requireNonNull(document, "document must not be null");
    var safeCaretaker = Objects.requireNonNull(caretaker, "caretaker must not be null");
    var safeNextContent = Objects.requireNonNull(nextContent, "nextContent must not be null");

    // 編集前の状態を保存
    safeCaretaker.push(safeDocument.save());

    // 内容を更新
    safeDocument.edit(safeNextContent);

    // 直前の状態へ復元
    safeDocument.restore(safeCaretaker.pop());

    return safeDocument.getContent();
  }
}
