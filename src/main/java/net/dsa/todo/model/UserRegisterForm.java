package net.dsa.todo.model;

import lombok.Data;

@Data
public class UserRegisterForm {
	
	private String id;
	private String password;
	private String name;
	private String email;
	
	public User toUser() {
		User user = new User();
		
		user.setId(this.id);
		user.setPassword(this.password);
		user.setName(this.name);
		user.setEmail(this.email);
		
		return user;
	}
}
