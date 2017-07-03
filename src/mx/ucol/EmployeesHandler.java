package mx.ucol;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.OutputStream;

public class EmployeesHandler implements HttpHandler{

    @Override
    public void handle(HttpExchange packet) {
        System.out.println("Method:" + packet.getRequestMethod());
        
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
