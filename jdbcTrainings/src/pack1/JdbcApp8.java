package pack1;

import java.util.Scanner;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class JdbcApp8 {

	private String Dburl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "system";
	private String pwd = "iesshankar";

	void meth1() {
		System.out.println("Implemeting jdbc roWset");

		try {
			RowSetFactory rsf = RowSetProvider.newFactory();

			JdbcRowSet jrs = rsf.createJdbcRowSet();
			jrs.setUrl(Dburl);
			jrs.setUsername(user);
			jrs.setPassword(pwd);
			jrs.setCommand("select * from employee");
			jrs.execute();

			jrs.last();

			System.out.println(jrs.getString(1) + "\t" + jrs.getString(2) + "\t" + jrs.getString(3) + "\t"
					+ jrs.getInt(4) + " " + jrs.getString(5));

			System.out.println("-----------------------------");

			jrs.first();

			System.out.println(jrs.getString(1) + "\t" + jrs.getString(2) + "\t" + jrs.getString(3) + "\t"
					+ jrs.getInt(4) + " " + jrs.getString(5));

			System.out.println("-----------------------------");

			jrs.beforeFirst();

			while (jrs.next()) {
				System.out.println(jrs.getString(1) + "\t" + jrs.getString(2) + "\t" + jrs.getString(3) + "\t"
						+ jrs.getInt(4) + "\t" + jrs.getString(5));
			}
			System.out.println("---------------------------------");

			jrs.afterLast();

			while (jrs.previous()) {

				System.out.println(jrs.getString(1) + "\t" + jrs.getString(2) + "\t" + jrs.getString(3) + "\t"
						+ jrs.getInt(4) + "\t" + jrs.getString(5));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	void meth2() {
		System.out.println("Implemeting Cache roWset");
		Scanner sc = new Scanner(System.in);

		try {
			RowSetFactory rsf = RowSetProvider.newFactory();
			CachedRowSet crs = rsf.createCachedRowSet();
			crs.setUrl(Dburl);
			crs.setUsername(user);
			crs.setPassword(pwd);

			crs.setCommand("select EID  ,EFNAME  , ESAL  from employee");
			crs.execute();

			System.out.println("Enter eid to update data");
			String eid = sc.nextLine();
			System.out.println("Enter esal to update ");
			int sal = Integer.parseInt(sc.nextLine());

			while (crs.next()) {

				String empid = crs.getString(1);

				if (eid.equals(empid)) {
					System.out.println("Updating the salary from employee " + crs.getString(2));
					crs.updateInt("esal", sal);
					crs.updateRow();
					// System.out.println(crs.getString(1)+"\t"+crs.getString(2)+"\t"+crs.getInt(3));

					System.out.println("new sal -> " + crs.getInt(3));

					System.out.println("DO want to see databse ");

					char choice = sc.next().charAt(0);

					switch (choice) {
						case 'Y', 'y':
							System.out.println(crs.getString(1) + "\t" + crs.getString(2) + "\t" + crs.getInt(3));
							break;

						case 'N', 'n':
							System.out.println("See you soon!!");
							System.exit(0);
							break;

						default:
							System.out.println("Soo you soon !");

					}
				}
			}

			// crs.acceptChanges();
			sc.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JdbcApp8 obj = new JdbcApp8();
		// obj.meth1();
		obj.meth2();
	}

}
