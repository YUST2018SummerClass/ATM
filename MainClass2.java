package ATM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.Calendar;
import java.text.SimpleDateFormat;

public class MainClass2 {
	
	static void menu1() {				// ù��° �޴� ���
		System.out.println("===========================================");
		System.out.println("1.���    2.�α���    3.������");
	}
	
	static void menu2() {				// ���� �޴� ���
		System.out.println("===========================================");
		System.out.println("1.�Ա�    2.���    3.�ܾ���ȸ    4.��Ϻ���    5.������");
	}
	
	private static void deposit(Statement stmt, String id, float moneyIn, float money) {		// �Ա�
		
		try {
			
			String today = null;		// ��¥
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		    Calendar currentDate = Calendar.getInstance();
		    today = dateFormat.format(currentDate.getTime()); 
			
		    float balance = 0;
			System.out.println("�Ա��Ͻ� �ݾ��� �Է��ϼ���");
			Scanner sc = new Scanner(System.in);
			moneyIn = sc.nextFloat();
			ResultSet rs = stmt.executeQuery("select userMoney from atm where userID = '"+id+"';");
			while (rs.next()) {
				balance = rs.getFloat("userMoney");
				money = balance + moneyIn;
				if (money < 1000000) {
					System.out.println(moneyIn+"���� �ԱݵǾ����ϴ�.");
				} else if (money >= 1000000){
					System.out.println("�ѵ��� �ʰ��ϼ̽��ϴ�.");
				} 
			}
			stmt.executeUpdate("update atm set userMoney = '"+money+"' where userID = '"+id+"';");
			stmt.executeUpdate("insert into "+id+" values ("+moneyIn+", 0, "+money+", '"+today+"');");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private static void withdraw(Statement stmt, String id, float moneyOut, float money) {		// ���
		
		try {
			
			String today = null;		// ��¥
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		    Calendar currentDate = Calendar.getInstance();
		    today = dateFormat.format(currentDate.getTime()); 
			
			float a = 0;
			float balance = 0;
			System.out.println("����� �ݾ��� �Է��ϼ���");
			Scanner sc = new Scanner(System.in);		// ����� �ݾ� �Է�
			moneyOut = sc.nextFloat();					// �Է��� �ݾ��� moneyOut ������ ����
			ResultSet rs = stmt.executeQuery("select userMoney from atm where userID = '"+id+"';");	// id�� �ش��ϴ� ���� �ݾ��� �ҷ���
			while (rs.next()) {
				balance = rs.getFloat("userMoney");				// �����ͺ��̽��� �ܾװ��� balance ������ ����
				money = balance - moneyOut;
				a = money;
				if (rs.getFloat("userMoney") >= moneyOut) {						// ���� �ܾ��� ��ݱݾ׺��� ������ ���������� ���
					System.out.println(moneyOut+"���� ����Ͽ����ϴ�.");
				} else {
					System.out.println("�ܾ��� �����մϴ�.");	// ���� �ܾ��� ��ݱݾ׺��� ���ٸ� �ܾ��� �����ϴٴ� �޼���
					a = money + moneyOut;
				}
			}
			stmt.executeUpdate("update atm set userMoney = '"+a+"' where userID = '"+id+"';");
			stmt.executeUpdate("insert into "+id+" values (0, "+moneyOut+", "+money+", '"+today+"');");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private static void balance(Statement stmt, String id) {
		
		try {
			ResultSet rs = stmt.executeQuery("select userMoney from atm where userID = '"+id+"';");
			while (rs.next()) {
				System.out.println("�ܾ��� "+rs.getFloat("userMoney")+"�� �Դϴ�.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void record(Statement stmt, String id) {
		
		try {
			System.out.println("�Աݾ�\t\t��ݾ�\t\t�ܾ�\t\t��¥");
			ResultSet rs = stmt.executeQuery("select * from "+id+";");
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int k = 1; k <= columnsNumber; k++)
					System.out.print(rs.getString(k) + "     \t");
				System.out.println();
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	public static void main(String[] args) throws SQLException  {
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
		
		String today = null;		// ��¥
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    Calendar currentDate = Calendar.getInstance();
	    today = dateFormat.format(currentDate.getTime()); 
	    
	    System.out.println(today);
	    
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
					stmt.executeUpdate("insert into atm (userID, userPass, userMoney) values ('"+id+"','"+pass+"',0);");	// atm ���̺� ���̵� ����
					stmt.executeUpdate("create table "+id+" ( moneyIn decimal(8,2), moneyOut decimal(8,2), balance decimal(8,2), record varchar(30) )");	// ���̵�� ���̺� ����
					System.out.println("ȸ������ �Ϸ�.");	
					menu1();
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
							record(stmt, id);
							menu2();
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
