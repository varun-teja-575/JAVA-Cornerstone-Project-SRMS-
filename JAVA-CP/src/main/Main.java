package main;

import dao.*;
import model.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentDAO sDAO = new StudentDAO();
        ResultDAO rDAO = new ResultDAO();

        System.out.println("===== LOGIN =====");
        System.out.println("1. Admin");
        System.out.println("2. Student");
        System.out.print("Enter choice: ");

        int role = sc.nextInt();
        sc.nextLine();

        // ================= ADMIN LOGIN =================
        if (role == 1) {

            System.out.print("Enter Admin Username: ");
            String username = sc.nextLine();

            System.out.print("Enter Admin Password: ");
            String password = sc.nextLine();

            if (!username.equals("admin") || !password.equals("admin123")) {
                System.out.println("❌ Invalid Admin Credentials");
                return;
            }

            System.out.println("✅ Admin Login Successful");

            // ================= ADMIN MENU =================
            while (true) {

                System.out.println("\n===== ADMIN MENU =====");
                System.out.println("1. Add Student");
                System.out.println("2. View Students");
                System.out.println("3. Search Student");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Add Result");
                System.out.println("7. Update Result");
                System.out.println("8. Delete Result");
                System.out.println("9. View Results");
                System.out.println("10. Search Result by Roll");
                System.out.println("11. Failures by Section");
                System.out.println("12. Failures by Subject");
                System.out.println("13. Exit");
                System.out.print("Choice: ");

                int ch = sc.nextInt();
                sc.nextLine();

                switch (ch) {

                    case 1 -> {
                        System.out.print("Roll No: ");
                        String r = sc.nextLine();

                        System.out.print("Name: ");
                        String n = sc.nextLine();

                        System.out.print("Dept: ");
                        String d = sc.nextLine();

                        System.out.print("Sem: ");
                        int s = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Section: ");
                        String sec = sc.nextLine();

                        System.out.print("Password: ");
                        String pass = sc.nextLine();

                        sDAO.addStudent(new Student(r, n, d, s, sec, pass));
                    }

                    case 2 -> sDAO.viewAllStudents();

                    case 3 -> {
                        System.out.print("Enter Roll No: ");
                        sDAO.searchStudent(sc.nextLine());
                    }

                    case 4 -> {
                        System.out.print("Roll No: ");
                        String r = sc.nextLine();

                        System.out.print("New Name: ");
                        String n = sc.nextLine();

                        System.out.print("New Dept: ");
                        String d = sc.nextLine();

                        System.out.print("New Sem: ");
                        int s = sc.nextInt();
                        sc.nextLine();

                        System.out.print("New Section: ");
                        String sec = sc.nextLine();

                        sDAO.updateStudent(r, n, d, s, sec);
                    }

                    case 5 -> {
                        System.out.print("Enter Roll No: ");
                        sDAO.deleteStudent(sc.nextLine());
                    }

                    // ✅ ADD RESULT (NO CREDITS INPUT NOW)
                    case 6 -> {
                        System.out.print("Roll No: ");
                        String r = sc.nextLine();

                        System.out.print("Course Code: ");
                        String c = sc.nextLine();

                        System.out.print("Course Name: ");
                        String n = sc.nextLine();

                        System.out.print("Marks: ");
                        int m = sc.nextInt();
                        sc.nextLine();

                        rDAO.addResult(new Result(r, c, n, m));
                    }

                    case 7 -> {
                        System.out.print("Roll No: ");
                        String r = sc.nextLine();

                        System.out.print("Course Code: ");
                        String c = sc.nextLine();

                        System.out.print("New Marks: ");
                        int m = sc.nextInt();
                        sc.nextLine();

                        rDAO.updateResult(r, c, m);
                    }

                    case 8 -> {
                        System.out.print("Roll No: ");
                        String r = sc.nextLine();

                        System.out.print("Course Code: ");
                        String c = sc.nextLine();

                        rDAO.deleteResult(r, c);
                    }

                    case 9 -> rDAO.viewAllResults();

                    case 10 -> {
                        System.out.print("Enter Roll No: ");
                        rDAO.viewResultByRoll(sc.nextLine());
                    }

                    case 11 -> {
                        System.out.print("Enter Section: ");
                        rDAO.failBySection(sc.nextLine());
                    }

                    case 12 -> {
                        System.out.print("Enter Subject Code: ");
                        rDAO.failBySubject(sc.nextLine());
                    }

                    case 13 -> {
                        System.out.println("✅ Exiting...");
                        System.exit(0);
                    }

                    default -> System.out.println("❌ Invalid Choice");
                }
            }
        }

        // ================= STUDENT LOGIN =================
        else if (role == 2) {

            System.out.print("Enter Roll No: ");
            String roll = sc.nextLine();

            System.out.print("Enter Password: ");
            String pass = sc.nextLine();

            if (rDAO.loginStudent(roll, pass)) {
                System.out.println("✅ Login Successful");
                rDAO.viewResultByRoll(roll);
            } else {
                System.out.println("❌ Invalid Credentials");
            }
        }

        else {
            System.out.println("❌ Invalid Option");
        }
    }
}