package net.dsa.todo.model;

import lombok.Data;

@Data
public class LoginForm {

	private String id;
	private String password;
	
	public User toUser() {
		User user = new User();
		
		user.setId(this.id);
		user.setPassword(this.password);
		
		return user;
	}
	
}
