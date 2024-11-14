package net.dsa.todo.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomAuthFailuserHandler extends SimpleUrlAuthenticationFailureHandler {
	
	// 사용자 인증 실패 시 실행되는 핸들러 메소드
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.info("onAuthenticationFailure 핸들러 호출");
		log.info("exception: {}", exception);
		
		/*
		 * 인증 실패의 예외 종류
		 * BadCredentialException : 아이디 or 비밀번호가 다르다
		 * LockedException : UserDetails 객체의 isAccountNonLocked() 메소드의 리턴값이 false일 경우 
		 * DisabledException : UserDetails 객체의 isEnable() 메소드의 리턴값이 false일 경우 
		 * AccountExpiredException : UserDetails 객체의 isAccountNoneExpired() 메소드의 리턴값이 false일 경우 
		 * AuthenticationCredentialsNotFoundException : UserDetails 객체의 is CredendialNonExpired() 메소드의 리턴값이 false일 경우
		 */
		
		String message = "";
		if (exception instanceof UsernameNotFoundException) {
			log.info("아이디가 존재하지 않습니다.");
			message="아이디가 존재하지 않습니다.";
		}
		else if (exception instanceof BadCredentialsException) {
			log.info("페스워드가 올바르지 않습니다.");
			message="페스워드가 올바르지 않습니다.";
		}
		else if (exception instanceof AuthenticationCredentialsNotFoundException) {
			log.info("AuthenticationCredentialsNotFoundException 예외 발생");
		}
		
		else {
			log.info("알수없는 이벤트가 발생합니다.");
		}
		
		// 로그인 실패 시 지정한 URL로 파라미터를 담아서 전달
		message = URLEncoder.encode(message, StandardCharsets.UTF_8);
		setDefaultFailureUrl("/user/login?message=" + message);
		
		// 로그인 실패 시 기본 처리방법
		super.onAuthenticationFailure(request, response, exception);
	}

}
