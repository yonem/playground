package jp.ne.yonem.restful.infrastructure.lesson2.command;

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
class SmartHomeWorkflowServiceTest {

  @InjectMocks private SmartHomeWorkflowService sut;

  @Mock private RemoteControlInvoker invoker;

  @Mock private Command command;

  @Nested
  class SuccessTests {

    @Test
    @DisplayName("正常系: モックインベーカーとコマンド経由で正しく実行結果が返ること")
    void test01() {
      when(invoker.pressButton()).thenReturn("Executed");

      var result = sut.execute(invoker, command);

      assertThat(result).isEqualTo("Executed");
      verify(invoker, times(1)).setCommand(command);
      verify(invoker, times(1)).pressButton();
    }

    @Test
    @DisplayName("正常系: LightOnCommand（実体）を利用して照明が点灯すること")
    void test02() {
      var light = new LightReceiver();
      var onCommand = new LightOnCommand(light);
      var realInvoker = new RemoteControlInvoker();

      var result = sut.execute(realInvoker, onCommand);

      assertThat(result).isEqualTo("Light is ON");
    }

    @Test
    @DisplayName("正常系: LightOffCommand（実体）を利用して照明が消灯すること")
    void test03() {
      var light = new LightReceiver();
      var offCommand = new LightOffCommand(light);
      var realInvoker = new RemoteControlInvoker();

      var result = sut.execute(realInvoker, offCommand);

      assertThat(result).isEqualTo("Light is OFF");
    }
  }

  @Nested
  class ExceptionTests {

    @Test
    @DisplayName("異常系: インベーカーがnullの場合、NullPointerExceptionが発生すること")
    void test01() {
      assertThatThrownBy(() -> sut.execute(null, command))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("invoker must not be null");
    }

    @Test
    @DisplayName("異常系: コマンドがnullの場合、NullPointerExceptionが発生すること")
    void test02() {
      assertThatThrownBy(() -> sut.execute(invoker, null))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("command must not be null");
    }

    @Test
    @DisplayName("異常系: LightOnCommand生成時にlightがnullの場合、NullPointerExceptionが発生すること")
    void test03() {
      assertThatThrownBy(() -> new LightOnCommand(null))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("light must not be null");
    }
  }
}
