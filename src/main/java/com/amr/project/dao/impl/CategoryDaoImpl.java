package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.CategoryDao;
import com.amr.project.model.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDaoImpl extends ReadWriteDaoImpl<Category, Long> implements CategoryDao {
}
