package model;

public class Result {

    private String rollNo, courseCode, courseName;
    private int marks;

    public Result(String rollNo, String courseCode, String courseName, int marks) {
        this.rollNo = rollNo;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.marks = marks;
    }

    public String getRollNo() { return rollNo; }
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getMarks() { return marks; }
}