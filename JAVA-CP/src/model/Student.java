package model;

public class Student {

    private String rollNo, name, dept, section, password;
    private int sem;

    public Student(String rollNo, String name, String dept, int sem, String section, String password) {
        this.rollNo = rollNo;
        this.name = name;
        this.dept = dept;
        this.sem = sem;
        this.section = section;
        this.password = password;
    }

    public String getRollNo() { return rollNo; }
    public String getName() { return name; }
    public String getDept() { return dept; }
    public int getSem() { return sem; }
    public String getSection() { return section; }
    public String getPassword() { return password; }
}