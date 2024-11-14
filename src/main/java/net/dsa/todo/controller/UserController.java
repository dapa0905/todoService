package net.dsa.todo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.todo.config.AuthenticatedUser;
import net.dsa.todo.model.LoginForm;
import net.dsa.todo.model.UserRegisterForm;
import net.dsa.todo.model.User;
import net.dsa.todo.service.UserService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;
	
	// 회원가입 폼 이동
	@GetMapping("user/register")
	public String register(Model model) {
		
		UserRegisterForm registerForm = new UserRegisterForm();
				
		model.addAttribute("registerForm", registerForm);
		
		return "registerUser";
	}
	
	// 회원가입
	@PostMapping("user/register")
	public String register(@ModelAttribute UserRegisterForm registerForm) {
		log.info("registerForm: {}", registerForm);
		
		User user = registerForm.toUser();
		userService.register(user);
		
		return "redirect:/user/login";
	}
	
	// 로그인 폼 이동
	@GetMapping("user/login")
	public String login(@RequestParam(name = "message", required = false) String message,
			Model model) {
		log.info("message :{}", message);
		model.addAttribute("loginForm", new LoginForm());
		
		return "login";
	}
	
	// 로그인
//	@PostMapping("user/login")
//	public String login(@ModelAttribute LoginForm loginForm,
//						HttpSession session) {
//		
//		log.info("loginForm: {}", loginForm);
//		
//		User findUser = userService.findUser(loginForm.getId());
//		
//		if (findUser.getPassword().equals(loginForm.getPassword())) {
//			log.info("로그인 성공");
//			session.setAttribute("loginUser", findUser);
//		}
//		
//		return "redirect:/";
//	}
	
	@GetMapping("user/login-success")
	public String loginSuccess(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
		
		log.info("****** 로그인 성공 authenticatedUser:{}", authenticatedUser.getUser());
		return "redirect:/";
	}
	
	@GetMapping("user/login-fail")
	public String loginfail() {
		log.info("****** 로그인 실패");
		return "redirect:/";
	}
	
	// 로그아웃
//	@GetMapping("user/logout")
//	public String logout(HttpServletRequest request) {
//		
//		HttpSession session = request.getSession(false);
//		if (session != null) {
//		    session.invalidate();
//		}
//		
//		return "redirect:/";
//	}
}
