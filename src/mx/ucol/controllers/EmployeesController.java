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
                employees.add(new Employee(result.getInt("id"), result.getString("name")));
            
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return employees;
    }
    
    @Override
    public Employee getById(int id)
    {
        ResultSet result;
        Employee employee = null;
        try
        {
            result = statement.executeQuery("SELECT* FROM employees WHERE id = " + id);
            
            if(result.first())
                employee = new Employee(result.getInt("id"), result.getString("name"));
            
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return employee;
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
    public void update(Employee employee)
    {
        try
        {
            statement.executeUpdate("UPDATE employees SET name = \"" +
                    employee.getName() + "\" WHERE id = " + employee.getId());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    @Override
    public void delete(int id) 
    {
        try
        {
            statement.executeUpdate("DELETE FROM employees WHERE id = " + id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}