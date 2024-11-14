package net.dsa.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.dsa.todo.model.Todo;
import net.dsa.todo.model.User;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	List<Todo> findByUser(User user);
}
