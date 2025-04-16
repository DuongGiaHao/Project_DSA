
package com.fpi.Haodg.assignment;

import java.util.Scanner;
import java.util.List; 
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
public class StudentManagement {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Student> students = new ArrayList<>();
    private static final Map<String, Student> studentMap = new HashMap<>();

    public static void inputStudentList() {
        System.out.print("Enter the number of students: ");
        int count;
        try {
            count = Integer.parseInt(scanner.nextLine());
            if (count < 0) {
                System.out.println("Error: Number must be non-negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number format.");
            return;
        }

        for (int i = 0; i < count; i++) {
            System.out.println("\nStudent " + (i + 1));
            System.out.print("ID: ");
            String id = scanner.nextLine();
            if (studentMap.containsKey(id)) {
                System.out.println("Error: ID already exists.");
                i--;
                continue;
            }

            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Mark (0-10): ");
            double mark;
            try {
                mark = Double.parseDouble(scanner.nextLine());
                if (mark < 0 || mark > 10) {
                    System.out.println("Error: Mark must be between 0 and 10.");
                    i--;
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid mark.");
                i--;
                continue;
            }

            Student student = new Student(id, name, mark);
            students.add(student);
            studentMap.put(id, student);
        }
    }

    public static void outputStudentList(List<Student> list) {
        if (list.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        System.out.println("\nStudent List:");
        list.forEach(System.out::println);
    }

    public static void editStudent() {
        System.out.print("\nEnter ID to edit: ");
        String id = scanner.nextLine();

        if (!studentMap.containsKey(id)) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("New Name: ");
        String name = scanner.nextLine();

        System.out.print("New Mark (0-10): ");
        try {
            double mark = Double.parseDouble(scanner.nextLine());
            if (mark < 0 || mark > 10) {
                System.out.println("Error: Mark must be between 0 and 10.");
                return;
            }
            Student student = studentMap.get(id);
            student.setName(name);
            student.setMark(mark);
            System.out.println("Update successful.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid mark input.");
        }
    }

    public static void deleteStudent() {
        System.out.print("\nEnter ID to delete: ");
        String id = scanner.nextLine();

        Student student = studentMap.remove(id);
        if (student != null) {
            students.remove(student);
            System.out.println("Deletion successful.");
        } else {
            System.out.println("Student not found.");
        }
    }

    public static void searchStudentById() {
        System.out.print("\nEnter ID to search: ");
        String id = scanner.nextLine();

        Student student = studentMap.get(id);
        System.out.println(student != null ? "Found: " + student : "Student not found.");
    }

    public static void searchStudentByName() {
        if (students.isEmpty()) {
            System.out.println("No students to search.");
            return;
        }

        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();

        List<Student> sorted = new ArrayList<>(students);
        quickSortByName(sorted, 0, sorted.size() - 1);

        int left = 0, right = sorted.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int cmp = sorted.get(mid).getName().compareToIgnoreCase(name);
            if (cmp == 0) {
                System.out.println("Found: " + sorted.get(mid));
                return;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        System.out.println("Name not found.");
    }

    public static void searchStudentByMark() {
        if (students.isEmpty()) {
            System.out.println("No students to search.");
            return;
        }

        System.out.print("Enter mark to search: ");
        try {
            double mark = Double.parseDouble(scanner.nextLine());
            if (mark < 0 || mark > 10) {
                System.out.println("Invalid mark.");
                return;
            }

            List<Student> sorted = new ArrayList<>(students);
            quickSortByMark(sorted, 0, sorted.size() - 1);

            int left = 0, right = sorted.size() - 1;
            while (left <= right) {
                int mid = (left + right) / 2;
                double midMark = sorted.get(mid).getMark();
                if (Math.abs(midMark - mark) < 1e-4) {
                    System.out.println("Found: " + sorted.get(mid));
                    return;
                } else if (midMark < mark) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            System.out.println("Mark not found.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid mark input.");
        }
    }

    public static void quickSortByMark(List<Student> list, int low, int high) {
        if (low < high) {
            int pi = partitionByMark(list, low, high);
            quickSortByMark(list, low, pi - 1);
            quickSortByMark(list, pi + 1, high);
        }
    }

    private static int partitionByMark(List<Student> list, int low, int high) {
        double pivot = list.get(high).getMark();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).getMark() >= pivot) {
                Collections.swap(list, ++i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }

    public static void quickSortByName(List<Student> list, int low, int high) {
        if (low < high) {
            int pi = partitionByName(list, low, high);
            quickSortByName(list, low, pi - 1);
            quickSortByName(list, pi + 1, high);
        }
    }

    private static int partitionByName(List<Student> list, int low, int high) {
        String pivot = list.get(high).getName();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (list.get(j).getName().compareToIgnoreCase(pivot) <= 0) {
                Collections.swap(list, ++i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }

    public static void mainMenu() {
        while (true) {
            System.out.println("\n=== Student Management ===");
            System.out.println("1. Input Students");
            System.out.println("2. Display Students");
            System.out.println("3. Edit Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search by ID");
            System.out.println("6. Search by Name");
            System.out.println("7. Search by Mark");
            System.out.println("8. Sort by Mark");
            System.out.println("9. Exit");
            System.out.print("Choose (1-9): ");

            String input = scanner.nextLine();
            switch (input) {
                case "1" -> inputStudentList();
                case "2" -> outputStudentList(students);
                case "3" -> editStudent();
                case "4" -> deleteStudent();
                case "5" -> searchStudentById();
                case "6" -> searchStudentByName();
                case "7" -> searchStudentByMark();
                case "8" -> {
                    List<Student> sorted = new ArrayList<>(students);
                    quickSortByMark(sorted, 0, sorted.size() - 1);
                    outputStudentList(sorted);
                }
                case "9" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    public static void main(String[] args) {
        mainMenu();
    }
}
