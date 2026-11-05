package jp.ne.yonem.restful.infrastructure.lesson2.interpreter;

/** 式（Expression）を表す抽象インターフェースです。 */
public interface Expression {
  // public sealed interface Expression permits TerminalExpression, AndExpression {
  boolean interpret(String contextText);
}
