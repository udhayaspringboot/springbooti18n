package com.deptempspbsecurityi18.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.deptempspbsecurityi18.model.Department;
import com.deptempspbsecurityi18.model.Employee;
@Repository
public class DeptEmpDaoImpl implements DeptEmpDao{

	
	@Autowired
	DeptRepo deptRepo;
	
	@Autowired
	EmpRepo empRepo;
	
	@Override
	public boolean createDept(Department dept) {
		deptRepo.save(dept);
		return false;
	}

	@Override
	public boolean updateDept(Department dept) {
		deptRepo.save(dept);
		return false;
	}

	@Override
	public List<Department> readAllDept() {
		List<Department> lDept = (List<Department>) deptRepo.findAll();
		return lDept;
	}

	@Override
	public boolean delteDept(int deptId) {
		deptRepo.deleteById(deptId);
		return false;
	}

	@Override
	public boolean createEmp(Employee employee) {
		empRepo.save(employee);
		return false;
	}

	@Override
	public boolean updateEmp(Employee employee) {
		empRepo.save(employee);
		return false;
	}

	@Override
	public List<Employee> readEmpFromDept(int deptId) {
		List<Employee> lEmp = empRepo.findByDepartment(deptId);
		return lEmp;
	}

	@Override
	public boolean deleteEmpFromDept(int deptId, int empId) {
		empRepo.deleteByEmpIdAndDepartment(empId,deptId);
		return false;
	}

	
	
	
	

}
