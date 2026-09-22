package pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Savepoint;
import java.util.Scanner;

public class JdbcApp12 {

	Scanner sc = new Scanner(System.in);
	private String drive = "oracle.jdbc.OracleDriver";
	private String Dburl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "system";
	private String pwd = "iesshankar";

	String sqlQuery1 = "update bank_account set balance=balance-? where  acc_no=?";
	String sqlQuery2 = "update bank_account set balance=balance+? where acc_no=?";

	public Connection connect() {
		Connection con = null;
		try {
			Class.forName(drive);
			con = DriverManager.getConnection(Dburl, user, pwd);
			System.out.println("Database cannected");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	void meth1() {
		try {
			Connection con = connect();

			con.setAutoCommit(false);

			PreparedStatement prsmt1 = con.prepareStatement(sqlQuery1);
			System.out.println("Enter a balance : ");
			int balance = sc.nextInt();
			System.out.println("Enter id ");
			int id = sc.nextInt();
			prsmt1.setInt(1, balance);
			prsmt1.setInt(2, id);

			int Rowcount1 = prsmt1.executeUpdate();

			if (Rowcount1 == 0) {
				System.out.println("balance is not reduce");
			} else {
				System.out.println("balance is reduce ");
			}

			Savepoint sp = con.setSavepoint();

			PreparedStatement prsmt2 = con.prepareStatement(sqlQuery2);

			System.out.println("Enter id ");
			int id1 = sc.nextInt();

			prsmt2.setInt(1, balance);
			prsmt2.setInt(2, id1);

			int rowcount2 = prsmt2.executeUpdate();
			if (rowcount2 == 0) {
				System.out.println("balance is not add");
				con.rollback(sp);
			} else {
				System.out.println("Balance is add ");
				con.commit();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JdbcApp12 obj = new JdbcApp12();
		obj.meth1();

	}
}
