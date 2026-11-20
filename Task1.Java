import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Employee {
    private final String name;
    private final String surname;
    private final double salary;

    public Employee(String name, String surname, double salary) {
        this.name = name;
        this.surname = surname;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
 
    public double getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return name + " " + surname + " (" + salary + ")";
    }
}

class Department {
    private final String name;
    private final Employee head;
    private final List<Employee> employees = new ArrayList<Employee>();

    public Department(String name, Employee head) {
        this.name = name;
        this.head = head;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public String getName() {
        return name;
    }

    public Employee getHead() {
        return head;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}

class Firm {
    private final String name;
    private final Employee director;
    private final List<Department> departments = new ArrayList<Department>();

    public Firm(String name, Employee director) {
        this.name = name;
        this.director = director;
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public String getName() {
        return name;
    }

    public Employee getDirector() {
        return director;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    // задача 1 - макс. зарплата, цикл for-each
    public double findMaxSalary() {
        double max = director.getSalary();
        for (Department department : departments) {
            // начальник теж рахується
            if (department.getHead().getSalary() > max) {
                max = department.getHead().getSalary();
            }
            for (Employee employee : department.getEmployees()) {
                if (employee.getSalary() > max) {
                    max = employee.getSalary();
                }
            }
        }
        return max;
    }

    // задача 2 - відділ де хтось заробляє більше за начальника, нетипізований ітератор
    @SuppressWarnings("rawtypes")
    public Department findDepartmentWithRichEmployee() {
        Iterator depIt = departments.iterator();
        while (depIt.hasNext()) {
            // без generics next() повертає Object, тому кастимо
            Department department = (Department) depIt.next();
            Iterator empIt = department.getEmployees().iterator();
            while (empIt.hasNext()) {
                Employee employee = (Employee) empIt.next();
                if (employee.getSalary() > department.getHead().getSalary()) {
                    return department;
                }
            }
        }
        // не знайшли
        return null;
    }

    // задача 3 - список всіх працівників, типізований ітератор
    public List<Employee> getAllEmployees() {
        List<Employee> result = new ArrayList<Employee>();
        result.add(director);

        Iterator<Department> depIt = departments.iterator();
        while (depIt.hasNext()) {
            Department department = depIt.next();
            result.add(department.getHead());

            Iterator<Employee> empIt = department.getEmployees().iterator();
            while (empIt.hasNext()) {
                result.add(empIt.next());
            }
        }
        return result;
    }
}

public class Task1 {
    public static void main(String[] args) {
        Firm firm = new Firm("TechCorp", new Employee("Іван", "Петренко", 9000));

        Department dev = new Department("Розробка", new Employee("Олег", "Коваль", 5000));
        dev.addEmployee(new Employee("Марія", "Шевченко", 4200));
        dev.addEmployee(new Employee("Андрій", "Бондар", 5500)); // більше ніж у начальника

        Department hr = new Department("Кадри", new Employee("Ольга", "Мельник", 3500));
        hr.addEmployee(new Employee("Наталя", "Ткач", 2800));
        hr.addEmployee(new Employee("Петро", "Кравчук", 3000));

        firm.addDepartment(dev);
        firm.addDepartment(hr);

        System.out.println("Задача 1. Максимальна зарплата: " + firm.findMaxSalary());

        Department found = firm.findDepartmentWithRichEmployee();
        if (found != null) {
            System.out.println("Задача 2. Знайдено відділ: " + found.getName());
        } else {
            System.out.println("Задача 2. Такого відділу немає");
        }

        System.out.println("Задача 3. Усі співробітники фірми:");
        for (Employee e : firm.getAllEmployees()) {
            System.out.println("  " + e);
        }
    }
}
