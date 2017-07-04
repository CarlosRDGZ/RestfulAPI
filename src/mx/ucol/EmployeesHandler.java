package mx.ucol;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.OutputStream;
import java.util.List;
import mx.ucol.controllers.EmployeesController;
import mx.ucol.models.Employee;

public class EmployeesHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange packet) {
        String requestMethod = packet.getRequestMethod();
        EmployeesController controller = new EmployeesController();
        List<Employee> employees;
        
        if(requestMethod.equalsIgnoreCase("get"))
        {
            employees = controller.getAll();
            
            for (Employee employee : employees) {
                System.out.println(employee.getId() + ": " + employee.getName());
            }
        }
        else if(requestMethod.equalsIgnoreCase("post"))
        {
            controller.create(new Employee(4, "Cuatro"));
        }
        else if(requestMethod.equalsIgnoreCase("put"))
        {
            controller.update(new Employee(4, "Cuatro"));
        }
        else if(requestMethod.equalsIgnoreCase("delete"))
        {
            controller.delete(1);
        }
        
        String response = "OK";
        
        try {
            packet.sendResponseHeaders(200, 0);
            OutputStream out = packet.getResponseBody();
            out.write(response.getBytes());
            out.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    
}
