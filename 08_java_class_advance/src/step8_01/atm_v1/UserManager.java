package step8_01.atm_v1;

import java.util.Scanner;

public class UserManager {
	
	Scanner scan = new Scanner(System.in);
	User[] user = null; // 클래스 배열 생성 후 값은 null
	int userCount = 0;
	
	void printAllUser() {
		for(int i = 0; i < userCount; i++) {
			user[i].printAccount();
		}
	}
	
	
	
	void addUser() { //userCount의 초기값 = 0
		
		if(userCount == 0) { // 처음 가입할때
			user = new User[1]; // 클래스 배열 user 값 null -> 배열의 크기[1]로 변경
		}
		else { // 2번째 가입할때 userCount = 1
			User[] temp = user; // 위의 가입한 정보를 temp 클래스 배열에 주소 공유
			user = new User[userCount + 1]; // 클래스 배열의 값을 [1] -> [1+1]로 변경
			for(int i = 0; i < userCount; i++) { // int i=0; i<1; i++ 일때 i=0일때 성립
				user[i] = temp[i]; //user[0]에 temp[0]의 값 공유(기존 정보)
			}                      //user = new User[2]
			temp = null; // temp 값 삭제
		}
		
		
		System.out.print("[가입] 아이디를 입력하세요 : ");
		String id = scan.next();
		
		boolean isDuple = false;
		for (int i = 0; i < userCount; i++) { // userCount = 0일때는 성립X
			if (user[i].id.equals(id)) {
				isDuple = true;
			}
		}
		if (!isDuple) { // isDuple이 false일때?
			user[userCount] = new User(); //user[0] 에 User의 객체 대입
			user[userCount].id = id; // 위의 입력한 id를 user[0].id에 저장
			System.out.println("[메시지] ID : '" + id+ "' 가입 되었습니다.\n");
			userCount++; // (1) userCount 0+1 = 1 // (2) userCount = 2
		}
		else { // 위의 39번째 if문이 성립되어 isDuple이 true일 경우
			System.out.println("[메시지] '"+ id + "'은 이미 가입된 아이디 입니다.\n");
		}
		
	}
	
	
	
	User getUser(int idx) {
		
		return user[idx];
	}
	
	
	
	
	int logUser() {
		
		int identifier = -1;
		System.out.print("[입력] 아이디를 입력하세요 : ");
		String name = scan.next();
		
		for (int i = 0; i < userCount; i++) { // id를 1개 만들었을 경우 userCount = 1 / 2개일 경우 2
			                                  // i=0; i<1; i++ 일 경우 i=0일때만 성립
			if (name.equals(user[i].id)) {    // 위에 입력한 name과 user[0].id가 일치 할 경우
				identifier = i;               // identifier의 값을 user의 인덱스로 변경 -> identifier = 0
				System.out.println("[" + user[identifier].id + "] 님 로그인.\n");
			}
		}
		
		return identifier; // identifier의 값 반환 
		
	}
	
	
	
	void leave() {
		
		System.out.print("[입력] 탈퇴할 아이디를 입력하세요 : ");
		String name = scan.next();
		int identifier = -1;
		for (int i = 0; i < userCount; i++) {
			if (name.equals(user[i].id)) {
				identifier = i;	//일치하는 id의 index대입	
			}
		}
		
		if(identifier == -1) {
			System.out.println("[메시지] 아이디를 다시 확인하세요.");
			return;
		}
		
		System.out.println("ID : '" +user[identifier].id + "' 가 탈퇴되었습니다.");
		
		User[] temp = user;
		user = new User[userCount - 1];
		
		for(int i = 0; i < identifier; i++) {
			user[i] = temp[i];
		}
		for(int i =identifier; i < userCount-1; i++) {
			user[i] =temp[i + 1];
		}
		
		userCount--;
		
	}
	
}
