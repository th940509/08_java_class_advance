package step8_01.atm_v1;

public class User {
	
	String id;
	int accCount;
	Account[] acc; // 클래스 배열 생성
	
	void printAccount() {
		
		for (int i = 0; i < accCount; i++) {
			acc[i].printOwnAccount();
		}	
		
	}
	
}
