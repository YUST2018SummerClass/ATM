import java.util.*;
import java.sql.*;

class Select {
	
	void menu1() {				// ù��° �޴� ���
		System.out.println("========================");
		System.out.println("1.���\t2.�α���\t3.������");
	}
	
	void menu2() {				// ���� �޴� ���
		System.out.println("========================");
		System.out.println("1.�Ա�\t2.���\t3.��ȸ\t4.������");
	}
}


public class AtmMain {
	
	
	private static void balance(Statement stmt, String id) {		// �ܾ� ��ȸ
		
		try {
			
			ResultSet srs = stmt.executeQuery("select userMoney from userinfo where userID = '"+id+"';");
			while (srs.next()) {
				System.out.println("�ܾ��� "+srs.getFloat("userMoney")+"�� �Դϴ�.");	
			}	
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private static void deposit(Statement stmt, String id, float moneyIn, float money) {	// �Ա�
		
		try {
			
			System.out.println("�Ա��Ͻ� �ݾ��� �Է��ϼ���");
			Scanner sc = new Scanner(System.in);			// �Ա��� �ݾ� �Է��ϼ���
			moneyIn = sc.nextFloat();						// �Է��� �ݾ��� moneyIn ������ ����
			ResultSet srs = stmt.executeQuery("select userMoney from userinfo where userID = '"+id+"';");	// id�� �ش��ϴ� ���� �ݾ��� �ҷ���
			while (srs.next()) {
				money = srs.getFloat("userMoney") + moneyIn;		// �������� �ݾ׿� �Է��� �ݾ��� ����
				if (money < 1000000) {
					System.out.println(moneyIn+"���� �ԱݵǾ����ϴ�.");
					stmt.executeUpdate("update userinfo set userMoney = '"+money+"' where userID = '"+id+"';");		// ���� ���� �����ͺ��̽��� ����
				} else if (money >= 1000000){
					System.out.println("�ѵ��� �ʰ��ϼ̽��ϴ�.");
				} else {
					System.out.println("�߸� �Է��ϼ̽��ϴ�.");
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private static void withdraw(Statement stmt, String id, float moneyOut, float money) {		// ���
		
		try {
			
			System.out.println("����� �ݾ��� �Է��ϼ���");
			Scanner sc = new Scanner(System.in);		// ����� �ݾ� �Է�
			moneyOut = sc.nextFloat();					// �Է��� �ݾ��� moneyOut ������ ����
			ResultSet srs = stmt.executeQuery("select userMoney from userinfo where userID = '"+id+"';");	// id�� �ش��ϴ� ���� �ݾ��� �ҷ���
			while (srs.next()) {
				if (srs.getFloat("userMoney") >= moneyOut) {						// ���� �ܾ��� ��ݱݾ׺��� ������ ���������� ���
					System.out.println(moneyOut+"���� ����Ͽ����ϴ�.");
					money = srs.getFloat("userMoney") - moneyOut;		// �������� �ݾ׿��� �ӷ��� �ݾ��� ��
					stmt.executeUpdate("update userinfo set userMoney = '"+money+"' where userID = '"+id+"';");		// �� ���� �����ͺ��̽��� ����
				} else {
					System.out.println("�ܾ��� �����մϴ�.");							// ���� �ܾ��� ��ݱݾ׺��� ���ٸ� �ܾ��� �����ϴٴ� �޼���
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		int i;
		int j;
		boolean a = true;
		Connection conn;
		Statement stmt = null;
		String id = null;
		String pass = null;
		float moneyIn = (float) 0;
		float moneyOut = (float) 0;
		float money = (float) 0;
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/practice?useSSL=false&serverTimezone=Asia/Seoul", "root","");
			System.out.println("DB ���� �Ϸ�");
			stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC ����̹� �ε� ����" + e.getMessage());
		} catch (SQLException e) {
			System.out.println("DB ���� ����" + e.getMessage());
		}
		
		
		Select s = new Select();
		s.menu1();
		
		Scanner sc = new Scanner(System.in);
		
		while (a) {
			i = sc.nextInt();
			switch(i) {
			case 1: 
				try {
					
					Scanner sc1 = new Scanner(System.in);
					System.out.println("���̵� �Է��ϼ���: ");
					id = sc1.nextLine();
					System.out.println("��й�ȣ�� �Է��ϼ���: ");
					pass = sc1.nextLine();
					stmt.executeUpdate("insert into userinfo (userID, userPass, userMoney) values ('"+id+"','"+pass+"',0);");
					System.out.println("ȸ������ �Ϸ�.");
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				try {
					Scanner sc2 = new Scanner(System.in);
					System.out.println("�α����ϼ���.");
					System.out.println("ID : ");
					id = sc2.nextLine();
					System.out.println("Password : ");
					pass = sc2.nextLine();
					
					s.menu2();

								
					while (a) {
						j = sc.nextInt();
						switch(j) {
						case 1: 
							deposit(stmt, id, moneyIn, money);
							s.menu2();
							break;
						case 2:
							withdraw(stmt, id, moneyOut, money);
							s.menu2();
							break;
						case 3:
							balance(stmt, id);
							s.menu2();
							break;
						case 4:
							System.out.println("End.");
							a = false;
							break;
						default:
							System.out.println("�߸� �Է��ϼ̽��ϴ�.");
							break;
						}
					}
					
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				System.out.println("End.");
				a = false;
				break;
			default:
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
				break;
			}			
			
		}
		
		
	}//main


	
	
}//class

