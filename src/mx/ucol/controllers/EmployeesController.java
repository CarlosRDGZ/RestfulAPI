package mx.ucol.controllers;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mx.ucol.models.Employee;

/**
 * 
 * @author CarlosFco
 */

public class EmployeesController implements Controller<Employee>
{
    private final String controller = "com.mysql.cj.jdbc.Driver";
    private Connection connection;
    private Statement statement;
    private String sql;
    
    public EmployeesController()
    {
        try
        {
            Class.forName(controller);
            connection = DriverManager.getConnection("jdbc:mysql://localhost/restful_api?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Override
    public List<Employee> getAll()
    {
        List<Employee> employees = new ArrayList<>();
        ResultSet result;
        try
        {
            result = statement.executeQuery("SELECT* FROM employees");
            
            while (result.next())
            {
                employees.add(new Employee(result.getInt("id"), result.getString("name")));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return employees;
    }
    
    @Override
    public Employee getById(int id)
    {
        return new Employee(3, "Tres");
    }
    
    @Override
    public void create(Employee employee)
    {
        try
        {
            statement.executeUpdate("INSERT INTO employees VALUES (" + 
                    employee.getId() + ", \"" + 
                    employee.getName() + "\")");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Override
    public void update(Employee e) 
    {
        System.out.println("Update:" + e.getName());
    }
    
    @Override
    public void delete(int id) 
    {
        System.out.println("Delete:" + id);
    }
}