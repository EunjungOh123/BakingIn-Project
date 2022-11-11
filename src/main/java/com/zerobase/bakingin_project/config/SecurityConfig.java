package com.zerobase.bakingin_project.config;

import com.zerobase.bakingin_project.member.service.UserDetailsServiceImpl;
import com.zerobase.bakingin_project.member.type.MemberRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final AuthFailureHandler authFailureHandler;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        //  BadCredentialException 과 UsernameNotFoundException 별도로 처리하기 위해 사용
        authProvider.setHideUserNotFoundExceptions(false);

        return authProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { // 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

    @Bean // configure(AuthenticationManagerBuilder) 대신 사용
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers( "/css/**"); // 보안 필터를 적용 X
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests()
                .antMatchers("/", "/member/login",
                        "/member/register", "/member/email-auth", "/board/list")
                .permitAll(); // 해당 경로들은 접근을 허용

        http.authorizeRequests()
                .antMatchers("/board/detail", "/board/update").authenticated();

        http.authorizeRequests()
                .antMatchers("/admin/**")
                .hasAuthority(MemberRole.ADMIN.getValue());

        /* anyRequest() : 이외의 모든 resource 접근에 대해 authenticated() : 로그인 해야 허용 */

        http.formLogin()
                .loginPage("/member/login")
                // loadUserByUsername에 값이 들어오기 위해서는 로그인 폼에서 password 태그의 name이 userid로 설정
                .usernameParameter("userId")
                .failureHandler(authFailureHandler)
                .defaultSuccessUrl("/")
                .permitAll();

        http.logout() // 로그아웃 설정 진행
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 로그아웃 경로 지정
                .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 경로를 지정
                .invalidateHttpSession(true); // 로그아웃 성공 시 세션을 제거

        http.exceptionHandling()
                .accessDeniedPage("/error/denied");

        http.authenticationProvider(authenticationProvider());

        return http.build();
    }
}
