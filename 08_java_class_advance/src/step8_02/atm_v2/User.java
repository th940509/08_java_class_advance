package step8_02.atm_v2;

public class User { // userList
	
	Account[] acc = new Account[UserManager.getInstance().ACC_MAX_CNT];	// acc[3]
	//Account[] acc = new Account[3];
	int accCnt;	

	String id;											
	String pw;											
	
}


