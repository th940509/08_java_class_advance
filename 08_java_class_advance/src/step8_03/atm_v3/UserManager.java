package step8_03.atm_v3;

public class UserManager { // 싱글턴 패턴
	
	private UserManager () {} // 기본생성자 만들기
	private static UserManager instance = new UserManager(); // 자기 자신의 인스턴스 생성
	public static UserManager getInstance() { // getter 메서드 만들기
		return instance;
	}
	
	User[] userList;
	int userCount;
	int identifier = -1;
	
	
	void printAllUserInfo() {
		
		System.out.println("아이디\t패스워드\t계좌정보");
		for (int i=0; i<userCount; i++) { //i=0; i<1; i++ -> i=0일때만 성립
			userList[i].printOneUserAllAccounts();
		}
		System.out.println("--------------------------");
		
	}
	
	
	void setDummy() {
		
		userCount = 5;
		userList = new User[userCount];
		for (int i=0; i<userCount; i++) {
			userList[i] = new User();
		}
				
		String[] a = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"};
		String[] b = {"l", "b", "c", "m", "e", "f", "g", "n", "i", "p", "k"};
		String[] c = {"s", "t", "u", "w", "v", "o", "x", "n", "q", "p", "r"};
		
		for (int i=0; i<userCount; i++) {
			String id = "";
			int rNum = ATM.ran.nextInt(a.length);
			id += a[rNum];
			rNum = ATM.ran.nextInt(b.length);
			id += b[rNum];
			rNum = ATM.ran.nextInt(c.length);
			id += c[rNum];
			
			userList[i].id = id;
		}
		
		String[] d = {"1", "8", "9", "4"};
		String[] e = {"2", "7", "0", "6"};
		String[] f = {"5", "3", "2", "7"};
		
		for (int i=0; i<userCount; i++) {
			String pw = "";
			int rNum = ATM.ran.nextInt(d.length);
			pw += d[rNum];
			rNum = ATM.ran.nextInt(e.length);
			pw += d[rNum];
			rNum = ATM.ran.nextInt(f.length);
			pw += d[rNum];
			
			userList[i].password = pw;
		}
		
		System.out.println("[메세지]더미 파일이 추가되었습니다.\n");
		
	}

	
	int checkId(String id) {
		
		int check = -1;
		for (int i=0; i<userCount; i++) { // userCount의 초기값 = 0 -> i=0; i<0; i++ 성립X
			if (userList[i].id.equals(id)) {
				check = i;
			}
		}
		return check; // -1 반환
		
	}
	
	
	void joinUser() {
		
		System.out.print("[가입]아이디를 입력하세요 : ");
		String id = ATM.scan.next();
		
		int checkedId = checkId(id); // userCount = 0(초기값)일 경우 checkId에서 -1이 반환되므로
		                             // checkedId = -1
		if (checkedId != -1) {
			System.out.println("[메세지]아이디가 중복됩니다.\n");
			return;
		}
		
		System.out.print("[가입]패스워드를 입력하세요 : ");
		String password = ATM.scan.next();
		
		if (userCount == 0) {
			userList = new User[1]; // userList를 [1] 크기로 생성
			userList[0] = new User(id, password); // User에 id와 password 저장
		}
		else if (userCount > 0) {
			User[] temp = userList;
			
			userList = new User[userCount + 1];
			for (int i=0; i<userCount; i++) {
				userList[i] = temp[i];
			}
			
			userList[userCount] = new User(id, password);
			
			temp = null;
		}
		
		userCount++; // userCount 0 + 1 = 1
		System.out.println("[메세지]회원가입을 완료하였습니다.\n");
		
		FileManager.getInstance().saveData(); // 파일저장..
		
		printAllUserInfo();
		
	}
	
	
	void leaveUser() { // 회원탈퇴
		
		if (userCount == 1) { // userCount == 1일때, (계좌가 1개밖에 없을때) null로 없애기
			userList = null;
		}
		else if (userCount > 1) { // userCount가 2 이상일때, (계좌가 2개 이상 생성되어 있을때)
			User[] temp = userList; // temp 클래스 배열에 userList의 주소 공유
			userList = new User[userCount - 1]; // userList 크기 줄여 다시 생성
			
			int j = 0;
			for (int i=0; i<userCount; i++) { // ex) userCount = 3 (계좌가 3개일 경우)일 시,
				                              // i=0; i<3; i++ -> i 0.1.2일때 성립
				if (i != identifier) { // i 가 identifier(로그인 되어 있는 아이디의 인덱스)와 다를경우
					userList[j++] = temp[i]; // userList에 temp[i]의 주소 공유
					                         // userList[j++] = userList[j] , j++ 과 같음 
				}
			}
		}
		userCount--;
		
		System.out.println("[메세지]탈퇴되었습니다.\n");
		logoutUser();
		FileManager.getInstance().saveData(); // 파일 저장
				
	}
	
	
	void loginUser() {
		
		System.out.print("[로그인]아이디를 입력하세요 : ");
		String id = ATM.scan.next();
		
		System.out.print("[로그인]패스워드를 입력하세요 : ");
		String password = ATM.scan.next();
		
		for (int i=0; i<userCount; i++) { // i=0; i<1; i++ -> i=0일때 성립
			if (userList[i].id.equals(id) && userList[i].password.equals(password)) {
				identifier = i; // userList[0].id 와 위의 id / userList[0].password와 위의 password와 동일할때
				                // identifier = 0;
			}
		}
			
		if (identifier == -1) {
			System.out.println("[메세지]아이디와 패스워드가 틀렸습니다.\n");
		}
		else {
			System.out.println("[메세지]" + userList[identifier].id + "님, 환영합니다.\n");
			afterloginMenu();
		}
		
	}
	
	
	void logoutUser() { // 뒤로 가기
		identifier = -1;
		System.out.println("[메세지]로그아웃되었습니다.\n");
	}
	
	
	void afterloginMenu() {
		
		while (true) {
			
			System.out.println("[" + userList[identifier].id + "님, 로그인]");
			System.out.println("[1]계좌생성 [2]입금하기 [3]출금하기 [4]이체하기 [5]계좌조회 "
					+ "[6]계좌삭제 [7]회원탈퇴 [0]뒤로가기");
			System.out.print("메뉴를 선택하세요 : ");
			int choice = ATM.scan.nextInt();
			
			if (choice == 1)  { // 계좌 생성
				AccountManager.getInstance().createAccount(); 
			}
			else if (choice == 2) { // 입금하기
				AccountManager.getInstance().income(); 
			}
			else if (choice == 3) { // 출금하기
				AccountManager.getInstance().outcome();
			}
			else if (choice == 4) { // 이체하기
				AccountManager.getInstance().transfer(); 
			}
			else if (choice == 5) { // 계좌조회
				AccountManager.getInstance().lookupAcc(); 
			}
			else if (choice == 6) { // 계좌삭제
				AccountManager.getInstance().deleteAcc(); 
			}
			else if (choice == 7) { // 회원탈퇴
				leaveUser();
				break;
			}
			else if (choice == 0) { // 뒤로가기
				logoutUser();
				break; 
			}
			
		}
		
	}
}









