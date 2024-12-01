package telran.employee.dao;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CompanyImpl implements Company {
    private List<Employee> employees;
    private int capacity;

    public CompanyImpl(int capacity) {
        this.capacity = capacity;
        employees = new ArrayList<Employee>();
    }

    //O(n)
    @Override
    public boolean addEmployee(Employee employee) {
        if (employee == null || capacity == employees.size() || findEmployee(employee.getId()) != null) {
            return false;
        }
        return employees.add(employee);
    }

    //O(n)
    @Override
    public Employee removeEmployee(int id) {
        Employee victim = findEmployee(id);
        employees.remove(victim);
        return victim;
    }

    //O(n)
    @Override
    public Employee findEmployee(int id) {
        for (Employee emp : employees) {
            if (emp.getId() == id) {
                return emp;
            }
        }
        return null;
    }

    //O(1)
    @Override
    public int quantity() {
        return employees.size();
    }

    //O(n)
    @Override
    public double totalSalary() {
        double sum = 0;
        for (Employee emp : employees) {
            sum += emp.calcSalary();
        }
        return sum;
    }

    //O(n)
    @Override
    public double totalSales() {
        double sum = 0;
        for (Employee emp : employees) {
            if (emp instanceof SalesManager) {
                sum += ((SalesManager) emp).getSalesValue();
            }
        }
        return sum;
    }

    @Override
    public void printEmployees() {
        System.out.println("==== " + COUNTRY + " ====");
        for (Employee emp : employees) {
            System.out.println(emp);
        }
        System.out.println("Company's capacity = " + quantity());
        System.out.println("Amount of employees = " + employees.size());
        System.out.println("Total salary = " + totalSalary());
        System.out.println("Average salary = " + avgSalary());
        System.out.println("Total sales = " + totalSales());
    }

    @Override
    public Employee[] findEmployeesHoursGreaterThan(int hours) {
        return findEmployeesByPredicate(e -> e.getHours() > hours);
    }

    @Override
    public Employee[] findEmployeesSalaryBetween(int minSalary, int maxSalary) {
        return findEmployeesByPredicate(e -> e.calcSalary() >= minSalary && e.calcSalary() < maxSalary);
    }

    //O(n)
    private Employee[] findEmployeesByPredicate(Predicate<Employee> predicate) {
        List<Employee> res = new ArrayList<>();
        for (Employee emp : employees) {
            if (predicate.test(emp)) {
                res.add(emp);
            }
        }
        return res.toArray(new Employee[res.size()]);
    }
}
