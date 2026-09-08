package pack1;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Scanner;

public class Operations {
	Scanner sc = new Scanner(System.in);

	public void insertData(PreparedStatement prsmt1) {

		try {
			System.out.println("Enter patient id1 : ");
			String pid1 = sc.nextLine();
			System.out.println("Enter patient name  : ");
			String pName1 = sc.nextLine();
			System.out.println("Enter patient age  : ");
			int age1 = Integer.parseInt(sc.nextLine());
			System.out.println("Enter patient contact No : ");
			long cont1 = Long.parseLong(sc.nextLine());

			prsmt1.setString(1, pid1);
			prsmt1.setString(2, pName1);
			prsmt1.setInt(3, age1);
			prsmt1.setLong(4, cont1);

			int RowCount = prsmt1.executeUpdate();

			if (RowCount > 0) {
				System.out.println("Patient Data inserted ");
			} else {
				System.out.println("Patient data not inserted ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void viewPatientData(PreparedStatement prsmt2) {
		try {
			ResultSet res = prsmt2.executeQuery();

			while (res.next()) {
				System.out.println(
						res.getString(1) + " " + res.getString(2) + "  " + res.getInt(3) + "  " + res.getLong(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void specificData(PreparedStatement prsmt3) {
		try {
			System.out.println("Enter patient id1 : ");
			String pid3 = sc.nextLine();

			prsmt3.setString(1, pid3);

			ResultSet res = prsmt3.executeQuery();

			while (res.next()) {
				System.out.println(
						res.getString(1) + " " + res.getString(2) + "  " + res.getInt(3) + "  " + res.getLong(4));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void update(PreparedStatement prsmt4) {

		try {
			System.out.println("Enter Patient age : ");
			int age4 = Integer.parseInt(sc.nextLine());
			System.out.println("Enter patient id : ");
			String pid4 = sc.nextLine();

			prsmt4.setInt(1, age4);
			prsmt4.setString(2, pid4);
			int RowCount4 = prsmt4.executeUpdate();

			if (RowCount4 > 0) {
				System.out.println("Patient Data Updated ");
			} else {
				System.out.println("Patient data id not updated ");
			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete(PreparedStatement prsmt5) {
		try {
			System.out.println("Enter patient id : ");
			String pid5 = sc.nextLine();

			prsmt5.setString(1, pid5);

			int RowCount5 = prsmt5.executeUpdate();

			if (RowCount5 > 0) {
				System.out.println(pid5 + " Patient Data is deleted ");
			} else {
				System.out.println(pid5 + "is not found ");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
