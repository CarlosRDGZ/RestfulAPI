package mx.ucol;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * 
 * @author CarlosFco
 */
public class Server
{
    public static void main(String[] args)
    {
        System.out.println("Welcome to employees' server");
        try
        {
            HttpServer server = HttpServer.create(new InetSocketAddress(3000), 0);
            server.createContext("/employees", new EmployeesHandler());
            server.setExecutor(null);
            server.start();
            Scanner in = new Scanner(System.in);
            String command = in.nextLine();
            while(!command.equals("stop"))
                command = in.nextLine();
            server.stop(0);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
        
}
