package com.zerobase.bakingin_project.config;

import com.zerobase.bakingin_project.member.exception.MemberErrorCode;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMessage;


        if (exception instanceof UsernameNotFoundException) {
            errorMessage = MemberErrorCode.USER_NOT_EXIST.getValue();
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = MemberErrorCode.USER_PASSWORD_NOT_CORRECT.getValue();
        } else if (exception instanceof InternalAuthenticationServiceException) {
            errorMessage = exception.getMessage();
        } else {
            errorMessage = "알 수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8"); // 한글 인코딩 깨지지 않도록 함
        setUseForward(true);
        setDefaultFailureUrl("/member/login?error=true&exception="+errorMessage);

        super.onAuthenticationFailure(request, response, exception);
    }
}
