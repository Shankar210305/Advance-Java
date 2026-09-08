package pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcApp4 {
	private String driver = "oracle.jdbc.OracleDriver";
	private String DBurl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String DBusername = "system";
	private String DbPwd = "iesshankar";

	Scanner sc = new Scanner(System.in);

	void insertData() {

		try {

			Class.forName(driver);
			Connection conn = DriverManager.getConnection(DBurl, DBusername, DbPwd);
			System.out.println("Database is connected ");

			System.out.print("Enter emp userId : ");
			int id = sc.nextInt();
			System.out.print("Enter emp FirstName : ");
			String firstname = sc.next();
			System.out.print("Enter emp LastName : ");
			String lastName = sc.next();
			System.out.print("Enter emp Salary :  ");
			double sal = sc.nextDouble();
			System.out.print("Enter emp location : ");
			String loc = sc.next();

			String sqlQuery = "insert into employee values(" + id + ", '" + firstname + "', '" + lastName + "', " + sal
					+ ", '" + loc + "')";

			Statement stm = conn.createStatement();
			int count = stm.executeUpdate(sqlQuery);

			if (count > 0) {
				System.out.println("Data is inserted in databse ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void deleteEmpdata() {

		try {

			Class.forName(driver);
			Connection conn = DriverManager.getConnection(DBurl, DBusername, DbPwd);
			System.out.println("Database is connected ");

			System.out.println("Enter emp id to delete  ");
			int id = sc.nextInt();
			String query = "delete from employee where eid = " + id;

			Statement stm = conn.createStatement();
			int count = stm.executeUpdate(query);

			if (count > 0) {
				System.out.println("Data  is deleted in databse ");
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void updateData() {

		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(DBurl, DBusername, DbPwd);

			System.out.println("Enter id for update ");
			int id = sc.nextInt();
			System.out.println("Enter sal what you want to insert : ");
			double sal = sc.nextDouble();
			String query = "update employee set esal = " + sal + " where eid = " + id;

			Statement stm = conn.createStatement();
			int Count = stm.executeUpdate(query);

			if (Count > 0) {
				System.out.println("Data is updated in databse ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void retriveEmpData() {
		Scanner sc = new Scanner(System.in);

		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(DBurl, DBusername, DbPwd);
			Statement stm = conn.createStatement();

			System.out.println("Enter emp id which emp id you want to retirive the data  ");
			int id = sc.nextInt();
			String query = "select * from employee where eid = " + id;

			ResultSet res = stm.executeQuery(query);

			while (res.next()) {

				System.out.println(res.getInt(1) + " " + res.getString(2) + " " + res.getString(3) + " "
						+ res.getString(4) + " " + res.getString(5));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		sc.close();

	}

	void seeDataBase() {

		try {

			Class.forName(driver);
			Connection conn = DriverManager.getConnection(DBurl, DBusername, DbPwd);
			System.out.println("Databse is connected ");
			String querys = "select * from employee";

			Statement stmt = conn.createStatement();

			ResultSet res = stmt.executeQuery(querys);

			while (res.next()) {
				System.out.println(res.getString(1) + "\t" + res.getString(2) + "\t" + res.getString(3) + "\t"
						+ res.getInt(4) + "\t" + res.getString(5));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		JdbcApp4 obj = new JdbcApp4();
		// obj.insertData();
		obj.seeDataBase();
		// obj.deleteEmpdata();

		// obj.updateData();
		// obj.retriveEmpData();
		// obj.sc.close();

	}

}
