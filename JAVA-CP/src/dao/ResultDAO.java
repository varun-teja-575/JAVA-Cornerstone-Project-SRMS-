package dao;

import model.Result;
import java.sql.*;

public class ResultDAO {

    // ===== GRADE LOGIC =====
    private String getGrade(int marks) {
        if (marks >= 90) return "O";
        else if (marks >= 80) return "A+";
        else if (marks >= 70) return "A";
        else if (marks >= 60) return "B";
        else if (marks >= 50) return "C";
        else return "F";
    }

    // ===== CREDIT LOGIC =====
    private int getCredits(int marks) {
        if (marks >= 90) return 10;
        else if (marks >= 80) return 9;
        else if (marks >= 70) return 8;
        else if (marks >= 60) return 7;
        else if (marks >= 50) return 6;
        else return 0;
    }

    // ===== ADD RESULT =====
    public void addResult(Result r) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO results (roll_no, course_code, course_name, marks, grade, credits) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            int marks = r.getMarks();

            ps.setString(1, r.getRollNo());
            ps.setString(2, r.getCourseCode());
            ps.setString(3, r.getCourseName());
            ps.setInt(4, marks);
            ps.setString(5, getGrade(marks));
            ps.setInt(6, getCredits(marks));

            ps.executeUpdate();
            System.out.println("✅ Subject Added");

        } catch (Exception e) {
            System.out.println("❌ Error adding subject");
        }
    }

    // ===== UPDATE RESULT =====
    public void updateResult(String roll, String code, int marks) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "UPDATE results SET marks=?, grade=?, credits=? WHERE roll_no=? AND course_code=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, marks);
            ps.setString(2, getGrade(marks));
            ps.setInt(3, getCredits(marks));
            ps.setString(4, roll);
            ps.setString(5, code);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("✅ Result Updated");
            else
                System.out.println("❌ Record Not Found");

        } catch (Exception e) {
            System.out.println("Error updating result");
        }
    }

    // ===== DELETE RESULT (THIS WAS MISSING ❗) =====
    public void deleteResult(String roll, String code) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM results WHERE roll_no=? AND course_code=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, roll);
            ps.setString(2, code);

            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("✅ Result Deleted");
            else
                System.out.println("❌ Record Not Found");

        } catch (Exception e) {
            System.out.println("Error deleting result");
        }
    }

    // ===== VIEW ALL RESULTS =====
    public void viewAllResults() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM results");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                        rs.getString("roll_no") + " | " +
                                rs.getString("course_code") + " | " +
                                rs.getInt("marks")
                );
            }

            if (!found) System.out.println("❌ No Results Found");

        } catch (Exception e) {}
    }

    // ===== LOGIN =====
    public boolean loginStudent(String roll, String pass) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM students WHERE roll_no=? AND password=?");

            ps.setString(1, roll);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            return false;
        }
    }

    // ===== VIEW RESULT BY ROLL =====
    public void viewResultByRoll(String roll) {
        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps1 = con.prepareStatement(
                    "SELECT * FROM students WHERE roll_no=?");
            ps1.setString(1, roll);
            ResultSet s = ps1.executeQuery();

            if (!s.next()) {
                System.out.println("❌ Student not found");
                return;
            }

            System.out.println("\n=========== STUDENT RESULT ===========");
            System.out.println("Roll No : " + roll);
            System.out.println("Name    : " + s.getString("name"));
            System.out.println("Dept    : " + s.getString("dept"));
            System.out.println("Sem     : " + s.getInt("sem"));
            System.out.println("Section : " + s.getString("section"));

            System.out.println("\n------------------------------------------------------------------");
            System.out.printf("%-5s %-12s %-25s %-6s %-6s %-8s\n",
                    "No", "Code", "Course", "Marks", "Grade", "Credits");
            System.out.println("------------------------------------------------------------------");

            PreparedStatement ps2 = con.prepareStatement(
                    "SELECT * FROM results WHERE roll_no=?");
            ps2.setString(1, roll);

            ResultSet rs = ps2.executeQuery();

            int i = 1, total = 0, count = 0;

            while (rs.next()) {
                int marks = rs.getInt("marks");

                total += marks;
                count++;

                System.out.printf("%-5d %-12s %-25s %-6d %-6s %-8d\n",
                        i++,
                        rs.getString("course_code"),
                        rs.getString("course_name"),
                        marks,
                        rs.getString("grade"),
                        rs.getInt("credits"));
            }

            if (count == 0) {
                System.out.println("❌ No Results Found");
                return;
            }

            double avg = total / (double) count;

            System.out.println("------------------------------------------------------------------");
            System.out.println("Total : " + total);
            System.out.println("Avg   : " + avg);
            System.out.println("Result: " + (avg >= 50 ? "PASS" : "FAIL"));

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    // ===== FAIL BY SECTION =====
    public void failBySection(String section) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT s.roll_no, r.marks FROM students s JOIN results r ON s.roll_no=r.roll_no WHERE s.section=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, section);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                if (rs.getInt("marks") < 50) {
                    found = true;
                    System.out.println(rs.getString("roll_no") + " FAILED");
                }
            }

            if (!found) System.out.println("✅ No Failures");

        } catch (Exception e) {}
    }

    // ===== FAIL BY SUBJECT =====
    public void failBySubject(String code) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT roll_no, marks FROM results WHERE course_code=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                if (rs.getInt("marks") < 50) {
                    found = true;
                    System.out.println(rs.getString("roll_no") + " FAILED");
                }
            }

            if (!found) System.out.println("✅ No Failures");

        } catch (Exception e) {}
    }
}