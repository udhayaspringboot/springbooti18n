package com.deptempspbsecurityi18.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.deptempspbsecurityi18.model.Department;
@Repository
public interface DeptRepo extends CrudRepository<Department, Integer> {

}
