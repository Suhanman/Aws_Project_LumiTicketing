public String loginProc(String id, String pw, HttpSession session, HttpServletResponse response) {
    if (id == null || id.trim().isEmpty()) return "아이디를 입력하세요.";
    if (pw == null || pw.trim().isEmpty()) return "비밀번호를 입력하세요.";

    MemberDTO check = mapper.login(id);
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    if (check != null && encoder.matches(pw, check.getPw())) {
        session.setAttribute("id", check.getId());
        session.setAttribute("userName", check.getUserName());
        session.setAttribute("mobile", check.getMobile());
        session.setAttribute("email", check.getEmail());         // ✅ 추가
        session.setAttribute("membership", check.getMembership());

        // ✅ loginUser 세션에 MemberDTO 객체 통째로 저장
        session.setAttribute("loginUser", check);

        String grade = check.getMembership();
        try {
            redisService.save(check.getId(), new ObjectMapper().writeValueAsString(check));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

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
    
