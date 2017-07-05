package mx.ucol;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * 
 * @author CarlosFco
 */

public class Server 
{
    public static void main(String[] args)
    {
        try
        {
            HttpServer server = HttpServer.create(new InetSocketAddress(3000), 0);
            server.createContext("/employees", new EmployeesHandler());
            server.setExecutor(null);
            server.start();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
        
}
