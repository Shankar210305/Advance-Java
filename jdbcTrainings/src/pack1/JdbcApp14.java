package pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

public class JdbcApp14 {

	private String DBurl, dBuname, dbpwd;
	Vector<Connection> v = new Vector<Connection>();

	public JdbcApp14(String DBurl, String dBuname, String dbpwd) {

		this.DBurl = DBurl;
		this.dBuname = dBuname;
		this.dbpwd = dbpwd;

	}

	void con_Initialization() {
		System.out.println("Creating '5' Connection Objects");

		while (v.size() < 5) {
			try

			{
				Connection con = DriverManager.getConnection(DBurl, dBuname, dbpwd);
				v.addElement(con);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		for (Object data : v) {
			System.out.println(data);
		}
		System.out.println(v.size() + "Connection Objects presents in the Connection pool");
	}
	
	Connection con_Acquisition()
	{
		System.out.println("Assigning a Connection Objects ");
		Connection con = v.elementAt(0);
		v.remove(con);
		return con;
		
		
	}
	
	void  con_Return(Connection obj)
	{
		System.out.println("Adding the Cannection Objects back into Connection Pool");
		v.add(obj);
		System.out.println("--------------------");
		for(Object data : v) {
			System.out.println(data);
		}
	}
	

}
