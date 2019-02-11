package com.revature.dao;

import java.util.List;

import com.revature.models.Employee;

public interface EmployeeDao {

	// Read All
	List<Employee> getAllEmployees();
	
	// Read one
	Employee getEmployeeById(int id);
	
	// Create
	Employee createEmployee(Employee employee);
	
	// Update
	Employee updateEmployee(Employee employee);
	
	// Delete
	Employee deleteEmployee(int id);
}