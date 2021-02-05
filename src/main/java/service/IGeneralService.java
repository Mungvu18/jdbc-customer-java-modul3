package service;

import java.util.List;

public interface IGeneralService <T>{
    List<T> findAll();
    T creat(int id,T t);
    T findById(int id);
    T update(int id, T t);
    void delete(int id);
}
