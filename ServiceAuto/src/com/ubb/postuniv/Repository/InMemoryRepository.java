package com.ubb.postuniv.Repository;

import com.ubb.postuniv.Domain.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<T extends Entity> implements IRepository<T> {

    private Map<Integer, T> storage;

    public InMemoryRepository() {
        this.storage = new HashMap<>();
    }

    @Override
    public void create(T entity) throws Exception {
        if (this.storage.containsKey(entity.getId())) {
            throw new Exception("There is already an entity with the id: " + entity.getId());
        }
        this.storage.put(entity.getId(), entity);

    }

    @Override
    public void update(T entity) throws Exception {
        if (!this.storage.containsKey(entity.getId())) {
            throw new Exception("There is no entity with the id: " + entity.getId() + " to update.");
        }
        this.storage.put(entity.getId(), entity);

    }

    @Override
    public List<T> readAll() {
        return new ArrayList<>(this.storage.values());
    }

    @Override
    public T read(int id) {
        return this.storage.get(id);
    }

    @Override
    public void delete(int id) throws Exception {
        if (!this.storage.containsKey(id)) {
            throw new Exception("There is no entity with the id: " + id + " to delete.");
        }
        this.storage.remove(id);
    }
}
