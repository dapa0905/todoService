package net.dsa.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.dsa.todo.model.Todo;
import net.dsa.todo.model.User;
import net.dsa.todo.repository.TodoRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class TodoService {
	
	private final TodoRepository todoRepository;
	
	// 할 일 목록
	public List<Todo> getTodoList(User user) {
		return todoRepository.findByUser(user);
	}
	
	// 할 일 등록
	@Transactional
	public void addTodo(Todo todo) {
		todoRepository.save(todo);
	}
	
	// 할 일 조회
	// 메서드가 실행된 후 반환되는 값에 대한 접근 제어를 할 수 있다.
	@PostAuthorize("returnObject.user.id == authentication.principal.User.id"
			+ "or hasAnyRole('ROLE_MANAGER')")
	public Todo getTodo(Long id) {
		return todoRepository.findById(id).orElseThrow(
				() -> new RuntimeException("ID [" + id + "] 할 일이 없습니다."));
	}
	
	// 할 일 수정
	@Transactional
	public void updateTodo(Todo todo) { 
		Todo findTodo = todoRepository.findById(todo.getId()).orElseThrow(
				() -> new RuntimeException("ID [" + todo.getId() + "] 할 일이 없습니다."));
		
		findTodo.setTitle(todo.getTitle());
		findTodo.setDetails(todo.getDetails());
		findTodo.setDueDate(todo.getDueDate());
		findTodo.setStatus(todo.getStatus());		
	}
	
	// 할 일 삭제
	@Transactional
	public void removeTodo(Long id) {
		todoRepository.deleteById(id);
	}

}