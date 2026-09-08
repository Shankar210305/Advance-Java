package pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcApp7 {

	private String drive = "oracle.jdbc.OracleDriver";
	private String Dburl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "system";
	private String pwd = "iesshankar";

	Scanner sc = new Scanner(System.in);

	private String SqlQuery = "select * from employee";
	private String SqlQuery2 = "select eid,efname,esal from employee";

	public Connection connect() {
		Connection conn = null;
		try {
			Class.forName(drive);
			conn = DriverManager.getConnection(Dburl, user, pwd);
			// System.out.println("Databse cannected ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	void meth1() {

		System.out.println("Implementation Scrollable Resultset");
		try {

			Connection conn = connect();
			// Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			// ResultSet.CONCUR_READ_ONLY);

			Statement stmt = conn.createStatement(1004, 1007);
			ResultSet res = stmt.executeQuery(SqlQuery);
			res.afterLast();

			while (res.previous()) {
				System.out.println(res.getString(1) + " " + res.getString(2) + " " + res.getString(3) + " "
						+ res.getInt(4) + " " + res.getString(5));
			}

			System.out.println("--------------------------");
			System.out.println();

			while (res.next()) {
				System.out.println(res.getString(1) + " " + res.getString(2) + " " + res.getString(3) + " "
						+ res.getInt(4) + " " + res.getString(5));
			}

			System.out.println("------------------------------");
			System.out.println();

			res.last();

			System.out.println(res.getString(1) + " " + res.getString(2) + " " + res.getString(3) + " " + res.getInt(4)
					+ " " + res.getString(5));

			System.out.println("-----------------------------");
			System.out.println();

			res.absolute(-5);
			System.out.println(res.getString(1) + " " + res.getString(2) + " " + res.getString(3) + " " + res.getInt(4)
					+ " " + res.getString(5));

			System.out.println("----------------------------");
			System.out.println();

			res.relative(1);
			System.out.println(res.getString(1) + " " + res.getString(2) + " " + res.getString(3) + " " + res.getInt(4)
					+ " " + res.getString(5));

			System.out.println("------------------------");
			System.out.println();

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void math2() {
		System.out.println("Implementing  Scrollable Resultset  Update ");
		try {
			Connection conn = connect();
			Statement stmt = conn.createStatement(1004, 1008);
			ResultSet res = stmt.executeQuery(SqlQuery2);

			System.out.println("Enter eid to update data");
			String eid = sc.nextLine();
			System.out.println("Enter esal to update ");
			int sal = sc.nextInt();
			sc.nextLine();

			while (res.next()) {
				String empid = res.getString(1);

				if (eid.equals(empid)) {
					System.out.println("Updating the salary from employee " + res.getString(2));
					res.updateInt("esal", sal);
					res.updateRow();
					System.out.println("new Sal -> " + res.getInt(3));

					System.out.println("Data Updated doy you want to view (Y/N)");
					char choice = sc.nextLine().charAt(0);

					switch (choice) {
					case 'Y', 'y' -> {

						System.out.println(res.getString(1) + " " + res.getString(2) + " " + res.getString(3));
						break;

					}

					case 'N', 'n' -> {
						System.out.println("See you soon!!");
						System.exit(0);
					}
					default -> {
						System.out.println("Invalid input ");
					}
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JdbcApp7 obj = new JdbcApp7();
		// obj.meth1();
		obj.math2();
	}

}
