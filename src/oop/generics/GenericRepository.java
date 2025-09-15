package oop.generics;

import java.util.List;

public interface GenericRepository<T> {
    T getById(Integer id);// select * from tabke where id = &id

    List<T> getAll(); //select * from table

    T create(T newRow); //insert into table(......) values()

    T update(T updateRow, Integer id); //update table set ....where id = ?id

    void delete(T entity); //delete from table where entity_id = entity.getId();


}
