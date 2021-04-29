package step8_02.atm_v2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager { // 싱글턴 패턴
	
	private FileManager() {} // private 기본 생성자 생성
	private static FileManager instance = new FileManager(); // 내부에서 static으로 자기 자신의 인스턴스 생성
	public static FileManager getInstance() { // instance를 반환할 getter메서드 생성
		return instance;
	}
	
	String fileName = "ATM.txt";
	String data = "";
	UserManager um = UserManager.getInstance();
	
	void setData() {
		
		data = "";
		int userCount = um.userCnt; // userCnt = 1 -> userCount = 1
		data += userCount; // data = data + 1 -> data = "" + 1 
		data += "\n";
		
		for (int i=0; i<userCount; i++) { // int i=0; i<1; i++ -> i=0일때만 성립
			data += um.userList[i].id; // um.userList[0].id 저장
			data += "\n";
			data += um.userList[i].pw; // um.userList[0].pw 저장
			data += "\n";
			data += um.userList[i].accCnt; // accCnt = 0 저장 // 1로 변경
			data += "\n";
			
			if (um.userList[i].accCnt == 0) { // 계좌생성 후 성립X
				data += "0\n";
			}
			else { // 계좌 생성 후 
				for (int j=0; j<um.userList[i].accCnt; j++) { // int j=0; j<1; j++ ->  j=0일때만 성립
					data += um.userList[i].acc[j].accNumber; // data에 accNumber 대입
					data += "/";
					data += um.userList[i].acc[j].money; // money = 0 대입 -> accNumber/money
					if (j != um.userList[i].accCnt-1) {
						data += ",";
					}
				}
				data += "\n";
			}
		}
		
	}
	
	
	void save() {
		
		setData();
		
		FileWriter fw = null; // 파일 입력
		
		try {
			fw = new FileWriter(fileName);
			fw.write(data);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(fw != null) {try {fw.close();} catch (IOException e) {}}
		}
		
	}
	
	
	void load() {
		
		File file = new File(fileName);
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			
			if (file.exists()) {
				
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				while(true) {
					String line = br.readLine();
					if(line == null) {
						break;
					}
					data += line;
					data += "\n";
				}
				
				String[] tmp = data.split("\n");
				um.userCnt = Integer.parseInt(tmp[0]);
				um.userList = new User[um.userCnt];
				for(int i=0; i<um.userCnt; i++) {
					um.userList[i] = new User();
				}
				
				int j = 0;
				for(int i=1; i<tmp.length; i+=4) {
					String id = tmp[i];
					String pw = tmp[i+1];
					int accCnt = Integer.parseInt(tmp[i+2]);
					um.userList[j].id = id;
					um.userList[j].pw = pw;
					um.userList[j].accCnt = accCnt;
					String accInfo = tmp[i+3];
					if(accCnt == 1) {
						String[] temp = accInfo.split("/");
						String acc = temp[0];
						int money = Integer.parseInt(temp[1]);
						um.userList[j].acc[0] = new Account();
						um.userList[j].acc[0].accNumber = acc;
						um.userList[j].acc[0].money = money;
					}
					if(accCnt > 1){
						String[] temp = accInfo.split(",");
						for(int k=0; k<temp.length; k++) {
							String[] parse = temp[k].split("/");
							String acc = parse[0];
							int money = Integer.parseInt(parse[1]);
							um.userList[j].acc[k] = new Account();
							um.userList[j].acc[k].accNumber = acc;
							um.userList[j].acc[k].money = money;
						}
					}
					j++;
				}
			}
			else {
				//um.setDummy();
				setData();
				save();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {try {br.close();} catch (IOException e) {}}
			if (fr != null) {try {fr.close();} catch (IOException e) {}}
		}
	}
	
}





