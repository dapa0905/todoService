package net.dsa.todo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Todo {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String details;
	private LocalDate dueDate;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public TodoUpdateForm toUpdateForm() {
		TodoUpdateForm todoUpdateForm = new TodoUpdateForm();
		
		todoUpdateForm.setId(this.id);
		todoUpdateForm.setTitle(this.title);
		todoUpdateForm.setDetails(this.details);
		todoUpdateForm.setDueDate(this.dueDate);
		todoUpdateForm.setStatus(this.status);
		todoUpdateForm.setUser(this.user);
		
		return todoUpdateForm;
	}
}
