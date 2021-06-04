package dao;

import java.util.List;

public interface DAO <Entity,Key> {
    void create(Entity entity);
    List<Entity> findAll();
    Entity readById(Key key);
    boolean update(Entity entity,Key key);
    boolean delete(Key key);
}
