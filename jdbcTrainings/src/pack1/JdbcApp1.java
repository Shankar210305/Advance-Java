package pack1;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcApp1 {

	private String driver = "oracle.jdbc.OracleDriver";
	private String DBurl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String DBuname = "system";
	private String DBpwd = "iesshankar";

	void createConnection() {
		System.out.println("Connecting java program to databse");
		try {

			Class.forName(driver);
			Connection con = DriverManager.getConnection(DBurl, DBuname, DBpwd);

			System.out.println("Cannection Created");
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		new JdbcApp1().createConnection();
	}

}