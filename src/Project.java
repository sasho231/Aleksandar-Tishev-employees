import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Project {
    private String id;
    private List<Employee> employees;

    public Project(String id){
        this.employees = new ArrayList<>();
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addEmployee(Employee employee){
        this.employees.add(employee);
    }

    public Pair<Long, Pair<Employee, Employee>> getBestPair() {
        long bestTime = 0;
        Employee employee1 = null;
        Employee employee2 = null;

        for (int i = 0; i < employees.size(); i++) {
            for (int j = i + 1; j < employees.size(); j++) {
                Long timeWorkingTogether = this.getTimeWorkingTogether(employees.get(i), employees.get(j));
                if(timeWorkingTogether > bestTime){
                    bestTime = timeWorkingTogether;
                    employee1 = employees.get(i);
                    employee2 = employees.get(j);
                }
            }
        }

        return new Pair<>(bestTime, new Pair<>(employee1, employee2));
    }

    private Long getTimeWorkingTogether(Employee employee1, Employee employee2) {
        if (employee1.getStart().getTime() < employee2.getStart().getTime()) {
           if (employee1.getEnd().getTime() >= employee2.getEnd().getTime()) {
               return employee2.getTotalTime();
           } else {
               return employee1.getEnd().getTime() - employee1.getStart().getTime();
           }
        } else {
            if (employee1.getEnd().getTime() > employee2.getEnd().getTime()) {
               return  employee2.getEnd().getTime() - employee1.getStart().getTime();
            } else {
                return employee1.getTotalTime();
            }
        }
    }
}