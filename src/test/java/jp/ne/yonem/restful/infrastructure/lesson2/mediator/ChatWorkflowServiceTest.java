package jp.ne.yonem.restful.infrastructure.lesson2.mediator;

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
class ChatWorkflowServiceTest {

  @InjectMocks private ChatWorkflowService sut;

  @Mock private ChatMediator mediator;

  @Mock private ChatUser sender;

  @Mock private ChatUser receiver;

  @Nested
  class SuccessTests {

    @Test
    @DisplayName("正常系: モックメディエーター経由でメッセージが送信されること")
    void test01() {
      var result = sut.execute(mediator, sender, receiver, "Hello World");

      assertThat(result).isEqualTo("Message broadcasted");
      verify(mediator, times(1)).addUser(sender);
      verify(mediator, times(1)).addUser(receiver);
      verify(sender, times(1)).send("Hello World");
    }

    @Test
    @DisplayName("正常系: ChatRoomMediator（実体）を使用してチャットユーザー間でメッセージが正しく中継・受信されること")
    void test02() {
      var room = new ChatRoomMediator();
      var alice = new StandardChatUser(room, "Alice");
      var bob = new StandardChatUser(room, "Bob");

      var result = sut.execute(room, alice, bob, "Hi Bob!");

      assertThat(result).isEqualTo("[Alice says]: Hi Bob!");
      assertThat(bob.getLastReceivedMessage()).isEqualTo("[Alice says]: Hi Bob!");
    }
  }

  @Nested
  class ExceptionTests {

    @Test
    @DisplayName("異常系: mediatorがnullの場合、NullPointerExceptionが発生すること")
    void test01() {
      assertThatThrownBy(() -> sut.execute(null, sender, receiver, "msg"))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("mediator must not be null");
    }

    @Test
    @DisplayName("異常系: senderがnullの場合、NullPointerExceptionが発生すること")
    void test02() {
      assertThatThrownBy(() -> sut.execute(mediator, null, receiver, "msg"))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("sender must not be null");
    }

    @Test
    @DisplayName("異常系: receiverがnullの場合、NullPointerExceptionが発生すること")
    void test03() {
      assertThatThrownBy(() -> sut.execute(mediator, sender, null, "msg"))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("receiver must not be null");
    }

    @Test
    @DisplayName("異常系: messageがnullの場合、NullPointerExceptionが発生すること")
    void test04() {
      assertThatThrownBy(() -> sut.execute(mediator, sender, receiver, null))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("message must not be null");
    }
  }
}
