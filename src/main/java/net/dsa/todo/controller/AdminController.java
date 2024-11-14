package net.dsa.todo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.todo.model.User;
import net.dsa.todo.service.UserService;

// 스프링 시큐리티에서 메서드 레벨의 보안을 활성화 하는 어노테이션
// prePostEnable : 메서드 수행전에 권력을 행사, post~ : 메서드를 수행후 리턴값에 권력을 행사
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController {
	
	private final UserService userService;
	
	// 관리자 페이지 메인
	@GetMapping("admin")
	public String admin() {
		log.info("관리자 메인페이지 이동");
		return "admin/main";
	}
	
	// 관리자 로그인 페이지 이동
	@GetMapping("admin/login")
	public String login() {
		return "admin/login";
	}
	
	// 회원목록 조회
	@GetMapping("admin/users")
	public String users(Model model) {
		model.addAttribute("users", userService.findAllUsers());
		log.info("model:{}", model);
		return "admin/list";
	}
	
	// 회원정보 상세
	@GetMapping("admin/users/{id}")
	public String details(@PathVariable(name = "id") String id, Model model) {
		model.addAttribute("user", userService.findUser(id));
		return "admin/edit";
	}
	
	// 회원정보 수정
	// 매니저와 어드민 모두 접근이 가능
	@Secured({"ROLE_MANAGER", "ROLE_ADMIN"})
	@PostMapping("admin/users/{id}/edit")
	public String edit(@PathVariable(name = "id") String id, @ModelAttribute User user) {
		userService.updateUser(id, user);
		return "redirect:/admin/users/" + id;
	}
	
	// 회원정보 삭제
	// 어드민만 접근이 가능
	// 두개의 차이는 좀더 세밀한 차이를 할수있다, ROLE 검사뿐만아니라 특정값에 대해서 체크할수있다.
//	@Secured("ROLE_ADMIN")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("admin/users/{id}/delete")
	public String delete(@PathVariable(name = "id") String id) {
		userService.deleteUser(id);
		return "redirect:/admin/users";
	}
	
	
}
