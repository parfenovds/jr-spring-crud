package com.javarush.repository;

import com.javarush.entity.Task;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TaskRepository extends BaseRepositoryImpl<Integer, Task> {
  private final SessionFactory sessionFactory;

  public TaskRepository(SessionFactory sessionFactory, SessionFactory sessionFactory1) {
    super(sessionFactory, Task.class);
    this.sessionFactory = sessionFactory1;
  }

  public List<Task> findAllPaging(Integer offset, Integer limit) {
    return sessionFactory.getCurrentSession()
        .createQuery("SELECT t FROM Task t", Task.class)
        .setFirstResult(offset)
        .setMaxResults(limit)
        .getResultList();
  }

  public Integer getAllCount() {
    Long result = sessionFactory.getCurrentSession()
        .createQuery("SELECT COUNT(t) FROM Task t", Long.class)
        .uniqueResult();
    return Math.toIntExact(result);
  }
}
