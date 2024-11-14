package net.dsa.todo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {
	ROLE_USER("일반 사용자"),
	ROLE_MANAGER("관리자"),
	ROLE_ADMIN("최고 관리자");
	

	private final String description;

}
