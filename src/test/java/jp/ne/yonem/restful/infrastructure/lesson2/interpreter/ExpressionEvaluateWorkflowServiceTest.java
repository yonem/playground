package jp.ne.yonem.restful.infrastructure.lesson2.interpreter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ExpressionEvaluateWorkflowServiceTest {

  @InjectMocks private ExpressionEvaluateWorkflowService sut;

  @Mock private Expression expression;

  @Nested
  class SuccessTests {

    @Test
    @DisplayName("正常系: モックのExpression経由で正しく評価結果が返ること")
    void test01() {
      when(expression.interpret("sample text")).thenReturn(true);

      var result = sut.execute(expression, "sample text");

      assertThat(result).isEqualTo("MATCHED: Condition satisfied");
      verify(expression, times(1)).interpret("sample text");
    }

    @Test
    @DisplayName("正常系: TerminalExpression（実体）で単語が含まれている場合にマッチすること")
    void test02() {
      Expression expr = new TerminalExpression("Java");

      var result = sut.execute(expr, "Learning Java 21");

      assertThat(result).isEqualTo("MATCHED: Condition satisfied");
    }

    @Test
    @DisplayName("正常系: TerminalExpression（実体）で単語が含まれていない場合にアンマッチになること")
    void test03() {
      Expression expr = new TerminalExpression("Python");

      var result = sut.execute(expr, "Learning Java 21");

      assertThat(result).isEqualTo("UNMATCHED: Condition not satisfied");
    }

    @Test
    @DisplayName("正常系: AndExpression（実体）を用いて両方のキーワードが含まれる場合にマッチすること")
    void test04() {
      // 「Java」 AND 「21」
      Expression expr =
          new AndExpression(new TerminalExpression("Java"), new TerminalExpression("21"));

      var result = sut.execute(expr, "Modern Java 21 features");

      assertThat(result).isEqualTo("MATCHED: Condition satisfied");
    }

    @Test
    @DisplayName("正常系: AndExpression（実体）で片方のキーワードが欠けている場合にアンマッチになること")
    void test05() {
      // 「Java」 AND 「Spring」 ("Spring" が含まれない)
      Expression expr =
          new AndExpression(new TerminalExpression("Java"), new TerminalExpression("Spring"));

      var result = sut.execute(expr, "Modern Java 21 features");

      assertThat(result).isEqualTo("UNMATCHED: Condition not satisfied");
    }
  }

  @Nested
  class ExceptionTests {

    @Test
    @DisplayName("異常系: 式（expression）がnullの場合、NullPointerExceptionが発生すること")
    void test01() {
      assertThatThrownBy(() -> sut.execute(null, "text"))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("expression must not be null");
    }

    @Test
    @DisplayName("異常系: 対象テキストがnullの場合、NullPointerExceptionが発生すること")
    void test02() {
      assertThatThrownBy(() -> sut.execute(expression, null))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("text must not be null");
    }

    @Test
    @DisplayName("異常系: TerminalExpression生成時に単語がnullの場合、NullPointerExceptionが発生すること")
    void test03() {
      assertThatThrownBy(() -> new TerminalExpression(null))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("word must not be null");
    }

    @Test
    @DisplayName("異常系: AndExpression生成時に片方の式がnullの場合、NullPointerExceptionが発生すること")
    void test04() {
      var expr = new TerminalExpression("Java");
      assertThatThrownBy(() -> new AndExpression(expr, null))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("expr2 must not be null");
    }
  }
}
