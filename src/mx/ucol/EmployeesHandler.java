package mx.ucol;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import mx.ucol.controllers.EmployeesController;
import mx.ucol.models.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;

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
        String[] contextArray = context.split("/");
        String response = "OK";
        
        ObjectMapper mapper = new ObjectMapper();
                
        System.out.println("Method: " + packet.getRequestMethod());
        System.out.println(context + "\n");

        if(requestMethod.equalsIgnoreCase("get"))
        {
            if(contextArray.length == 2)
            {
                try
                {
                    response = mapper.writeValueAsString(controller.getAll());
                } catch (JsonProcessingException e) {
                    System.err.println(e.getMessage());
                }
            }
            else if(contextArray.length == 3)
            {
                try
                {
                    Employee employee = controller.getById(Integer.parseInt(contextArray[2]));
                    if(employee != null)
                        response = mapper.writeValueAsString(employee);
                    else
                        response = "Not found";
                    
                } catch (NumberFormatException | JsonProcessingException e) {
                    System.err.println(e.getMessage());
                }
                
            }
        }
        else if(contextArray.length == 2 && requestMethod.equalsIgnoreCase("post"))
        {
            try
            {
                controller.create(mapper.readValue(packet.getRequestBody(), Employee.class));
                response = "Employee created";
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        else if(contextArray.length == 3 && requestMethod.equalsIgnoreCase("put"))
        {
            try
            {
                Employee employee = mapper.readValue(packet.getRequestBody(), Employee.class);
                if(Integer.parseInt(contextArray[2]) == employee.getId())
                {
                    controller.update(employee);
                    response = "Employee update";
                }
                else
                    response = "Id mismatch";
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        else if(contextArray.length == 3 && requestMethod.equalsIgnoreCase("delete"))
        {
            controller.delete(Integer.parseInt(contextArray[2]));
            response = "Employee delete";
        }
        
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
        