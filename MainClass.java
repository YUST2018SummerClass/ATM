package ATM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MainClass {
	
	static void menu1() {				// ù��° �޴� ���
		System.out.println("========================");
		System.out.println("1.���\t2.�α���\t3.������");
	}
	
	static void menu2() {				// ���� �޴� ���
		System.out.println("========================");
		System.out.println("1.�Ա�\t2.���\t3.��ȸ\t4.������");
	}
	
	private static void deposit(Statement stmt, String id, float moneyIn, float money) {
		
		try {
			
			System.out.println("�Ա��Ͻ� �ݾ��� �Է��ϼ���");
			Scanner sc = new Scanner(System.in);
			moneyIn = sc.nextFloat();
			ResultSet rs = stmt.executeQuery("select userMoney from userinfo where userID = '"+id+"';");
			while (rs.next()) {
				money = rs.getFloat("userMoney") + moneyIn;
				if (money < 1000000) {
					System.out.println(moneyIn+"���� �ԱݵǾ����ϴ�.");
				} else if (money >= 1000000){
					System.out.println("�ѵ��� �ʰ��ϼ̽��ϴ�.");
				} 
			}
			stmt.executeUpdate("update userinfo set userMoney = '"+money+"' where userID = '"+id+"';");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private static void withdraw(Statement stmt, String id, float moneyOut, float money) {
		
		float a = 0;
		try {
			System.out.println("����� �ݾ��� �Է��ϼ���");
			Scanner sc = new Scanner(System.in);		// ����� �ݾ� �Է�
			moneyOut = sc.nextFloat();					// �Է��� �ݾ��� moneyOut ������ ����
			ResultSet rs = stmt.executeQuery("select userMoney from userinfo where userID = '"+id+"';");	// id�� �ش��ϴ� ���� �ݾ��� �ҷ���
			while (rs.next()) {
				money = rs.getFloat("userMoney") - moneyOut;
				a = money;
				if (rs.getFloat("userMoney") >= moneyOut) {						// ���� �ܾ��� ��ݱݾ׺��� ������ ���������� ���
					System.out.println(moneyOut+"���� ����Ͽ����ϴ�.");
				} else {
					System.out.println("�ܾ��� �����մϴ�.");	// ���� �ܾ��� ��ݱݾ׺��� ���ٸ� �ܾ��� �����ϴٴ� �޼���
					a = money + moneyOut;
				}
			}
			stmt.executeUpdate("update userinfo set userMoney = '"+a+"' where userID = '"+id+"';");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private static void balance(Statement stmt, String id) {
		
		try {
			ResultSet rs = stmt.executeQuery("select userMoney from userinfo where userID = '"+id+"';");
			while (rs.next()) {
				System.out.println("�ܾ��� "+rs.getFloat("userMoney")+"�� �Դϴ�.");
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
		Statement stmt2 = null;
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
		
		menu1();
			
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
					menu1();
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

					menu2();

								
					while (a) {
						j = sc.nextInt();
						switch(j) {
						case 1: 
							deposit(stmt, id, moneyIn, money);
							menu2();
							break;
						case 2:
							withdraw(stmt, id, moneyOut, money);
							menu2();
							break;
						case 3:
							balance(stmt, id);
							menu2();
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
	}

}
