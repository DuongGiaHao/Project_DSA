
package com.fpi.Haodg.assignment;


public class Student {
    
    private String id;
    private String name;
    private double mark;
    
    public Student(String id, String name, double mark) {
        this.id = id;
        this.name = name;
        this.mark = mark;
    }
    
    public String getId() { return id; }
    public String getName() { return name; }    
    public double getMark() { return mark; }
    public void setName(String name) { this.name = name; }
    public void setMark(double mark) { this.mark = mark; }
    
    public String getRank() {
        if (mark < 5.0) return "Fail";
        else if (mark < 6.5) return "Medium";
        else if (mark < 7.5) return "Good";
        else if (mark < 9.0) return "Very Good";
        else return "Excellent";
    }
    
    @Override
    public String toString() {
        return String.format(
            "--------------------------\n" +
            "Student Information:\n" +
            "ID    : %s\n" +
            "Name  : %s\n" +
            "Mark  : %.2f\n" +
            "Rank  : %s\n" +
            "--------------------------",
            id, name, mark, getRank()
        );
    }
}

