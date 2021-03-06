package step8_03.atm_v3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager { // 싱글턴 패턴

	private FileManager() {} // 기본생성자 만들기
	private static FileManager instance = new FileManager(); //자기 자신의 인스턴스 생성
	public static FileManager getInstance() { // getter 메서드 만들기
		return instance;
	}
	
	String fileName = "atmData.txt";
	UserManager userManager = UserManager.getInstance();
	
	
	boolean loadData() {
		
		boolean isFinish = false;
		
		File file = new File(fileName);
		FileReader fr = null;
		BufferedReader br = null;
		
		if (file.exists()) {
			
			userManager.userList = null;
			
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				
				String strCnt = br.readLine();
				int count = Integer.parseInt(strCnt);
				
				userManager.userList = new User[count];
				userManager.userCount = 0;
				
				while (true) {
					
					String line = br.readLine();
					if(line == null) break;
					
					String[] info = line.split("/");
					int size = info.length;
					
					User user = null;
					if (size == 3) {
						user = new User(info[0], info[1], null, 0);
					}
					else {
						String id = info[0];
						String password = info[1];
						int accCount = Integer.parseInt(info[2]);
						Account[] accList = new Account[accCount];
						int j = 3;
						for(int i=0; i<accCount; i++) {
							accList[i] = new Account();
							accList[i].number = info[j];
							accList[i].money = Integer.parseInt(info[j+1]);
							j += 2;
						}
						user = new User(id, password, accList, accCount);
					}
					userManager.userList[userManager.userCount] = user;
					userManager.userCount++; 
					
				}
				isFinish = true;
				System.out.println("[메세지]파일을 로드하였습니다.\n");
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (br != null) {try {br.close();} catch (IOException e) {}}
				if (fr != null) {try {fr.close();} catch (IOException e) {}}
			}
		}
		return isFinish;
		
	}
	
	
	void saveData() { // 정보를 저장 하는 메서드
			
		if (userManager.userCount == 0) return; // userCount == 0 일 경우 (아이디가 1개도 없을 경우)  return;
		
		String data = ""; // 문자열 생성
		data += userManager.userCount; // data에 userCount = 1의 값 대입
		data += "\n"; // 줄 내리기
		
		for (int i=0; i<userManager.userCount; i++) { // ex) userCount = 1일 경우, i=0; i<1; i++ -> i=0일때만 성립
			User user = userManager.userList[i]; // user에 userList[0]의 주소 공유
			data += user.id;		data += "/"; // id/password/accCount 형식으로 저장
			data += user.password;	data += "/";
			data += user.accCount;
			
			if (user.accCount > 0) { // 계좌가 있을 경우 ex) accCount = 2
				data += "/";	
				for (int j=0; j<user.accCount; j++) { // j=0; j<2; j++ -> j=0,1일때 성립
					Account acc = user.accList[j]; // acc 클래스 변수에 accList[0], accList[1] 주소 공유
					data += acc.number; // id/password/accCount/acc.number/acc.money 형식으로 저장
					data += "/";
					data += acc.money;
					if (j != user.accCount - 1) { // j=0일때, user.accCount(2) -1 = 1 성립 -> + "/"
						data += "/";
					}
				}
			}

			if (i != userManager.userCount - 1) { // i=0이고, userCount = 1 일때, 0 != 0 성립X -> 사용자가 2명부터는 줄 내리기 
				data += "\n";
			}
		}
		
		FileWriter fw = null; // 파일명은 위의 클래스에서 만듬
		
		
		try {
			fw = new FileWriter(fileName); // 파일을 생성하는 객체
			fw.write(data); // write메서드로 문자열 데이터 입력
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fw != null) {try {fw.close();} catch (IOException e) {}}
		}
	}

	
}
