package net.dsa.todo.config;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.ToString;
import net.dsa.todo.model.User;

@Getter @ToString
public class AuthenticatedUser implements UserDetails {

	private User user;

	public AuthenticatedUser(User user) {
		this.user = user;
	}
	
	// 사용자가 가지고 있는 권한 목록을 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		// 유저의 권한 처리를 toString 하는거라고한다
		collect.add(new SimpleGrantedAuthority(user.getRole().name()));
		return collect;
	}
	
	// 페스워드를 리턴한다.
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	// 아이디를 리턴한다.
	@Override
	public String getUsername() {
		return user.getId();
	}
	
	// 계정의 사용기간만료 유무 리턴
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	// 계정의 잠겨있지 않는지 여부를 리턴 
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	// 계정의 인증시간이 만료되지 않았는지를 체크
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// 계정이 활성화 되었는지를 리턴
	@Override
	public boolean isEnabled() {
		return true;
	}
}
