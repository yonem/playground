package jp.ne.yonem.restful.infrastructure.lesson2.iterator;

import java.util.Objects;

/** 管理対象のタスク（レコード）です。 */
public record Task(String title, int priority) {
  public Task {
    Objects.requireNonNull(title, "title must not be null");
  }
}
