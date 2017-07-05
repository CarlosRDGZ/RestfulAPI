package mx.ucol;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import mx.ucol.controllers.EmployeesController;
import mx.ucol.models.Employee;

/**
 * 
 * @author CarlosFco
 */

public class EmployeesHandler implements HttpHandler
{
    @Override
    public void handle(HttpExchange packet)
    {
        String requestMethod = packet.getRequestMethod();
        String context = packet.getRequestURI().toString();
        EmployeesController controller = new EmployeesController();
        List<Employee> employees;
        
        String[] contextArray = context.split("/");
        
        /*
        
        Obtener todos los registros
        GET /employees
        
        
        Obtener registro por id
        GET /employees/:id
        
        Crear un registro
        POST /employees
        
        {"id":6, "name":"Nombre"}
        
        Actualizar un registro
        PUT /employees/:id
        
        Borrar registro
        DELETE /employees/:id
        
        */
        
        Employee temp;
        
        System.out.println("Method: " + packet.getRequestMethod());
        System.out.println(context + "\n");

        if(requestMethod.equalsIgnoreCase("get"))
        {
            if(contextArray.length == 2)
            {
                employees = controller.getAll();
            }
            else if(contextArray.length == 3)
            {
                try
                {
                    int id = Integer.parseInt(contextArray[2]);
                    temp = controller.getById(id);
                    System.out.println("Name: " + temp.getName());
                    
                } catch (NumberFormatException e)
                {
                    System.err.println(e.getMessage());
                }
                
            }
        }
        else if(contextArray.length == 2 && requestMethod.equalsIgnoreCase("post"))
        {
            try
            {
                InputStreamReader isr = new InputStreamReader(packet.getRequestBody(), "utf-8");
                BufferedReader buffer = new BufferedReader(isr);
                String value = buffer.readLine();
                System.out.println(value);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
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
        
        try
        {
            packet.sendResponseHeaders(200, 0);
            try (OutputStream out = packet.getResponseBody())
            {
                out.write(response.getBytes());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
