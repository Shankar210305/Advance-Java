package pack1;

import java.sql.Connection;

public class ConnectionPool {

	String Dburl = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "system";
	String pwd = "iesshankar";

	JdbcApp14 obj = new JdbcApp14(Dburl, user, pwd);

	void meth1() {
		System.out.println("Implemeting Connection pooling");

		obj.con_Initialization();
		System.out.println("Size of Vector : " + obj.v.size());

		System.out.println("\n------User 1-------");
		Connection con1 = obj.con_Acquisition();
		System.out.println("Size of vector : " + obj.v.size());

		System.out.println("\n------User 2-------");
		Connection con2 = obj.con_Acquisition();
		System.out.println("Size of vector : " + obj.v.size());

		System.out.println("\n------User 3-------");
		Connection con3 = obj.con_Acquisition();
		System.out.println("Size of vector : " + obj.v.size());

		obj.con_Return(con1);
		obj.con_Return(con2);
		obj.con_Return(con3);

	}

	public static void main(String[] args) {
		new ConnectionPool().meth1();
	}
}
