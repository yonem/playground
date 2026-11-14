package jp.ne.yonem.restful.infrastructure.lesson2.iterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** タスクを保持する具象集約クラスです。 */
public class TaskCollection implements Aggregate<Task> {

  private final List<Task> tasks = new ArrayList<>();

  public void addTask(Task task) {
    tasks.add(Objects.requireNonNull(task, "task must not be null"));
  }

  public Task getTaskAt(int index) {
    return tasks.get(index);
  }

  public int getCount() {
    return tasks.size();
  }

  @Override
  public Iterator<Task> createIterator() {
    return new TaskIterator(this);
  }
}
