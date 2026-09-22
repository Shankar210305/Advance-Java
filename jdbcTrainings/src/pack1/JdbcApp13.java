package pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

public class JdbcApp13 {

	Scanner sc = new Scanner(System.in);
	private String drive = "oracle.jdbc.OracleDriver";
	private String Dburl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "system";
	private String pwd = "iesshankar";

	String sqlQuery1 = "update  movieseatAvailability set  AVAILABLE_SEAT =  AVAILABLE_SEAT-1 where movie_id=? and  AVAILABLE_SEAT > 0";
	String sqlQuery2 = "insert into  moviebookingDetails values(?,?,?,?,?)";
	String sqlQuery3 = "select  AVAILABLE_SEAT from   movieseatavailability where MOVIE_ID=?";
	String sqlQuery4 = "select PAYMENT_STATUS from  customerpayments where CUSTOMER_ID =?";
	String sqlQuery5 = "update  moviebookingdetails set STATUS='Success' WHERE CUSTOMER_ID=?";

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

	public void bookTicket(String movieId, String customerId, int seatNumber) {
		System.out.println("\n===== BOOK MOVIE TICKET =====");

		try {

			Connection con = connect();
			System.out.println("getAutoCmmit " + con.getAutoCommit());
			con.setAutoCommit(false);
			System.out.println("getAutoCmmit " + con.getAutoCommit());

			PreparedStatement prsmt1 = con.prepareStatement(sqlQuery1);
			prsmt1.setString(1, movieId);

			int rowcount1 = prsmt1.executeUpdate();

			if (rowcount1 == 0) {
				throw new RuntimeException("Seat are not available ");
			} else {
				System.out.println("Seat is locked  successfully ");
			}

			Savepoint sp = con.setSavepoint();

			System.out.print("Enter bokking id : ");
			String bookingId = sc.next();
			PreparedStatement prsmt2 = con.prepareStatement(sqlQuery2);
			prsmt2.setString(1, bookingId);
			prsmt2.setString(2, movieId);
			prsmt2.setString(3, customerId);
			prsmt2.setInt(4, seatNumber);
			prsmt2.setString(5, "Pending Payemnt");

			int rowcount2 = prsmt2.executeUpdate();

			if (rowcount2 == 0) {
				throw new RuntimeException("Booking record is not creted");
			} else {
				System.out.println("booking record created ");
			}
			con.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkAvailabilty(String movieId) {
		try {
			Connection con = connect();

			PreparedStatement prsmt3 = con.prepareStatement(sqlQuery3);
			prsmt3.setString(1, movieId);

			ResultSet rs = prsmt3.executeQuery();

			while (rs.next()) {
				int availableSeat = rs.getInt("AVAILABLE_SEAT");

				System.out.println("Available seats : " + availableSeat);
			}

			System.out.println("Witing for payment confirmation");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlePaymentStatus(String customerId) {
		try {
			Connection con = connect();

			PreparedStatement prsmt4 = con.prepareStatement(sqlQuery4);
			prsmt4.setString(1, customerId);

			ResultSet rs = prsmt4.executeQuery();

			while (rs.next()) {
				String status = rs.getString("PAYMENT_STATUS");

				if (status.equals("Success")) {
					PreparedStatement prsmt5 = con.prepareStatement(sqlQuery5);
					prsmt5.setString(1, customerId);

					int rowcount3 = prsmt5.executeUpdate();

					if (rowcount3 == 0) {
						throw new RuntimeException("Transaction failed");
					} else {
						System.out.println("Transcation success");
						// con.commit();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter movie id : ");
		String movieId = sc.next();
		System.out.print("Enter cutomer id : ");
		String customerId = sc.next();
		System.out.print("Enter seat number : ");
		int seatNumber = sc.nextInt();

		JdbcApp13 obj = new JdbcApp13();
		obj.bookTicket(movieId, customerId, seatNumber);
		obj.checkAvailabilty(movieId);
		obj.handlePaymentStatus(customerId);

		sc.close();

	}
}
