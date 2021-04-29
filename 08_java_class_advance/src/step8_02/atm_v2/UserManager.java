package step8_02.atm_v2;

import java.util.Random;
import java.util.Scanner;

public class UserManager { // 싱글턴 패턴
	
	private UserManager() {} // private 기본 생성자 생성
	static private UserManager instance = new UserManager(); // 내부에서 static으로 자신의 인스턴트 생성
	static public UserManager getInstance() { // instance를 반환할 getter메서드 생성
		return instance;
	}
	
	Scanner scan = new Scanner(System.in);
	Random ran = new Random();
	
	final int ACC_MAX_CNT = 3;			// 최대 개설 가능한 계좌 수
	User[] userList = null;				// 전체 회원정보
	int userCnt = 0;					// 전체 회원 수
	
	void printAllUser() {
		
		for (int i=0; i<userCnt; i++) {
			System.out.print((i+1) + ".ID(" + userList[i].id + ")\tPW(" + userList[i].pw + ")\t");
			if (userList[i].accCnt != 0) {
				for (int j=0; j<userList[i].accCnt; j++) {
					System.out.print("(" + userList[i].acc[j].accNumber + ":" + userList[i].acc[j].money + ")");
				}
			}
			System.out.println();
		}
	}
	
	
	boolean getCheckAcc(String account) {
		
		boolean isDuple = false;
		for (int i=0; i<userCnt; i++) { // int i=0; i<1; i++ -> i=0일때만 성립
			for (int j=0; j<userList[i].accCnt; j++) { // int j=0; j<0; j++ 성립X
				if (account.equals(userList[i].acc[j].accNumber)) {
					isDuple = true;
				}
			}
		}
		return isDuple; // isDuple = false 반환
		
	}
	
	
	int logUser() {
		
		int identifier = -1;
		
		System.out.print("[로그인]아이디를 입력하세요 : ");
		String id = scan.next();
		System.out.print("[로그인]패스워드를 입력하세요 : ");
		String pw = scan.next();
		
		for (int i=0; i<UserManager.instance.userCnt; i++) { // i=0; i<1; i++ -> i=0일때만 성립
			if (userList[i].id.equals(id) && userList[i].pw.equals(pw)) {
				identifier = i; // identifier = 0
			}
		}
		
		return identifier; // identifier 값 반환 (0)

	}
	
	
	boolean checkId(String id) { // userCnt 초기값 = 0
		
		boolean isDuple = false;
		for (int i=0; i<userCnt; i++) { // userCnt = 0일경우 성립X
			if (userList[i].id.equals(id)) {
				isDuple = true;
			}
		}
		return isDuple; // false 반환
		
	}
	
	
	void joinMember() {
		
		System.out.print("[회원가입]아이디를 입력하세요 : ");
		String id = scan.next();
		System.out.print("[회원가입]패스워드를 입력하세요 : ");
		String pw = scan.next();
		
		boolean isResult = checkId(id); // false 대입
		
		if (isResult) { // isResult 가 true 일때
			System.out.println("[메세지]아이디가 중복됩니다.");
			return;
		}
		
		if (userCnt == 0) { //처음 가입할때
			userList = new User[userCnt + 1]; // userList의 크기 [0+1]
			userList[userCnt] = new User(); // userList[0] = User의 객체 대입
		}
		else {
			User[] tmp = userList;
			userList = new User[userCnt + 1];
			userList[userCnt] = new User();
			
			for(int i=0; i<userCnt; i++) {
				userList[i] = tmp[i];
			}
			tmp = null;
		}
		userList[userCnt].id = id; // userList[0].id = 입력한 id대입
		userList[userCnt].pw = pw; // userList[0].pw = 입력한 pw대입
		
		userCnt++; // userCnt = 0+1 = 1
		System.out.println("[메세지]회원가입을 축하합니다.");
		
		FileManager.getInstance().save(); // save 메서드 호출

	}

	
	int deleteMember(int identifier) {
		//identifier = 0 일때
		User[] tmp = userList; // tmp에 주소 공유
		userList = new User[userCnt - 1]; // userCnt = 1일경우 userList 크기 [1-1] -> [0]
		                                  // userCnt = 2일경우 userList 크기 [2-1] -> [1]
		int j = 0;
		for (int i=0; i<userCnt; i++) { // int i=0; i<1; i++ -> i=0일때만 성립
			                            // int i=0; i<2; i++ -> i=0, i=1일때만 성립 
			if (i != identifier) { // userCnt = 1 일때는 성립X
				                   // userCnt = 2 일때는 성립O -> i = 1 != identifier = 0
				userList[j++] = tmp[i]; // userList[1] = tmp[1] 대입
			}
		}
		
		userCnt--; // 2-1 = 1
		tmp = null;
		identifier = -1;
		
		System.out.println("[메세지]탈퇴되었습니다.");

		FileManager.getInstance().save();
		
		return identifier;
		
	}
	
	// (테스트생성용 메서드)  : 테스트 데이타 > 더미
	void setDummy() {
		
		String[] ids  = {"user1"  ,  "user2",     "user3",    "user4",    "user5"};
		String[] pws  = {"1111"   ,   "2222",      "3333",     "4444",    "5555"};
		String[] accs = {"1234567",  "2345692",  "1078912",   "2489123",  "7391234"};
		int[] moneys  = { 87000   ,     12000,    49000,        34000,     128000};
		
		userCnt = 5;
		userList = new User[userCnt];
		
		for (int i=0; i<userCnt; i++) {
			userList[i] = new User();
			userList[i].id = ids[i];
			userList[i].pw = pws[i];
			userList[i].acc[0] = new Account();
			userList[i].acc[0].accNumber = accs[i];
			userList[i].acc[0].money = moneys[i];
			userList[i].accCnt++;
		}
		
	}
	
}
