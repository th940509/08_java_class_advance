package step8_01.atm_v1;
import java.util.Random;
import java.util.Scanner;

public class ATM {
	
	Scanner scan = new Scanner(System.in);
	Random ran   = new Random();
	UserManager userManager = new UserManager();
	int identifier = -1;
	
	void printMainMenu() { // 2
				
		while (true) {
			
			System.out.println("\n[ MEGA ATM ]");
			System.out.print("[1.로그인] [2.로그아웃] [3.회원가입] [4.회원탈퇴] [0.종료] : ");
			int sel = scan.nextInt();
			
			if      (sel == 1) 	    login(); 
			else if (sel == 2) 		logout();
			else if (sel == 3) 		join(); 
			else if (sel == 4) 		leave();
			else if (sel == 0) 		break;
			
		}
		
		System.out.println("[메시지] 프로그램을 종료합니다.");
		
	}
	
	
	void login() {
		
		identifier = userManager.logUser();
		
		if (identifier != -1) {
			printAccountMenu();
		}
		else {
			System.out.println("[메세지] 로그인실패.");
		}
		
	}
	
	
	void join() { 
		
		userManager.addUser();
	}
	
	
	void logout() {
		
		if (identifier == -1) {
			System.out.println("[메시지] 로그인을 하신 후 이용하실 수 있습니다.");
		}
		else {
			identifier = -1;
			System.out.println("[메시지] 로그아웃 되었습니다.");
		}
		
	}
	
	
	void leave() {
		
		userManager.leave();
		
	}
	
	
	void printAccountMenu() { // identifier = 0 /(2) identifier = 1
		
		while (true) {
			
			System.out.print("[1.계좌생성] [2.계좌삭제] [3.조회] [0.로그아웃] : ");
			int sel = scan.nextInt();
			
			String makeAccount = Integer.toString(ran.nextInt(90001) + 10000); // 10000 ~ 90000까지 랜덤 숫자 생성
			 
			
			if (sel == 1) { // 계좌생성
				if (userManager.user[identifier].accCount == 0) { 
					userManager.user[identifier].acc = new Account[1]; // [1]만큼의 클래스 배열 생성
					
					userManager.user[identifier].acc[0] = new Account(); // 객체 대입
					userManager.user[identifier].acc[0].number = makeAccount; // number에 위의 랜덤 숫자 대입
				
				}
				else { //한 아이디에 여러개 계좌를 생성할때
					Account[] temp = userManager.getUser(identifier).acc; // temp에 user(0).acc 주소 공유
					int tempAccCount = userManager.getUser(identifier).accCount; // tempAccCount에 1대입
					userManager.user[identifier].acc = new Account[tempAccCount+1];  // new Account[1+1]
					for (int i = 0; i < tempAccCount; i++) { // int i=0; i<1; i++ 일때 i=0일때만 성립
						userManager.user[identifier].acc[i] = temp[i]; // user[0].acc[0]에 temp[0] 대입
					}
					userManager.user[identifier].acc[tempAccCount] = new Account(); //user[0].acc[1] 에 객체 대입
					userManager.user[identifier].acc[tempAccCount].number = makeAccount; // user[0].acc[1].number에 랜덤 숫자 생성
					
				}
				userManager.user[identifier].accCount++; // accCount 0+1 = 1 // 1+1 = 2
				System.out.println("[메시지]'"+makeAccount +"'계좌가 생성되었습니다.\n");
			} 	
			else if (sel == 2) { // 계좌삭제
				
				if (userManager.user[identifier].accCount == 0) {
					System.out.println("[메시지] 더 이상 삭제할 수 없습니다.\n");
					continue;
				}
				
				if ( userManager.user[identifier].accCount == 1) { // 1개일때 
					System.out.println("[메시지] 계좌번호 :'"+ userManager.user[identifier].acc[0].number+"' 삭제 되었습니다.\n");
					userManager.user[identifier].acc = null;
				}
				else { // 계좌가 여러개 일때
					
					System.out.print("삭제 하고 싶은 계좌 번호를 입력하세요 : ");
					String deleteAccount = scan.next();
					int tempAccCount = userManager.user[identifier].accCount; // tempAccCount = 2 대입
					int delIdx = -1;
					for (int i = 0; i <tempAccCount; i++) { //i=0; i<2; i++ 이면 i=0,1일때만 성립
						if (deleteAccount.equals(userManager.user[identifier].acc[i].number)) {
							delIdx = i; // acc[0],acc[1] 중 일치하는 것이 있을 시 index를 delIdx에 대입
						}
					}
					
					if ( delIdx == -1 ) { // 위의 for문이 성립되지 않았을 경우
						System.out.println("[메시지] 계좌번호를 확인하세요.\n");
						continue;
					}
					else { // 위의 for문이 성립된 경우
						System.out.println("[메시지] 계좌번호 :'"+ userManager.user[identifier].acc[delIdx].number+"' 삭제 되었습니다.\n");
						
						Account[] temp = userManager.user[identifier].acc; //temp에 user[0].acc 주소 공유
						userManager.user[identifier].acc = new Account[tempAccCount-1]; // user[0].acc에 [2-1] Account 배열 생성
						                                                                // user[0].acc의 크기는 [1]
						
						for (int i = 0; i < delIdx; i++) { // 삭제할 delIdx = 1일 경우, i=0일때만 성립
							                               // ser[0].acc[0] = temp[0]대입
							userManager.user[identifier].acc[i] = temp[i];
						}
						for (int i = delIdx; i < tempAccCount - 1; i++) { // int i=1; i<1; i++ 성립X
							userManager.user[identifier].acc[i] = temp[i+1];
						}
					}
					
				}
				userManager.user[identifier].accCount--; // 2-1 =1
				
			}
			
			else if (sel == 3) { // 조회
				if (userManager.user[identifier].accCount == 0) {
					System.out.println("[메시지] 생성된 계좌가 없습니다.\n");
				}
				else {
					userManager.user[identifier].printAccount(); // int i=0; i<1; i++ 일때 i=0일때 성립 acc[0].printOwnAccount -> number : money 출력
				}
			}   
			else if (sel == 0) {
				logout();
				break;
			}
			
		}
		
	}	
}
