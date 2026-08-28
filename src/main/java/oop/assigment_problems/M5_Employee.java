public class M5_Employee {

    static class Employee {
        // instance fields — unique per object
        String empName;
        double salary;

        // static fields — shared across all objects
        static String companyName = "Bright Horizon Technologies";
        static int employeeCount = 0;

        Employee(String empName, double salary) {
            this.empName = empName;
            this.salary = salary;
            employeeCount++;
        }

        // static method: only touches static fields, no instance fields
        static void printCompanyInfo() {
            System.out.println(companyName);
            System.out.println("Employees on record: " + employeeCount);
        }
    }

    public static void main(String[] args) {
        Employee e1 = new Employee("Ravi", 40000);
        Employee e2 = new Employee("Sneha", 45000);
        Employee e3 = new Employee("Karan", 50000);

        // called through the class name, not through any object
        Employee.printCompanyInfo();
    }
}
