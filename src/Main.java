import javafx.util.Pair;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        if(args.length != 1){
            System.out.println("First you have to pass the log file's location as the first argument");
            return;
        }
        String logFilePath = args[0];

        Map<String, Project> projects = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader(logFilePath));
        String line = "";
        while ((line = reader.readLine()) != null){
            String[] data = line.split(", ");
            String employeeId = data[0];
            String projectId = data[1];
            Date startDate = parseDate(data[2]);
            Date endDate = parseDate(data[3]);

            Employee employee = new Employee(employeeId, startDate, endDate);
            if(!projects.containsKey(projectId)){
                Project project = new Project(projectId);
                project.addEmployee(employee);
                projects.put(projectId, project);
            } else {
                Project project = projects.get(projectId);
                project.addEmployee(employee);
            }
        }
        Pair<Long, Pair<Employee, Employee>> longestWorkingPair = null;
        String longestWorkingPairProjectId = "";

        for (Map.Entry<String, Project> entry : projects.entrySet()) {
            Pair<Long, Pair<Employee, Employee>> pair = entry.getValue().getBestPair();

            if(longestWorkingPair == null) {
                longestWorkingPair = pair;
                longestWorkingPairProjectId = entry.getKey();
            }

            if(pair.getKey() > longestWorkingPair.getKey()) {
                longestWorkingPair = pair;
                longestWorkingPairProjectId = entry.getKey();
            }
        }

        if (longestWorkingPair.getValue().getKey() != null) {
            System.out.println("Longest pair of employees working together are: "
                    + longestWorkingPair.getValue().getKey().getId() + " and "
                    + longestWorkingPair.getValue().getValue().getId() + " in project: " + longestWorkingPairProjectId
            );
        } else {
            System.out.println("Not enough data to create the statistics");
        }
    }

    public static Date parseDate(String date) throws ParseException {
        if (date.equals("NULL")) return Date.from(Instant.now());

        return new SimpleDateFormat("yyyy-MM-dd").parse(date);
    }
}
