public class M2_PayrollBatchBonus {

    static class Employee {
        String empId;
        double salary;

        Employee(String empId, double salary) {
            this.empId = empId;
            this.salary = salary;
        }

        void raiseSalary(double salary) {
            this.salary += salary;
        }
    }

    public static void main(String[] args) {
        String[] empIds = {"E-101", "E-102", "E-103", "E-104"};
        double[] startingSalaries = {40000, 55000, 62000, 48000};

        Employee[] employees = new Employee[empIds.length];
        for (int i = 0; i < empIds.length; i++) {
            employees[i] = new Employee(empIds[i], startingSalaries[i]);
        }

        for (Employee emp : employees) {
            emp.raiseSalary(5000);
            System.out.println(emp.empId + " | Final Salary: Rs " + emp.salary);
        }
    }
}
