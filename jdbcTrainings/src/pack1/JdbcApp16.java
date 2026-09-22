package pack1;

import java.net.CacheRequest;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.sql.RowSet;
import javax.sql.RowSetMetaData;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class JdbcApp16 {

	private String drive = "oracle.jdbc.OracleDriver";
	private String Dburl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "system";
	private String pwd = "iesshankar";

	String sqlQuery = "select efname,elname from employee where eid=?";

	void meth1() {

		try {

			Class.forName(drive);
			Connection con = DriverManager.getConnection(Dburl, user, pwd);
			PreparedStatement prstm = con.prepareStatement(sqlQuery);
			prstm.setString(1, "102");

			ResultSet rs = prstm.executeQuery();

			RowSetFactory rsf = RowSetProvider.newFactory();
			CachedRowSet crs = rsf.createCachedRowSet();
			crs.setUrl(Dburl);
			crs.setUsername(user);
			crs.setPassword(pwd);

			crs.setCommand("select efname,elname from employee where eid=?");
			crs.setString(1, "102");
			crs.execute();

			DatabaseMetaData dtbmt = con.getMetaData();
			System.out.println("\n-----Database metadata---------");
			System.out.println("Database Name : " + dtbmt.getDatabaseProductName());
			System.out.println("Database version : " + dtbmt.getDatabaseProductVersion());
			System.out.println(" driver name : " + dtbmt.getDriverName());
			System.out.println("supportStoreProcudure :  " + dtbmt.supportsStoredProcedures());

			ParameterMetaData prmtdt = prstm.getParameterMetaData();
			System.out.println("\n---Parameterize database------");
			System.out.println("no of parameter : " + prmtdt.getParameterCount());
			System.out.println("paramater type : " + prmtdt.getParameterType(1));
			System.out.println("paramter mode : " + prmtdt.getParameterMode(1));
			System.out.println("isnullable : " + prmtdt.isNullable(1));

			ResultSetMetaData rsmtdt = rs.getMetaData();

			System.out.println("\n----Resultsetdatabase-----");
			System.out.println("no of coluns : " + rsmtdt.getColumnCount());
			System.out.println("Coulumn name : " + rsmtdt.getColumnName(1));
			System.out.println("Ccolumns size : " + rsmtdt.getColumnDisplaySize(1));
			System.out.println("isAutoIncrement : " + rsmtdt.isAutoIncrement(1));

			RowSetMetaData rowmtdta = (RowSetMetaData) crs.getMetaData();
			System.out.println("\n------ROwset meta data -------");
			System.out.println("no of columns : " + rowmtdta.getColumnCount());
			System.out.println("Coulumn name  : " + rowmtdta.getColumnName(1));
			System.out.println("coulumn type : " + rowmtdta.getColumnType(1));
			// System.out.println("is AutoIncrement : "+rowmtdta.isAutoIncrement(1));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JdbcApp16 obj = new JdbcApp16();
		obj.meth1();
	}
}
