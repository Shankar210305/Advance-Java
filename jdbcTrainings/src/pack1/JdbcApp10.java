package pack1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;

//import java.sql.SQLException;
import java.sql.Types;
//import java.text.CollationElementIterator;

import java.util.Scanner;

public class JdbcApp10 {

	Scanner sc = new Scanner(System.in);
	private String drive = "oracle.jdbc.OracleDriver";
	private String Dburl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "system";
	private String pwd = "iesshankar";

	public Connection connect() {
		Connection con = null;

		try {
			Class.forName(drive);
			con = DriverManager.getConnection(Dburl, user, pwd);
			System.out.println("Database cannected");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	void meth1() {
		Connection con = connect();
		try {
			CallableStatement csmt = con.prepareCall("{call InsertEmpData(?,?,?,?,?)}");

			System.out.print("Enter emp id : ");
			String eid = sc.nextLine();
			System.out.print("Enter emp name : ");
			String ename = sc.nextLine();
			System.out.print("Enter desgition ");
			String edesc = sc.nextLine();
			System.out.print("Enter a basic Salary : ");
			int besal = Integer.parseInt(sc.nextLine());
			double etsal = besal + ((0.35 * besal) + (0.10 * besal));

			csmt.setString(1, eid);
			csmt.setString(2, ename);
			csmt.setString(3, edesc);
			csmt.setInt(4, besal);
			csmt.setDouble(5, etsal);

			csmt.execute();

			System.out.println("Data inserted !!!");
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void retriveData() {
		System.out.println("Implemeting the collable satament for retirve tha data ");

		try {
			Connection con = connect();
			CallableStatement csmt = con.prepareCall("{call  RetriveEmpDetails(?,?,?,?,?)}");

			System.out.print("Enter emp id  to retrive the data : ");
			String eid = sc.nextLine();

			csmt.setString(1, eid);
			csmt.registerOutParameter(2, Types.VARCHAR);
			csmt.registerOutParameter(3, Types.VARCHAR);
			csmt.registerOutParameter(4, Types.INTEGER);
			csmt.registerOutParameter(5, Types.FLOAT);

			csmt.execute();
			System.out.println();
			System.out.println("******Employee Details********");
			System.out.println();
			System.out.println("Employee id :" + eid);
			System.out.println("Employee Name : " + csmt.getString(2));
			System.out.println("Employee Desgnation : " + csmt.getString(3));
			System.out.println("Employee Basic Salary : " + csmt.getInt(4));
			System.out.println("Employee Total Salary : " + csmt.getFloat(5));

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	void retriveEmptsal() {
		System.out.println("Implemeting the collable satament for retirve tha data  in function ");
		try {
			Connection con = connect();
			CallableStatement csmt = con.prepareCall("{call ?:= retrivetsal(?)}");

			System.out.println("Enter Employee id ");
			String eid = sc.nextLine();

			csmt.setString(2, eid);

			csmt.registerOutParameter(1, Types.FLOAT);
			csmt.execute();

			System.out.println("Employee id : " + eid + " Total sal is " + csmt.getFloat(1));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		JdbcApp10 obj = new JdbcApp10();
		// obj.meth1();
		// obj.retriveData();
		obj.retriveEmptsal();
	}

}
