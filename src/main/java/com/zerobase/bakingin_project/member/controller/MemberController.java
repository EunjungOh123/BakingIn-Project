package com.zerobase.bakingin_project.member.controller;

import com.zerobase.bakingin_project.member.dto.RegisterMemberInput;
import com.zerobase.bakingin_project.member.service.MemberService;
import com.zerobase.bakingin_project.member.validator.RegisterValidatorInher.CheckEmailValidator;
import com.zerobase.bakingin_project.member.validator.RegisterValidatorInher.CheckPasswordMatchValidator;
import com.zerobase.bakingin_project.member.validator.RegisterValidatorInher.CheckUserIdValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final CheckEmailValidator checkEmailValidator;
    private final CheckUserIdValidator checkUserIdValidator;
    private final CheckPasswordMatchValidator checkPasswordMatchValidator;

    /* 중복 체크를 위한 커스텀 유효성 검사 */
    /* 컨트롤러가 요청 올 때마다 WebDataBinder 호출되면서 WebDataBinder 에 등록한 검증기가 매번 적용 */

    @InitBinder // 특정 컨트롤러애서 바인딩 또는 검증 설정을 변경할 때 사용
    public void validatorBinder (WebDataBinder webDataBinder) {
        webDataBinder.addValidators(checkEmailValidator);
        webDataBinder.addValidators(checkUserIdValidator);
        webDataBinder.addValidators(checkPasswordMatchValidator);
    }

    @GetMapping("/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/register")
    public String registerSubmit (@Valid RegisterMemberInput memberInput, Errors errors, Model model) {
        if(errors.hasErrors()) {
            /* 회원가입 실패시 입력 데이터 유지 */
            model.addAttribute("memberInput", memberInput);
            /* 유효성 검사를 통과하지 못한 필드와 message 모델에 매핑해서 뷰로 전달 */
            Map<String, String> validateMap = memberService.validateHandling(errors);
            /* map.keySet() -> 모든 key 값 가져온 뒤 반복문을 통해 키와 에러 메세지로 매핑 */
            for (String key : validateMap.keySet()) {
                model.addAttribute(key, validateMap.get(key));
            }
            return "member/register";
        }
        memberService.register(memberInput);
        return "/member/register-success";
    }
    @GetMapping("/login")
    public String login () {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam (value = "exception", required = false) String exception,
            Model model
    ) {
        /* error, exception > model에 담아 view로 넘겨준다 */
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "member/login";
    }
}
