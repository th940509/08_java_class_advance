package step8_02.atm_v2;

import java.util.Random;
import java.util.Scanner;

public class AccountManager { // 싱글턴패턴

	private AccountManager() {}
	private static AccountManager instance = new AccountManager();
	public static AccountManager getInstance() {
		return instance;
	}
	
	Scanner scan = new Scanner(System.in);
	Random ran = new Random();
	UserManager um = UserManager.getInstance();

	void createAcc(int identifier) { // 계좌생성 현재 indentifier = 0
		
		int accCntByUser = um.userList[identifier].accCnt; // accCntByUser = 0
		
		
		if (accCntByUser == 3) {
			System.out.println("[메세지]계좌개설은 3개까지만 가능합니다.");
			return;
		
	}
		
		
		um.userList[identifier].acc = new Account[accCntByUser+1];
		
		um.userList[identifier].acc[accCntByUser] = new Account(); // userList[0].acc[0] = Account 객체 대입
		
		
		String makeAccount = "";
		
		while (true) {
			makeAccount = ran.nextInt(9000000) + 1000001 + "";	// 1000001 ~ 10000000 랜덤 숫자? / 숫자->문자열 형변환
			if (!um.getCheckAcc(makeAccount)){ // getCheckAcc가 false 일때 반복문 break; 
			break;
			}
		}
		
		um.userList[identifier].acc[accCntByUser].accNumber = makeAccount; // userList[0].acc[0].accNumber에 makeAccount 대입
		um.userList[identifier].accCnt++; // accCount = 0+1 = 1
		System.out.println("[메세지]'" + makeAccount + "'계좌가 생성되었습니다.\n");
	}
	
	
	void printAcc(int identifier) { //조회
		
		User temp = um.userList[identifier]; // temp에 userList[0] 주소 공유
		System.out.println("====================");
		System.out.println("ID : " + temp.id); // id 출력
		System.out.println("====================");
		for (int i=0; i<temp.accCnt; i++) { // accNumber, money 출력
			System.out.println("accNumber:" +temp.acc[i].accNumber + " / money: " + temp.acc[i].money);
		}
		System.out.println("=============================\n");
		
	}
	
	int printAccNumber(int identifier) { // 계좌삭제구현시 필요한 메서드 생성
		int deleteAccIndex = -1;
		if(UserManager.getInstance().userList[identifier].accCnt > 0) { // 계좌가 있는 경우
			for(int i=0; i<UserManager.getInstance().userList[identifier].accCnt; i++) {
			System.out.println("[" + (i+1) + "]"+ UserManager.getInstance().userList[identifier].acc[i].accNumber);
			}
			System.out.println("[메세지] 계좌를 선택하세요 >> ");
			int choice = scan.nextInt();
			choice --;
			deleteAccIndex = choice;
		}
		return deleteAccIndex;
	}
	
}
