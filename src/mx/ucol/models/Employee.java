package mx.ucol.models;

/**
 * 
 * @author CarlosFco
 */

public class Employee
{
    private int id;
    private String name;
    
    public Employee()
    {
        super();
    }
    
    public Employee(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}