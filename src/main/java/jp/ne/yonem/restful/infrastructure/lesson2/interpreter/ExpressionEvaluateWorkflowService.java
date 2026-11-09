package jp.ne.yonem.restful.infrastructure.lesson2.interpreter;

import java.util.Objects;
import org.springframework.stereotype.Service;

/** Interpreterパターンを利用して条件式の評価を行うサービスです。 */
@Service
public class ExpressionEvaluateWorkflowService {

  /**
   * 指定された条件式（Expression）に従ってテキストを評価し、結果を文字列で返します。
   *
   * @param expression 評価する構文木
   * @param text 評価対象のテキスト
   * @return 評価結果メッセージ
   */
  public String execute(Expression expression, String text) {
    var safeExpr = Objects.requireNonNull(expression, "expression must not be null");
    var safeText = Objects.requireNonNull(text, "text must not be null");

    boolean matched = safeExpr.interpret(safeText);
    return matched ? "MATCHED: Condition satisfied" : "UNMATCHED: Condition not satisfied";
  }
}
