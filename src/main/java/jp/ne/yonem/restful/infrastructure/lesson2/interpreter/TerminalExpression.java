package jp.ne.yonem.restful.infrastructure.lesson2.interpreter;

/** 終端表現（Terminal Expression）: 指定された単語がテキストに含まれているかを評価します。 */
public final class TerminalExpression implements Expression {

  private final String word;

  public TerminalExpression(String word) {
    this.word = java.util.Objects.requireNonNull(word, "word must not be null");
  }

  @Override
  public boolean interpret(String contextText) {
    var safeText = java.util.Objects.requireNonNull(contextText, "contextText must not be null");
    return safeText.contains(word);
  }
}
