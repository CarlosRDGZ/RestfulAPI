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
        
        ObjectMapper mapper = new ObjectMapper();
        
        Employee temp;
        
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
                    response = mapper.writeValueAsString(controller.getById(Integer.parseInt(contextArray[2])));
                    
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
        else if(requestMethod.equalsIgnoreCase("put"))
        {
            try
            {
                controller.update(mapper.readValue(packet.getRequestBody(), Employee.class));
                response = "Employee update";
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        else if(requestMethod.equalsIgnoreCase("delete"))
        {
            controller.delete(1);
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
