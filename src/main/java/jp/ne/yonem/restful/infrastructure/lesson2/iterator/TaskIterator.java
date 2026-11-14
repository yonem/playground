package jp.ne.yonem.restful.infrastructure.lesson2.iterator;

import java.util.Objects;

/** TaskCollection専用の具象イテレーターです。 */
public class TaskIterator implements Iterator<Task> {

  private final TaskCollection collection;
  private int index = 0;

  public TaskIterator(TaskCollection collection) {
    this.collection = Objects.requireNonNull(collection, "collection must not be null");
  }

  @Override
  public boolean hasNext() {
    return index < collection.getCount();
  }

  @Override
  public Task next() {
    if (!hasNext()) {
      throw new java.util.NoSuchElementException("No more elements");
    }
    return collection.getTaskAt(index++);
  }
}
