
package ATM;

import java.util.*;

class Select {
	
	void menu() {				// �޴� ���
		System.out.println("1. ���\n2. �Ա�\n3. ���\n4. ��ȸ\n5. ������");
	}
}

class Login {
	
	void login() {				// ���
		
	}
}

class Deposit {
	
	void moneyIn() {			// �Ա�
		
	}	
}

class Withdrawal {

	void moneyOut() {			// ���
		
	}
}

class Balance {
	
	void balance() {			// ��ȸ
		
	}
}

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i;
		boolean a = true;
		
		Select s = new Select();
		s.menu();
		
		Login login = new Login();
		Deposit dep = new Deposit();
		Withdrawal wd = new Withdrawal();
		Balance bal = new Balance();
		
		Scanner sc = new Scanner(System.in);
		
		while (a) {
			i = sc.nextInt();
			switch(i) {
			case 1: 
				login.login();
				break;
			case 2:
				dep.moneyIn();
				break;
			case 3:
				wd.moneyOut();
				break;
			case 4:
				bal.balance();
				break;
			case 5:
				System.out.println("End.");
				a = false;
				break;
			default:
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
				break;
			}
		}
		
	}

}
