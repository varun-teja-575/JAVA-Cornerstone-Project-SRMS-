package dao;

import model.Student;
import java.sql.*;

public class StudentDAO {

    public void addStudent(Student s) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO students VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, s.getRollNo());
            ps.setString(2, s.getName());
            ps.setString(3, s.getDept());
            ps.setInt(4, s.getSem());
            ps.setString(5, s.getSection());
            ps.setString(6, s.getPassword());

            ps.executeUpdate();
            System.out.println("✅ Student Added");

        } catch (Exception e) {
            System.out.println("❌ Student already exists");
        }
    }

    public void viewAllStudents() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM students");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                        rs.getString("roll_no") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("dept") + " | " +
                                rs.getInt("sem") + " | " +
                                rs.getString("section")
                );
            }

            if (!found) System.out.println("❌ No Students Found");

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public void searchStudent(String roll) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM students WHERE roll_no=?");
            ps.setString(1, roll);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(
                        rs.getString("roll_no") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("dept")
                );
            } else {
                System.out.println("❌ Student Not Found");
            }

        } catch (Exception e) {}
    }

    public void updateStudent(String r, String n, String d, int s, String sec) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE students SET name=?, dept=?, sem=?, section=? WHERE roll_no=?");

            ps.setString(1, n);
            ps.setString(2, d);
            ps.setInt(3, s);
            ps.setString(4, sec);
            ps.setString(5, r);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("✅ Student Updated");
            else
                System.out.println("❌ Student Not Found");

        } catch (Exception e) {}
    }

    public void deleteStudent(String roll) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM students WHERE roll_no=?");

            ps.setString(1, roll);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("✅ Student Deleted");
            else
                System.out.println("❌ Student Not Found");

        } catch (Exception e) {
            System.out.println("❌ Error deleting student");
        }
    }
}