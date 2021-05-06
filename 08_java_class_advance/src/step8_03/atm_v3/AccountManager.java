package step8_03.atm_v3;


public class AccountManager {

	private AccountManager() {}
	private static AccountManager instance = new AccountManager();
	public static AccountManager getInstance() {
		return instance;
	}
	
	UserManager userManager = UserManager.getInstance();
	
	void createAccount() { // 계좌생성
		
		User loginUser = userManager.userList[userManager.identifier]; // identifier = 0일때, loginUser에 userList[0]의 주소 공유
		
		if (loginUser.accCount == 3) {
			System.out.println("[메세지]더 이상 계좌를 생성할 수 없습니다.\n");
			return;
		}
		
		if (loginUser.accCount == 0) { // 계좌 처음 생성할때
			loginUser.accList = new Account[loginUser.accCount + 1];
			// accList => Account[]accList / Account[]accList = new Account[login.accCount +1]
			// Account[]accList = new Account[0+1] -> accList[1] 크기로 생성
		} 
		else if (loginUser.accCount  > 0) { // 여러개의 계좌를 생성할때
			Account[] temp = loginUser.accList; // temp에 accList의 주소 공유
			
			loginUser.accList = new Account[loginUser.accCount + 1]; // accList = new Account[1+1] -> accList[2] 크기로 생성
			for(int i=0; i<loginUser.accCount; i++) { // i=0; i<1; i++ -> i=0일때만 성립
				loginUser.accList[i] = temp[i]; // accList[0] = temp[0]의 값 공유 / 기존의 값
			}
			temp = null; // temp 값 없애기
		}
		loginUser.accList[loginUser.accCount] = new Account(); // 1) accList[0] 에 Account 객체 대입 / 2) accList[1] 에 Account 객체 대입
		
		String makeAccount = ATM.ran.nextInt(90000000) + 10000001 +""; // 10000001 ~ 100000000 사이의 랜덤 숫자 +"" -> 문자열 형변환
		loginUser.accList[loginUser.accCount].number = makeAccount; // 1) accList[0].number 에 위의 랜덤 숫자 대입 / 2) accList[1]에 랜덤 숫자 대입
		loginUser.accList[loginUser.accCount].money = 0;
		
		loginUser.accCount++; // 1) 0+1 = 1 / 2) 1+1 =2
		System.out.println("[메세지]계좌가 생성되었습니다.\n");
		
		FileManager.getInstance().saveData(); // 파일저장
		
	}
	
	
	int showAccList(String msg) { // 출금, 입금, 이체, 삭제 시 모두 사용 하는 메서드
		
		int loginAccIndex = -1;

		User loginUser = userManager.userList[userManager.identifier]; // identifier=0일때, loginUser에 userList[0]의 주소 공유
		
		if (loginUser.accCount > 0) { // 계좌가 있을 경우
			for (int i=0; i<loginUser.accCount; i++) { // i=0; i<1; i++ (계좌1개일 경우) i=0일때만 성립
				System.out.println("[" + (i+1) + "]" + loginUser.accList[i].number); // [0+1] accList[0].number 출력
			}
			
			System.out.print("[" + msg + "]내 계좌를 메뉴에서 선택하세요 : "); // msg = 입금 -> [입금] 출력
			loginAccIndex = ATM.scan.nextInt(); // loginAccindex에 위의 출력된 '[0+1] accList[0].number'에서 1을 대입
			loginAccIndex--; // 1-1 = 0
		}
		
		return loginAccIndex; // 0 반환 : accList의 index 값
		
	}
	
	
	void income() { // 입금하기
		
		int loginAccIndex = showAccList("입금"); // loginAccIndex = 0;
		if (loginAccIndex == -1) {
			System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
			return;
		}
		
		System.out.print("[입금]금액을 입력하세요 : ");
		int money = ATM.scan.nextInt();
		
		userManager.userList[userManager.identifier].accList[loginAccIndex].money += money; // userList[0].accList[0].money에 위의 입력한 입금 금액 대입
		System.out.println("[메세지]입금을 완료하였습니다.\n");
		
		FileManager.getInstance().saveData(); // 파일저장
		
	}
	
	
	void outcome() { // 출금하기
		
		int loginAccIndex = showAccList("출금"); // loginAccIndex = 0;
		if (loginAccIndex == -1) {
			System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
			return;
		}
		
		System.out.print("[출금]금액을 입력하세요 : ");
		int money = ATM.scan.nextInt();
		
		if (userManager.userList[userManager.identifier].accList[loginAccIndex].money < money) { // userList[0].accList[0].money가 위의 입력한 money보다 작을 경우
			System.out.println("[메세지]계좌잔액이 부족합니다.\n");
			return;
		}
		
		userManager.userList[userManager.identifier].accList[loginAccIndex].money -= money; // userList[0].accList[0].money에서 위의 money 빼기
		System.out.println("[메세지]출금을 완료하였습니다.\n");
		
		FileManager.getInstance().saveData(); // 파일저장
		
	}
	
	
	int checkAcc(String transAccount) { // 이체 시 사용 
		
		int check = -1;
		for (int i=0; i<userManager.userList.length; i++) { // i=0; i<1; i++ -> userList.length[1]일때 i=0일때만 성립
			if (userManager.userList[i].accList != null) { // 계좌가 생성이 되어 있을때 성립
				for (int j=0; j<userManager.userList[i].accCount; j++) { // j=0; j<1; j++ -> 계좌가 한개일때 accCount = 1
					                                                     // j=0일때만 성립
					if (transAccount.equals(userManager.userList[i].accList[j].number)) { // 입력한 계좌번호와 userList[0].accList[0].number가 일치할때
						check = i;                                                        // check에 i값 대입 -> check = 0
					}
				}
			}
		}
		return check; // check 값  = 0 반환
		
	}
	
	
	int getAccIndex(int transUserIndex, String transAccount) { // 이체 시 사용 (transUserIndex = 0 / transAccount = 이체 시 입력한 계좌번호)
		
		int accIndex = 0;
		
		for (int i=0; i<userManager.userList[transUserIndex].accCount; i++) { // i=0; i<userList[0].accCount = 1 -> i<1; i++ -> i=0일때만 성립
			if (transAccount.equals(userManager.userList[transUserIndex].accList[i].number)) {
				accIndex = i; // transAccount(입력한 계좌번호)와 userList[0].accList[0].number(저장되어 있는 계좌번호)가 일치할 경우,
				              // accIndex에 i=0 값 대입
			}
		}
		
		return accIndex; // accIndex = 0 반환
		
	}
	
	
	void transfer() { // 이체하기
		
		int loginAccIndex = showAccList("이체"); // loginAccIndex = 0;
		if (loginAccIndex == -1) {
			System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
			return;
		}		
		
		System.out.print("[이체]이체할 '계좌번호'를 입력하세요 : ");
		String transAccount = ATM.scan.next();
		
		int transUserIndex = checkAcc(transAccount); // transUSerIndex = 0
		if (transUserIndex == -1) {
			System.out.println("[메세지]'계좌번호'를 확인해주세요.\n");
			return;
		}
		
		int transAccIndex = getAccIndex(transUserIndex, transAccount); // transUserIndex = 0 (int) / transAccount = 입력한 계좌번호 (string)
		                                                               // transAccIndex에 accIndex = 0 값 대입
		
		System.out.print("[이체]금액을 입력하세요 : ");
		int money = ATM.scan.nextInt();
		
		if (money > userManager.userList[userManager.identifier].accList[loginAccIndex].money) { 
			System.out.println("[메세지]계좌잔액이 부족합니다.\n");
			return;
		}
		
		userManager.userList[userManager.identifier].accList[loginAccIndex].money -= money;
		userManager.userList[transUserIndex].accList[transAccIndex].money += money;
		System.out.println("[메세지]이체를 완료하였습니다.\n");
		
		FileManager.getInstance().saveData(); // 파일 저장
	}
	
	
	void lookupAcc() { // 계좌조회
		userManager.userList[userManager.identifier].printOneUserAllAccounts();
	}

	
	void deleteAcc() { // 계좌삭제
		
		int loginAccIndex = showAccList("삭제"); // 삭제할 계좌번호의 인덱스가 반환 -> ex = 0
		if (loginAccIndex == -1) {
			System.out.println("[메세지]계좌를 먼저 생성해주세요.\n");
			return;
		}
		
		User user = userManager.userList[userManager.identifier]; // user클래스 변수에 userList[0] 객체 대입
		
		if (user.accCount == 1) { // 계좌가 1개일때, null로 없애기
			user.accList = null;
		}
		else if(user.accCount > 1) { // 계좌가 2개 이상 일때, ex) accCount=2일때
			Account[] acc = user.accList; // acc배열에 accList 주소 공유
			
			user.accList = new Account[user.accCount - 1]; // accList 배열 크기 줄여서 다시 생성
			int j = 0;
			for (int i=0; i<user.accCount; i++) { // i=0; i<2; i++ -> i= 0,1일때 성립
				if (i != loginAccIndex) { // i의 값이 위의 loginAccIndex(삭제할 계좌번호의 인덱스)와 다를 경우
					user.accList[j] = acc[i]; // 새로 생성한 accList[j] 배열에 acc[i]의 주소 공유
					j = j + 1; // j++
				}
			}
			acc = null;
		}
		user.accCount--; 
		
		System.out.println("[메세지]계좌삭제를 완료하였습니다.\n");
		
		FileManager.getInstance().saveData(); // 파일 저장
	
	}
	
	
}
