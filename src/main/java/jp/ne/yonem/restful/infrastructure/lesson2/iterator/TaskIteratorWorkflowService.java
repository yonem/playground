package jp.ne.yonem.restful.infrastructure.lesson2.iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;

/** Iteratorパターンを利用してタスクコレクションを走査するサービスです。 */
@Service
public class TaskIteratorWorkflowService {

  /**
   * 指定されたタスクコレクションをイテレーターで走査し、全タスクのタイトルリストを返却します。
   *
   * @param collection タスクコレクション
   * @return タスクタイトルのリスト
   */
  public List<String> execute(TaskCollection collection) {
    var safeCollection = Objects.requireNonNull(collection, "collection must not be null");
    List<String> resultTitles = new ArrayList<>();

    var iterator = safeCollection.createIterator();
    while (iterator.hasNext()) {
      Task task = iterator.next();
      resultTitles.add(task.title());
    }

    return resultTitles;
  }
}
