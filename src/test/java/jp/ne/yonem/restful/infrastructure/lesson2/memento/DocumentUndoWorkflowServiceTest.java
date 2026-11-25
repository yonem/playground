package jp.ne.yonem.restful.infrastructure.lesson2.memento;

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
class DocumentUndoWorkflowServiceTest {

  @InjectMocks private DocumentUndoWorkflowService sut;

  @Mock private DraftDocument document;

  @Mock private HistoryCaretaker caretaker;

  @Nested
  class SuccessTests {

    @Test
    @DisplayName("正常系: モック経由でスナップショット保存・編集・復元の一連の操作が行われること")
    void test01() {
      var snapshot = new DocumentSnapshot("v1");
      when(document.save()).thenReturn(snapshot);
      when(caretaker.pop()).thenReturn(snapshot);
      when(document.getContent()).thenReturn("v1");

      var result = sut.execute(document, caretaker, "v2");

      assertThat(result).isEqualTo("v1");
      verify(document, times(1)).save();
      verify(caretaker, times(1)).push(snapshot);
      verify(document, times(1)).edit("v2");
      verify(caretaker, times(1)).pop();
      verify(document, times(1)).restore(snapshot);
    }

    @Test
    @DisplayName("正常系: DraftDocumentとHistoryCaretakerの実体で編集後に元の状態へ復元できること")
    void test02() {
      var realDocument = new DraftDocument("初期ドラフト");
      var realCaretaker = new HistoryCaretaker();

      var result = sut.execute(realDocument, realCaretaker, "誤って消去した本文");

      assertThat(result).isEqualTo("初期ドラフト");
      assertThat(realCaretaker.hasHistory()).isFalse();
    }
  }

  @Nested
  class ExceptionTests {

    @Test
    @DisplayName("異常系: documentがnullの場合、NullPointerExceptionが発生すること")
    void test01() {
      assertThatThrownBy(() -> sut.execute(null, caretaker, "next"))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("document must not be null");
    }

    @Test
    @DisplayName("異常系: caretakerがnullの場合、NullPointerExceptionが発生すること")
    void test02() {
      assertThatThrownBy(() -> sut.execute(document, null, "next"))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("caretaker must not be null");
    }

    @Test
    @DisplayName("異常系: nextContentがnullの場合、NullPointerExceptionが発生すること")
    void test03() {
      assertThatThrownBy(() -> sut.execute(document, caretaker, null))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("nextContent must not be null");
    }

    @Test
    @DisplayName("異常系: 履歴が空の状態でpopを呼び出した場合、IllegalStateExceptionが発生すること")
    void test04() {
      var emptyCaretaker = new HistoryCaretaker();

      assertThatThrownBy(emptyCaretaker::pop)
          .isInstanceOf(IllegalStateException.class)
          .hasMessageContaining("No snapshot available to restore");
    }
  }
}
