package jp.ne.yonem.restful.infrastructure.lesson2.interpreter;

/** 非終端表現（And Expression）: 2つの式が両方とも成立するか（AND）を評価します。 */
public final class AndExpression implements Expression {

  private final Expression expr1;
  private final Expression expr2;

  public AndExpression(Expression expr1, Expression expr2) {
    this.expr1 = java.util.Objects.requireNonNull(expr1, "expr1 must not be null");
    this.expr2 = java.util.Objects.requireNonNull(expr2, "expr2 must not be null");
  }

  @Override
  public boolean interpret(String contextText) {
    var safeText = java.util.Objects.requireNonNull(contextText, "contextText must not be null");
    return expr1.interpret(safeText) && expr2.interpret(safeText);
  }
}
