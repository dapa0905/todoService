package net.dsa.todo.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TodoRegisterForm {
	
	private String title;
	private String details;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dueDate;
			
	public Todo toTodo() {
		Todo todo = new Todo();
		todo.setTitle(this.title);
		todo.setDetails(this.details);
		todo.setDueDate(this.dueDate);
		
		return todo;
	}
}
