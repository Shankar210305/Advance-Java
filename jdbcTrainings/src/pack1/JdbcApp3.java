package pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcApp3 {

	private String driver = "oracle.jdbc.OracleDriver";
	private String DBurl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String DBusername = "system";
	private String DbPwd = "iesshankar";
	private String SqlQuery = "insert into employee values(110,'Alok','Kumar',25000,'Bhopal')";

	void insertData() {
		Scanner sc = new Scanner(System.in);
		try {

			Class.forName(driver);
			Connection conn = DriverManager.getConnection(DBurl, DBusername, DbPwd);
			Statement stat = conn.createStatement();
			int noCount = stat.executeUpdate(SqlQuery);

			if (noCount > 0) {
				System.out.println("Database Update");
				System.out.println("Do you want to see your Database (Y/N)");
				char choice = sc.nextLine().charAt(0);

				switch (choice) {
				case 'Y', 'y':
					ResultSet res = stat.executeQuery("select * from employee");
					while (res.next()) {
						System.out.println(res.getInt(1) + " " + res.getString(2) + " " + res.getString(3) + " "
								+ res.getDouble(4) + " " + res.getString(5));

					}
					break;

				case 'N', 'n':
					System.out.println("Thanks For Updating");
					System.exit(0);
				}

			} else {
				System.out.println("There is a no change in database ");
			}

		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			System.out.println("Duplicate element is nt allowed ");
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		sc.close();
	}

	public static void main(String[] args) {
		new JdbcApp3().insertData();
	}

}
