package step8_02.atm_v2;

import java.util.Scanner;

public class ATM {
	
	Scanner scan = new Scanner(System.in);
	int identifier = -1;
	UserManager um = UserManager.getInstance(); // instance 객체 대입,,?
	
	void play() { // 2
		
		FileManager.getInstance().load();
		UserManager.getInstance().printAllUser();
		
		while (true) {
			
			System.out.println("[ATM]");
			System.out.println("[1.회원가입]\n[2.로그인]\n[0.종료]");
			System.out.print("메뉴 선택 : ");
			int sel = scan.nextInt();
			
			if		(sel == 1)  join();
			else if (sel == 2)  login();  
			else if (sel == 0)  break;
			
		}
		
	}
	
	
	void login() {
		
		identifier = um.logUser();
		if (identifier != -1) loginMenu();
		else 				  System.out.println("[메세지]아이디와 패스워드를 확인해주세요.");

	}
	
	
	void loginMenu() {
		
		while (true) {
			
			System.out.println("[" + um.userList[identifier].id + "님, 환영합니다.]");
			System.out.println("[1.계좌생성]\n[2.계좌삭제]\n[3.조    회]\n[4.회원탈퇴]\n[0.로그아웃]");
			System.out.println("메뉴 선택 : ");
			int selectMenu = scan.nextInt();

			if (selectMenu == 1) { // 계좌 생성
				AccountManager.getInstance().createAcc(identifier);
				FileManager.getInstance().save();
			}
			else if (selectMenu == 2) { // 계좌삭제 (구현해보기) 2021/04/29 17:40 ~ 18:33 (실행X)
				if (UserManager.getInstance().userList[identifier].accCnt == 0) { // 계좌를 생성하지 않은 경우
					System.out.println("[메세지] 더 이상 삭제할 수 없습니다.\n");
				}
				if (UserManager.getInstance().userList[identifier].accCnt == 1) { // 계좌가 1개일 경우
					System.out.println("[메세지] 계좌번호: " + UserManager.getInstance().userList[identifier].acc[0].accNumber + " 삭제 되었습니다.");
				}
				else {
					System.out.println("삭제 하고 싶은 계좌번호를 입력하세요: ");
					String deleteAccount = scan.next();
					
					int tempAccCount = UserManager.getInstance().userList[identifier].accCnt;
					int delIdx = -1;
					for (int i=0; i<tempAccCount; i++) {
						if(deleteAccount.equals(Integer.parseInt(UserManager.getInstance().userList[identifier].acc[i].accNumber))) {
							delIdx = i;
						}
					}
					
					if (delIdx == -1) {
						System.out.println("[메세지] 계좌번호를 확인하세요 \n");
						continue;
					}
					else {
						System.out.println("[메세지] 계좌번호: " +  UserManager.getInstance().userList[identifier].acc[delIdx].accNumber + "삭제되었습니다.");
						Account[] temp = UserManager.getInstance().userList[identifier].acc;
						UserManager.getInstance().userList[identifier].acc = new Account [tempAccCount-1];
						
						for(int i=0; i<delIdx; i++) {
							UserManager.getInstance().userList[identifier].acc[i] = temp[i];
						}
						for(int i=delIdx; i<tempAccCount-1; i++) {
							UserManager.getInstance().userList[identifier].acc[i] = temp[i+1];
						}
					}
				}
				UserManager.getInstance().userList[identifier].accCnt --;
			}
			else if (selectMenu == 3) { // 조회
				AccountManager.getInstance().printAcc(identifier);
			}
			else if (selectMenu == 4) { // 탈퇴
				identifier = um.deleteMember(identifier);
				break;
			}
			else if (selectMenu == 0) { // 로그아웃
				identifier = -1;
				System.out.println("로그아웃 되었습니다.");
				break;
			}
			
		}
		
	}
	
	
	void join() {
		
		UserManager.getInstance().joinMember();
		
	}
	

	
}
