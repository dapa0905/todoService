package net.dsa.todo.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TodoUpdateForm {
	
	private Long id;
	private String title;
	private String details;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dueDate;
	
	private Status status;
	private User user;
	
	public Todo toTodo() {
		Todo todo = new Todo();
		todo.setId(this.id);
		todo.setTitle(this.title);
		todo.setDetails(this.details);
		todo.setDueDate(this.dueDate);
		todo.setStatus(this.status);
		
		return todo;
	}
}
