package step8_03.atm_v3;

import java.util.Random;
import java.util.Scanner;

public class ATM {
	
	static Scanner scan = new Scanner(System.in);
	static Random ran = new Random();
	
	ATM() {
		
		boolean isLoad = FileManager.getInstance().loadData();
		// 테스트 데이터 생성
		if (!isLoad) {
			UserManager.getInstance().setDummy();
			FileManager.getInstance().saveData();
		}
		
	}
	
	
	void showMenu() {
		
		while(true) {
			
			printAllDataByAllUser();
			
			System.out.println("[MEGA ATM]");
			System.out.println("[1]회원가입\n[2]로그인\n[0]종료");
			System.out.print("메뉴를 선택하세요 : ");
			int choice = scan.nextInt();
			
			if		(choice == 1) 	join(); 
			else if (choice == 2) 	login(); 
			else if (choice == 0) 	break;
			
		}
		
	}
	
	
	void printAllDataByAllUser() {
		
		UserManager.getInstance().printAllUserInfo();
		
	}
	
	
	void login() { //로그인
		
		UserManager.getInstance().loginUser();
		
	}
	
	
	void join() { //회원가입
		
		UserManager.getInstance().joinUser(); 
		
	}
	
	
	
}







