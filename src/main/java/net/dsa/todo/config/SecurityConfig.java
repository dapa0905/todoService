package net.dsa.todo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {
	
	private final AuthenticationFailureHandler authenticationFailureHandler;

	// 관리자용 필터체인
	@Bean
	@Order(1)
	public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
				// 해당 필더의 범위를 지정한다
				// /admin/** 경로에 대한 모든 요청을 처리하는 필터
				.securityMatcher("/admin/**");

		http.authorizeHttpRequests(request -> request
				.requestMatchers("/admin/login").permitAll()
				.anyRequest().hasAnyRole("MANAGER","ADMIN"))
				
		.httpBasic(Customizer.withDefaults())
		.formLogin(formLogin -> formLogin
						.loginPage("/admin/login")
						.usernameParameter("id")
						.loginProcessingUrl("/admin/login")
						.defaultSuccessUrl("/admin")
						.failureUrl("/admin/login"))
		.logout(logout -> logout
						.logoutUrl("/admin/logout")
						.logoutSuccessUrl("/admin/login")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID"));
		return http.build();
	}

	// 클라이언트용 필터체인
	@Bean
	@Order(2)
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// csrf 설정을 비활성화 한다.
		http.csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(request -> request
				// 모든 사용자에게 접근을 허용하는 URL
				.requestMatchers("/", "/user/register", "/user/login").permitAll()
				// 그 외 경로는 인증을 받은 사용자만 접근할 수있다.
				.anyRequest().authenticated())
				// http basic 인증을 활성화 한다.
				.httpBasic(Customizer.withDefaults())
				// 폼 로그인 방식을 사용한다
				.formLogin(formLogin -> formLogin
						// 사용자가 만든 로그인 페이지를 사용한다.
						// 설정하지 않으면 기본 URL이 "/login"으로 호출된다. get 방식의 요청을 처리 부분
						.loginPage("/user/login")
						// username 파라미터의 기본값은 "username"이다. 만약 다른사람을 사용한다면 파라미터 이름을 지정해야한다.
						.usernameParameter("id")
//				.passwordParameter("password")
						// 로그인 인증 처리를 하는 URL post 방식의 요청의 부분의 처리 부분
						.loginProcessingUrl("/user/login")
						// 로그인 성공 시 URL 지정
						.defaultSuccessUrl("/user/login-success")
						// 로그인 실패시 이동할 URL
						.failureUrl("/user/login-fail")
						// 로그인 실패 핸들러
						.failureHandler(authenticationFailureHandler)
						)
						
				.logout(logout -> logout
						// 로그아웃 URL 지정
						.logoutUrl("/user/logout")
						// 로그아웃 성공 후 리다이렉트 주소
						.logoutSuccessUrl("/")
						// 세션을 삭제
						.invalidateHttpSession(true)
						// 쿠키 삭제
						.deleteCookies("JSESSIONID"));
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
