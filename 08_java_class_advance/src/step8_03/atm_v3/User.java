package step8_03.atm_v3;

public class User {
	
	String id;
	String password;
	Account[] accList;
	int accCount;
	
	
	User() {}
	
	User(String id, String password) { // 오버로딩
		this.id = id;
		this.password = password;
	}
	
	User(String id, String password, Account[] accList, int accCount) {
		this.id = id;
		this.password = password;
		this.accList = accList;
		this.accCount = accCount;
	}
	
	void printOneUserAllAccounts() {
		if(accCount == 0) {
			System.out.println(id + "\t" + password + "\t계좌를 개설해주세요.");
		}
		else if(accCount > 0) {
			System.out.print(id + "\t" + password + "\t");
			for (int i=0; i<accCount; i++) {
				System.out.print(accList[i].number + "/" + accList[i].money + "원;");
			}
			System.out.println();
		}
	}
	
	
}
