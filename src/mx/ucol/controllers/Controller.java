package mx.ucol.controllers;

import java.util.List;

/**
 * 
 * @author CarlosFco
 * @param <T> 
 */

public interface Controller<T>
{
    public List<T> getAll();
    
    public T getById(int id);
    
    public void create(T t);
    
    public void update(T t);
    
    public void delete(int id);
}
