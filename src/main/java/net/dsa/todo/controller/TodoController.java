package net.dsa.todo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dsa.todo.config.AuthenticatedUser;
import net.dsa.todo.model.Status;
import net.dsa.todo.model.Todo;
import net.dsa.todo.model.TodoRegisterForm;
import net.dsa.todo.model.TodoUpdateForm;
import net.dsa.todo.model.User;
import net.dsa.todo.service.TodoService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TodoController {
	
	private final TodoService todoService;
	
	// 할 일 목록 페이지 이동
	@GetMapping("todo/list")
	public String todoList(@AuthenticationPrincipal AuthenticatedUser loginUser,
						   Model model) {
		User findUser = loginUser.getUser();
		List<Todo> todoList = todoService.getTodoList(findUser);
		model.addAttribute("todoList", todoList);
		return "todoList";
	}
		
	// 할 일 등록 페이지 이동
	@GetMapping("todo/register")
	public String addTodo(Model model) {
		model.addAttribute("todoRegisterForm", new TodoRegisterForm());
		
		return "registerTodo";
	}
	
	
	// 할 일 등록
	@PostMapping("todo/register")
	public String addTodo(@SessionAttribute(name = "loginUser", required = false) User loginUser,
						  @ModelAttribute TodoRegisterForm todoRegisterForm) {
		
		log.info("todoRegisterForm: {}", todoRegisterForm);
		Todo todo = todoRegisterForm.toTodo();
		todo.setStatus(Status.PENDING);
		todo.setUser(loginUser);
		
		todoService.addTodo(todo);
		
		return "redirect:/todo/list";
	}
	
	// 할 일 조회 페이지 이동
	@GetMapping("todo/details")
	public String todoDetails(@RequestParam(name = "id") Long id,
							  Model model) {
		Todo todo = todoService.getTodo(id);
					
		model.addAttribute("todoUpdateForm", todo.toUpdateForm());
		
		return "todoDetails";
	}

	
	// 할 일 수정
	@PostMapping("todo/update")
	public String todoUpdate(@SessionAttribute(name = "loginUser", required = false) User loginUser,
							 @ModelAttribute TodoUpdateForm todoUpdateForm) {
		
		Todo todo = todoService.getTodo(todoUpdateForm.getId());
		
		// 로그인 유저가 작성한 todo 인지 확인하고 맞으면 수정한다.
		if (todo.getUser().getId().equals(loginUser.getId())) {
			Todo updateTodo = todoUpdateForm.toTodo();
			todoService.updateTodo(updateTodo);
		}
		
		return "redirect:/todo/list";
	}
	
	
	// 할 일 삭제
	@GetMapping("todo/remove")
	public String removeTodo(@SessionAttribute(name = "loginUser", required = false) User loginUser,
							 @RequestParam(name = "id") Long id) {
		
		Todo todo = todoService.getTodo(id);
		
		// 로그인 유저가 작성한 todo 인지 확인하고 맞으면 삭제한다.
		if (todo.getUser().getId().equals(loginUser.getId())) {
			todoService.removeTodo(id);
		}
		
		return "redirect:/todo/list";
	}

}
