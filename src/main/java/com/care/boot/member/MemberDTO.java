package com.care.boot.member;

<<<<<<< HEAD
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// ✅ Redis 세션 저장을 위해 Serializable 구현
public class MemberDTO implements Serializable {
    private static final long serialVersionUID = 1L;

=======
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;


public class MemberDTO {
>>>>>>> 608638d5f1afeb995ba21ef2eb37ac0f9578c15c
    private String id;          // 회원 ID
    private String pw;          // 비밀번호
    private String userName;    // 이름
    private String mobile;      // 연락처
    private String membership;  // "Regular", "VIP", "Admin"
    private Integer vipNumber;   // VIP 회원만 해당
<<<<<<< HEAD
    private int ticket_number;  // 티켓 예매한 경우 해당 티켓 번호
    private String confirm;
    private LocalDateTime date;

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    // 🛠 기본 생성자 (필수!)
    public MemberDTO() {
        this.membership = "Regular";
=======
    private int ticket_number;// 티켓 예매한 경우 해당 티켓 번호
    private String confirm;
    private LocalDateTime date;
    
    public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
		
	}

	// 🛠 기본 생성자 (필수!)
    public MemberDTO() {
    	this.membership = "Regular";
>>>>>>> 608638d5f1afeb995ba21ef2eb37ac0f9578c15c
    }

    // 🛠 모든 필드를 포함한 생성자 (최종 통합)
    public MemberDTO(String id, String pw, String userName, String mobile, String membership, int vipNumber, int ticketNumber, LocalDateTime date) {
        this.id = id;
        this.pw = pw;
        this.userName = userName;
        this.mobile = mobile;
        this.membership = membership;
        this.vipNumber = vipNumber;
        this.ticket_number = ticketNumber;
        this.date = date;
    }

    public int getTicket_number() {
<<<<<<< HEAD
        return ticket_number;
    }

    public void setTicket_number(int ticket_number) {
        this.ticket_number = ticket_number;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

=======
		return ticket_number;
	}

	public void setTicket_number(int ticket_number) {
		this.ticket_number = ticket_number;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	// ✅ Getter & Setter
>>>>>>> 608638d5f1afeb995ba21ef2eb37ac0f9578c15c
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPw() { return pw; }
    public void setPw(String pw) { this.pw = pw; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getMembership() { return membership; }
    public void setMembership(String membership) { this.membership = membership; }

    public Integer getVipNumber() { return vipNumber; }
    public void setVipNumber(int vipNumber) { this.vipNumber = vipNumber; }

    public int getTicketNumber() { return ticket_number; }
    public void setTicketNumber(int ticketNumber) { this.ticket_number = ticketNumber; }

    // ✅ 티켓 예매 메서드 (VIP: 1~100, 일반: 101~5000)
    public boolean bookTicket(int currentTicketCount) {
        if (this.membership.equals("VIP")) {
            this.ticket_number = this.vipNumber; // VIP는 자신의 VIP 번호가 티켓 번호
            return true;
        } else if (currentTicketCount >= 101 && currentTicketCount <= 5000) {
<<<<<<< HEAD
            this.ticket_number = currentTicketCount;
=======
        	this.ticket_number = currentTicketCount;
>>>>>>> 608638d5f1afeb995ba21ef2eb37ac0f9578c15c
            return true;
        }
        return false; // 티켓 부족
    }
}
