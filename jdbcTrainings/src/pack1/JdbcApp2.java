package pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JdbcApp2 {

	private String driver = "oracle.jdbc.OracleDriver";
	private String DBurl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String DBuname = "system";
	private String DBpwd = "iesshankar";

	private String SqlQuery = "select * from employee";

	void getPrintSqlData() {
		System.out.println("------------Employee Details-----------");
		try {

			Class.forName(driver);
			Connection conn = DriverManager.getConnection(DBurl, DBuname, DBpwd);
			System.out.println("Cannection Cerated ");

			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(SqlQuery);

			while (res.next()) {

				System.out.println(res.getInt(1) + " " + res.getString(2) + " " + res.getString(3) + " "
						+ res.getString(4) + " " + res.getString(5));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new JdbcApp2().getPrintSqlData();
	}
}
