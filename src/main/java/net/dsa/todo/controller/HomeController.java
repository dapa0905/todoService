package net.dsa.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.extern.slf4j.Slf4j;
import net.dsa.todo.model.User;

@Slf4j
@Controller
public class HomeController {

	@GetMapping
	public String home(@SessionAttribute(name="loginUser", required = false) User loginUser) {
		
		if (loginUser != null) {
			log.info("loginUser: {}", loginUser);
		}
		
		return "index";
	}
}
