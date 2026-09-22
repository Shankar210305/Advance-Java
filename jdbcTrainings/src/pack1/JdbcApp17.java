package pack1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class JdbcApp17 {

	private String drive = "oracle.jdbc.OracleDriver";
	private String Dburl = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "system";
	private String pwd = "iesshankar";

	String sqlQuery1 = "insert into mydata values(?,?)";
	String sqlQuery2 = " select PIC_DATA from mydata where id=?";
	String sqlQuery3 = "insert into mydata2 values(?,?)";
	String sqlQuery4 = " select FILE_DATA from mydata2 where id=?";

	public Connection connect() {
		Connection con = null;
		try {
			Class.forName(drive);
			con = DriverManager.getConnection(Dburl, user, pwd);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	void meth1() {
		System.out.println("Passing image into database");
		try {
			Connection con = connect();

			PreparedStatement psmt = con.prepareStatement(sqlQuery1);
			psmt.setString(1, "101");
			FileInputStream fil = new FileInputStream("D:\\pic1.jpg");
			psmt.setBlob(2, fil, fil.available());

			int rowcount = psmt.executeUpdate();
			if (rowcount == 0)
				throw new RuntimeException("Image is not inserted!!!");
			System.out.println("images Stored  in the  database");
			fil.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void meth2() {
		System.out.println("Retriving the image from database");
		try {
			Connection con = connect();

			PreparedStatement psmt = con.prepareStatement(sqlQuery2);
			psmt.setString(1, "101");

			ResultSet rs = psmt.executeQuery();

			if (rs.next()) {
				Blob b = rs.getBlob(2);
				byte arr[] = b.getBytes(1, (int) b.length());
				FileOutputStream fos = new FileOutputStream("D:\\pic2.jpg");
				fos.write(arr);
				System.out.println("Imag Retrived");
				fos.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void meth3() {
		System.out.println("Passing file into the database");
		try {

			Connection con = connect();

			PreparedStatement psmt = con.prepareStatement(sqlQuery3);
			psmt.setString(1, "101");
			FileReader fr = new FileReader("D:\\text1.txt");
			psmt.setClob(2, fr);

			int rowcount = psmt.executeUpdate();
			if (rowcount == 0)
				throw new RuntimeException("file is not inserted ");
			System.out.println("File is inserted");

			fr.close();
			con.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void meth4() {
		System.out.println("Retriving the file from the database");
		Scanner sc = new Scanner(System.in);
		try {

			Connection con = connect();
			PreparedStatement prsmt = con.prepareStatement(sqlQuery4);
//			System.out.print("Enter id : ");
//			String id = sc.next();
			prsmt.setString(1, "101");
			ResultSet rs = prsmt.executeQuery();

			if (rs.next()) {
				Clob b = rs.getClob(1);
				Reader data = b.getCharacterStream();
				BufferedReader br = new BufferedReader(data);
				FileWriter fr = new FileWriter("D:\\text2.txt");
				String line;
				while ((line = br.readLine()) != null) {
					fr.write(line);
					fr.write(System.lineSeparator()); // add this
				}
				br.close();
				fr.close();
				System.out.println("Clob data retrived ");

			} else {
				System.out.println("invalid id ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		JdbcApp17 obj = new JdbcApp17();
		// obj.meth1();
		// obj.meth2();
		// obj.meth3();
		obj.meth4();
	}

}
