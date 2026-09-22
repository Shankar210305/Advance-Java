package pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.util.Scanner;

public class JdbcApp11 {

	Scanner sc = new Scanner(System.in);
	private String drive = "oracle.jdbc.OracleDriver";
	private String Dburl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "system";
	private String pwd = "iesshankar";

	String sqlQuery = "UPDATE TRAINSEATAVAILABILITY " + "SET AVAILABLE_SEAT = AVAILABLE_SEAT - 1 "
			+ "WHERE TRAIN_ID = ? " + "AND JOURNEY_DATE = ? " + "AND CLASS = ? " + "AND AVAILABLE_SEAT > 0";

	String sqlQuery2 = "insert into bookingdetails values(?,?,?,?,?)";

	String sqlQuery3 = "SELECT PAYMENT_STATUS FROM customerpayment WHERE CUSTOMER_ID=?";
	String sqlQuery4 = "UPDATE BOOKINGDETAILS SET STATUS='Success' WHERE CUSTOMER_ID=?";
	

	public Connection connect() {
		Connection con = null;

		try {
			Class.forName(drive);
			con = DriverManager.getConnection(Dburl, user, pwd);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	void meth1() {

		System.out.println("Implementing Transcation management system");
		

		try {
			Connection con = connect();
			System.out.println("getAutoComit() " + con.getAutoCommit());
			con.setAutoCommit(false);
			System.out.println("getAutocommit() " + con.getAutoCommit());

			PreparedStatement psmt = con.prepareStatement(sqlQuery);

//			System.out.print("Enter the id : ");
//			String id = sc.next();
//			System.out.print("Enter journey Date : ");
//			String date = sc.next();
			psmt.setString(1, "12345");
			psmt.setString(2, "2024-10-10");
			psmt.setString(3, "Sleaper");

			int Rowcount1 = psmt.executeUpdate();

			if (Rowcount1 == 0) {
				throw new RuntimeException("Seats are not availblae");
			} else {
				System.out.println("Seat is lock");

			}
			Savepoint sp = con.setSavepoint();

			PreparedStatement psmt2 = con.prepareStatement(sqlQuery2);

			psmt2.setString(1, "BOO1");
			psmt2.setString(2, "12345");
			psmt2.setString(3, "C123");
			psmt2.setInt(4, 1);
			psmt2.setString(5, "Payment Pending");

			int rowCount2 = psmt2.executeUpdate();
			if (rowCount2 == 0) {
				throw new RuntimeException("illegal Argument");
			} else {
				System.out.println("Booking record created");
			}
			System.out.println("Witing for payment conformation");

			PreparedStatement prmst3 = con.prepareStatement(sqlQuery3);
			prmst3.setString(1, "C123");

			ResultSet rs = prmst3.executeQuery();

			if (rs.next()) {
				if (rs.getString(1).equals("Success")) {
					PreparedStatement prmst4 = con.prepareStatement(sqlQuery4);
					prmst4.setString(1, "C123");

					int rowcount3 = prmst4.executeUpdate();

					if (rowcount3 == 0)
						System.out.println("Transcation failed");
					else {
						System.out.println("Transcation success");
						con.commit();

					}
				} else {
					System.out.println("Payment not done");
					System.out.println("Rolling back to the last statement");
					con.rollback(sp);
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	

	public static void main(String[] args) {
		JdbcApp11 obj = new JdbcApp11();
		obj.meth1();
	}

}
