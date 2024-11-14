package net.dsa.todo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class User {
	
	@Id
	private String id;
	private String password;
	private String name;
	private String email;
	@Enumerated(EnumType.STRING)
	private RoleType role;
}
