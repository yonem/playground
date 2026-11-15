package jp.ne.yonem.restful.infrastructure.lesson2.iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaskIteratorWorkflowServiceTest {

  @InjectMocks private TaskIteratorWorkflowService sut;

  @Mock private TaskCollection collection;

  @Mock private Iterator<Task> iterator;

  @Nested
  class SuccessTests {

    @Test
    @DisplayName("正常系: モックコレクションとイテレーター経由でタスクタイトル一覧が取得できること")
    void test01() {
      when(collection.createIterator()).thenReturn(iterator);
      when(iterator.hasNext()).thenReturn(true, true, false);
      when(iterator.next()).thenReturn(new Task("タスク1", 1), new Task("タスク2", 2));

      var result = sut.execute(collection);

      assertThat(result).containsExactly("タスク1", "タスク2");
      verify(collection, times(1)).createIterator();
    }

    @Test
    @DisplayName("正常系: 実体のTaskCollectionを使用して順次要素を走査できること")
    void test02() {
      var realCollection = new TaskCollection();
      realCollection.addTask(new Task("設計書作成", 1));
      realCollection.addTask(new Task("実装", 2));
      realCollection.addTask(new Task("テスト", 3));

      var result = sut.execute(realCollection);

      assertThat(result).containsExactly("設計書作成", "実装", "テスト");
    }
  }

  @Nested
  class ExceptionTests {

    @Test
    @DisplayName("異常系: コレクションがnullの場合、NullPointerExceptionが発生すること")
    void test01() {
      assertThatThrownBy(() -> sut.execute(null))
          .isInstanceOf(NullPointerException.class)
          .hasMessageContaining("collection must not be null");
    }

    @Test
    @DisplayName("異常系: イテレーターの要素がない状態でnextを呼び出した場合、NoSuchElementExceptionが発生すること")
    void test02() {
      var realCollection = new TaskCollection();
      var taskIterator = realCollection.createIterator();

      assertThatThrownBy(taskIterator::next)
          .isInstanceOf(NoSuchElementException.class)
          .hasMessageContaining("No more elements");
    }
  }
}
