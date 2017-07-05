package mx.ucol;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.channels.SeekableByteChannel;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.ucol.controllers.EmployeesController;
import mx.ucol.models.Employee;

public class EmployeesHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange packet) {
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
                    
                } catch (Exception e)
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
                
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
        else if(contextArray.length == 3 && requestMethod.equalsIgnoreCase("put"))
        {
            try
            {
                InputStreamReader isr = new InputStreamReader(packet.getRequestBody(), "utf-8");
                BufferedReader buffer = new BufferedReader(isr);
                String value = buffer.readLine();
                
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
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
