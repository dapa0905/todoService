package net.dsa.todo.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
	PENDING("대기"),
	IN_PROGRESS("진행"),
	COMPLETED("완료");
	
	private final String description;
}
