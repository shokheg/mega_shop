package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.service.abstracts.ReadWriteService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ReadWriteServiceImpl<T, K> implements ReadWriteService<T, K> {

    protected final ReadWriteDao<T, K> dao;

    public ReadWriteServiceImpl(ReadWriteDao<T, K> dao) {
        this.dao = dao;
    }

    @Override
    @Transactional
    public T persist(T entity) {
        dao.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public void update(T entity) {
        dao.update(entity);
    }

    @Override
    @Transactional
    public void delete(T entity) {
        dao.delete(entity);
    }

    @Override
    @Transactional
    public void deleteByIdCascadeEnable(K id) {
        dao.deleteByIdCascadeEnable(id);
    }

    @Override
    @Transactional
    public void deleteByIdCascadeIgnore(K id) {
        dao.deleteByIdCascadeIgnore(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(K id) {
        return dao.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(K id) {
        return dao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return dao.findAll();
    }
}
