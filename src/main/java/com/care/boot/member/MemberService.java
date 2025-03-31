package com.care.boot.member;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MemberDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;            // 회원 ID
    private String pw;            // 비밀번호
    private String userName;      // 이름
    private String mobile;        // 연락처
    private String email;         // ✅ 이메일
    private String membership;    // "Regular", "VIP", "Admin"
    private Integer vipNumber;    // VIP 번호
    private int ticket_number;    // 티켓 예매 번호
    private String confirm;       // 비밀번호 확인용
    private LocalDateTime date;   // 가입 일시

    // ✅ 기본 생성자
    public MemberDTO() {
        this.membership = "Regular";
    }

    // ✅ 전체 필드 생성자
    public MemberDTO(String id, String pw, String userName, String mobile, String email,
                     String membership, int vipNumber, int ticketNumber, LocalDateTime date) {
        this.id = id;
        this.pw = pw;
        this.userName = userName;
        this.mobile = mobile;
        this.email = email;
        this.membership = membership;
        this.vipNumber = vipNumber;
        this.ticket_number = ticketNumber;
        this.date = date;
    }

    // ✅ Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPw() { return pw; }
    public void setPw(String pw) { this.pw = pw; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMembership() { return membership; }
    public void setMembership(String membership) { this.membership = membership; }

    public Integer getVipNumber() { return vipNumber; }
    public void setVipNumber(int vipNumber) { this.vipNumber = vipNumber; }

    public int getTicketNumber() { return ticket_number; }
    public void setTicketNumber(int ticketNumber) { this.ticket_number = ticketNumber; }

    public int getTicket_number() { return ticket_number; }
    public void setTicket_number(int ticket_number) { this.ticket_number = ticket_number; }

    public String getConfirm() { return confirm; }
    public void setConfirm(String confirm) { this.confirm = confirm; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    // ✅ 티켓 예매 로직
    public boolean bookTicket(int currentTicketCount) {
        if ("VIP".equalsIgnoreCase(this.membership)) {
            this.ticket_number = this.vipNumber;
            return true;
        } else if (currentTicketCount >= 101 && currentTicketCount <= 5000) {
            this.ticket_number = currentTicketCount;
            return true;
        }
        return false;
    }
}

