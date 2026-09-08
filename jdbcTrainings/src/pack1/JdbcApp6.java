package pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class JdbcApp6 {

	private String drive = "oracle.jdbc.OracleDriver";
	private String Dburl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "system";
	private String pwd = "iesshankar";

	public Connection connect() {
		Connection conn = null;
		try {
			Class.forName(drive);
			conn = DriverManager.getConnection(Dburl, user, pwd);
			System.out.println("Databse cannected ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	void meth1() {
		connect();
		System.out.println("\n-----TYPES-----");
		System.out.println(ResultSet.TYPE_FORWARD_ONLY);
		System.out.println(ResultSet.TYPE_SCROLL_INSENSITIVE);
		System.out.println(ResultSet.TYPE_SCROLL_SENSITIVE);

		System.out.println("\n----MODES----");
		System.out.println(ResultSet.CONCUR_READ_ONLY);
		System.out.println(ResultSet.CONCUR_UPDATABLE);
	}

	public static void main(String[] args) {
		JdbcApp6 obj = new JdbcApp6();
		obj.meth1();
	}
}
