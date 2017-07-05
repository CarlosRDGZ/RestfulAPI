package mx.ucol;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import mx.ucol.controllers.EmployeesController;
import mx.ucol.models.Employee;

public class EmployeesHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange packet)
    {
        String requestMethod = packet.getRequestMethod();
        EmployeesController controller = new EmployeesController();
        List<Employee> employees;
        String response = "";
        
        if(requestMethod.equalsIgnoreCase("get"))
        {
            System.out.printf("%n%s%n","Method: " + packet.getRequestMethod());
            employees = controller.getAll();
            response = "List of employees";
            for (Employee employee : employees) {
                System.out.println(employee.getId() + ": " + employee.getName());
            }
        }
        else if(requestMethod.equalsIgnoreCase("post"))
        {
            System.out.printf("%n%s%n","Method: " + packet.getRequestMethod());
            controller.create(new Employee(4, "Cuatro"));
            response = "Employee created";
        }
        else if(requestMethod.equalsIgnoreCase("put"))
        {
            System.out.printf("%n%s%n","Method: " + packet.getRequestMethod());
            controller.update(new Employee(4, "Cuatro"));
            response = "Employee update";
        }
        else if(requestMethod.equalsIgnoreCase("delete"))
        {
            System.out.printf("%n%s%n","Method: " + packet.getRequestMethod());
            controller.delete(1);
            response = "Employee delete";
        }
        
        try
        {
            packet.sendResponseHeaders(200, 0);
            try (OutputStream out = packet.getResponseBody()) {
                out.write(response.getBytes());
                out.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
}
