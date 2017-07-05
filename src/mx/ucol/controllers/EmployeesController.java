package mx.ucol.controllers;

import java.util.ArrayList;
import java.util.List;
import mx.ucol.models.Employee;

/**
 * 
 * @author CarlosFco
 */

public class EmployeesController implements Controller<Employee>
{
    @Override
    public List<Employee> getAll()
    {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Uno"));
        employees.add(new Employee(2, "Dos"));
        return employees;
    }
    
    @Override
    public Employee getById(int id)
    {
        return new Employee(3, "Tres");
    }
    
    @Override
    public void create(Employee e)
    {
        System.out.println("Create:" + e.getName());
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