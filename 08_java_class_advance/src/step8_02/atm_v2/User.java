package step8_02.atm_v2;

public class User {
	
	Account[] acc = new Account[UserManager.getInstance().ACC_MAX_CNT];	// acc[3]
	int accCnt;	

	String id;											
	String pw;											
	
}


