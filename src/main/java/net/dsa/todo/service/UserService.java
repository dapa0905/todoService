package net.dsa.todo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import net.dsa.todo.model.RoleType;
import net.dsa.todo.model.User;
import net.dsa.todo.repository.UserRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	// 회원가입
	@Transactional
	public void register(User user) {
		// 동일한 아이디로 가입된 회원정보가 존재하는지 확인한다.
		Optional<User> findUser = userRepository.findById(user.getId());

		// 회원정보가 존재하지 않으면 가입시킨다.
		if (findUser.isEmpty()) {
			// 페스워드 인코더 하는 부분
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			// 일반 사용자 권환을 부여한다..
			user.setRole(RoleType.ROLE_USER);
			userRepository.save(user);
		}
	}

	// 아이디로 회원정보 조회
	public User findUser(String id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("회원정보가 없습니다."));
	}
	
	// 모든 회원 조회
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	
	// 회원정보 수정
	@Transactional
	public void updateUser(String id, User updateUser) {
		User findUser = userRepository.findById(id).orElseThrow(
				() -> new RuntimeException("회원 정보가 없습니다."));
		// 페스워드 정보가 없으면 수정하지 않는다.
		if (!updateUser.getPassword().isEmpty()) {
			findUser.setPassword(passwordEncoder.encode(updateUser.getPassword()));
		}
		findUser.setName(updateUser.getName());
		findUser.setEmail(updateUser.getEmail());
		findUser.setRole(updateUser.getRole());
	}
	
	// 회원정보 삭제
	@Transactional
	public void deleteUser(String id) {
		userRepository.deleteById(id);
	}
}
