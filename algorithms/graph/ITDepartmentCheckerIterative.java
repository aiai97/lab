package algorithms.graph;

import java.util.*;

public class ITDepartmentCheckerIterative {

    static class Employee {
        String id;
        String name;
        String title;
        String managerId;

        Employee(String id, String name, String title, String managerId) {
            this.id = id;
            this.name = name;
            this.title = title;
            this.managerId = managerId;
        }
    }

    // Check if the title indicates IT department
    static boolean isITTitle(String title) {
        if (title == null) return false;
        title = title.toLowerCase();
        return title.contains("it") || title.contains("info") || title.contains("information technology");
    }

    // Cache to store results of whether employee belongs to IT
    private final Map<String, Boolean> cache = new HashMap<>();

    /**
     * Determine if the employee belongs to IT department by checking
     * their own title and recursively checking their managers' titles.
     * Uses iteration instead of recursion to avoid stack overflow.
     *
     * @param employeeId the ID of the employee to check
     * @param employees  map of all employees by ID
     * @return true if employee or any manager is in IT, false otherwise
     */
    public boolean isITDepartment(String employeeId, Map<String, Employee> employees) {
        if (employeeId == null) return false;

        // Check if result is already cached
        if (cache.containsKey(employeeId)) {
            return cache.get(employeeId);
        }

        // Track visited employees in current path to avoid cycles
        Set<String> path = new HashSet<>();

        String currentId = employeeId;
        while (currentId != null) {
            // If cached, use cached result and update cache for all in path
            if (cache.containsKey(currentId)) {
                boolean cachedResult = cache.get(currentId);
                for (String id : path) {
                    cache.put(id, cachedResult);
                }
                return cachedResult;
            }

            // Detect cycles to prevent infinite loops
            if (path.contains(currentId)) {
                for (String id : path) {
                    cache.put(id, false);
                }
                return false;
            }

            path.add(currentId);
            Employee current = employees.get(currentId);
            if (current == null) {
                for (String id : path) {
                    cache.put(id, false);
                }
                return false;
            }

            // Check if current employee's title indicates IT department
            if (isITTitle(current.title)) {
                for (String id : path) {
                    cache.put(id, true);
                }
                return true;
            }

            currentId = current.managerId;
        }

        // If reached top without finding IT title, cache false for all in path
        for (String id : path) {
            cache.put(id, false);
        }
        return false;
    }

    public static void main(String[] args) {
        Map<String, Employee> employees = new HashMap<>();

        employees.put("1", new Employee("1", "Alice", "Developer", "2"));
        employees.put("2", new Employee("2", "Bob", "Engineering Manager", "3"));
        employees.put("3", new Employee("3", "Carol", "IT Director", null));
        employees.put("4", new Employee("4", "Dave", "HR Specialist", "5"));
        employees.put("5", new Employee("5", "Eve", "HR Director", null));
        employees.put("6", new Employee("6", "Frank", "Intern", "1"));

        ITDepartmentCheckerIterative checker = new ITDepartmentCheckerIterative();

        System.out.println("Is Alice IT? " + checker.isITDepartment("1", employees)); // true
        System.out.println("Is Dave IT? " + checker.isITDepartment("4", employees));  // false
        System.out.println("Is Frank IT? " + checker.isITDepartment("6", employees)); // true
    }
}
