package com.care.boot.member;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.care.boot.config.RedisService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;

@Service
public class MemberService {
    @Autowired private IMemberMapper mapper;
    @Autowired private HttpSession session;
    @Autowired private HttpServletResponse response;
    @Autowired private RedisService redisService;
    @Autowired private AmazonSimpleEmailService amazonSES;
    
    private final String FROM = "victor1919@naver.com"; // SES에 인증된 주소로 변경

    public void sendWelcomeEmail(String toEmail, String userName) {
        String subject = "루미티켓팅 가입을 환영합니다!";
        String body = String.format("안녕하세요 %s님,\n\n루미티켓팅에 오신 것을 환영합니다! 🎉", userName);

        SendEmailRequest request = new SendEmailRequest()
            .withDestination(new Destination().withToAddresses(toEmail))
            .withMessage(new Message()
                .withSubject(new Content(subject))
                .withBody(new Body().withText(new Content(body))))
            .withSource(FROM);

        amazonSES.sendEmail(request);
    }




    public String registProc(MemberDTO member) {
        if (member.getId() == null || member.getId().trim().isEmpty()) return "아이디를 입력하세요.";
        if (member.getPw() == null || member.getPw().trim().isEmpty()) return "비밀번호를 입력하세요.";
        if (!member.getPw().equals(member.getConfirm())) return "두 비밀번호를 일치하여 입력하세요.";
        if (member.getUserName() == null || member.getUserName().trim().isEmpty()) return "이름을 입력하세요.";
        if (member.getMobile() == null || member.getMobile().trim().isEmpty()) return "전화번호를 입력하세요.";
        if (member.getEmail() == null || member.getEmail().trim().isEmpty()) return "이메일을 입력하세요.";

        System.out.println("[DEBUG] 회원가입 요청 - 이메일: " + member.getEmail());

        MemberDTO check = mapper.login(member.getId());
        if (check != null) return "이미 사용중인 아이디입니다.";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secretPass = encoder.encode(member.getPw());
        member.setPw(secretPass);

        int result = mapper.registProc(member);
        return (result == 1) ? "회원 등록 완료" : "회원 등록을 다시 시도하세요.";
    }

    public String loginProc(String id, String pw, HttpSession session, HttpServletResponse response) {
        if (id == null || id.trim().isEmpty()) return "아이디를 입력하세요.";
        if (pw == null || pw.trim().isEmpty()) return "비밀번호를 입력하세요.";

        MemberDTO check = mapper.login(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (check != null && encoder.matches(pw, check.getPw())) {
            session.setAttribute("id", check.getId());
            session.setAttribute("userName", check.getUserName());
            session.setAttribute("mobile", check.getMobile());
            session.setAttribute("email", check.getEmail());  // ✅ 이메일 저장
            session.setAttribute("membership", check.getMembership());
            session.setAttribute("loginUser", check); // DTO 통째 저장

            try {
                redisService.save(check.getId(), new ObjectMapper().writeValueAsString(check));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            String grade = check.getMembership();
            if ("vip".equalsIgnoreCase(grade) || "admin".equalsIgnoreCase(grade)) {
                response.setHeader("X-User-Membership", "vip");
                return "redirect:https://vip.lumiticketing.click/boot/index";
            }

            response.setHeader("X-User-Membership", "regular");
            return "redirect:https://www.lumiticketing.click/boot/index";
        }

        return "아이디 또는 비밀번호를 확인 후 다시 입력하세요.";
    }
}

