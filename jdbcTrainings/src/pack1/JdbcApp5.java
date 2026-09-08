package pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class JdbcApp5 {

	private String driver = "oracle.jdbc.OracleDriver";
	private String Dburl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String Duser = "system";
	private String dbPwd = "iesshankar";
	Scanner sc = new Scanner(System.in);

	public Connection connect() {
		Connection conn = null;

		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(Dburl, Duser, dbPwd);
			System.out.println("Database cannected ");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	void operation() {
		Connection conn = connect();

		try {
			PreparedStatement prsmt1 = conn.prepareStatement("insert into patient values(?,?,?,?)");
			PreparedStatement prsmt2 = conn.prepareStatement("select * from patient");
			PreparedStatement prsmt3 = conn.prepareStatement("select * from patient where pid=?");
			PreparedStatement prsmt4 = conn.prepareStatement("update patient set age=? where pid=?");
			PreparedStatement prsmt5 = conn.prepareStatement("delete from patient where pid=?");

			while (true) {
				System.out.println("----------Welcome to  patient Database----------");
				System.out.println("chouse your options");
				System.out.println();

				System.out.println("(1) Add Patient Data");
				System.out.println("(2) View Patient Data");
				System.out.println("(3) Retrieve  patient Data");
				System.out.println("(4) Update patient Data");
				System.out.println("(5) Delete Patient Data");
				System.out.println("(6) Exit");

				int choice = Integer.parseInt(sc.nextLine());

				switch (choice) {
					case 1 -> {
						System.out.println("------Adding Patient Data-----");
						System.out.println();
						new Operations().insertData(prsmt1);

					}
					case 2 -> {
						System.out.println("-------View All Patient Data---------");
						System.out.println();
						new Operations().viewPatientData(prsmt2);
						;
					}

					case 3 -> {
						System.out.println("-------view   specific patient data ");
						System.out.println();

						new Operations().specificData(prsmt3);
					}

					case 4 -> {
						System.out.println("--------update specific data------");
						new Operations().update(prsmt4);

					}

					case 5 -> {
						System.out.println("-----Delete Specific Data---------");
						System.out.println();
						new Operations().delete(prsmt5);
					}

					case 6 -> {
						System.out.println("Thank you for visiting ");
						System.out.println("Have a greet Day");
						System.exit(0);
					}
					default -> System.out.println();

				}

			}

		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
			System.out.println("Duplicate data is not allowed ");
			operation();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		JdbcApp5 obj = new JdbcApp5();
		obj.operation();
	}

}
