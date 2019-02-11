package com.revature.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.data.DataSource;
import com.revature.exception.EmployeeNotFoundException;
import com.revature.models.Employee;

public class EmployeeDaoImpl implements EmployeeDao {

	final static Logger log = Logger.getLogger(EmployeeDaoImpl.class);
	private final DataSource dataSource = DataSource.getInstance();
	
	// SELECT * FROM EMPLOYEE;
	@Override
	public List<Employee> getAllEmployees() {
		return dataSource.getEmployeeTable();
	}

	// SELECT * FROM EMPLOYEE WHERE ID = ?;
	@Override
	public Employee getEmployeeById(int id) {
		
		return dataSource.getEmployeeTable().stream()
					.filter(todo -> todo.getId() == id)
					.findFirst().orElseThrow(() -> new EmployeeNotFoundException(id));
	}
	
	@Override
	public Employee createEmployee(Employee employee) {
		if (dataSource.getEmployeeTable().add(employee)) {
			return employee;
		}
		return null;
	}
	
	@Override
	public Employee updateEmployee(Employee employee) {
		// Get a reference to existing Employee
		Employee updated = getEmployeeById(employee.getId());
		
		// Update the todo's state
		updated.setFirstname(employee.getFirstname());
		updated.setLastname(employee.getLastname());
		updated.setAddress(employee.getAddress());
		updated.setEmail(employee.getEmail());
		updated.setUsername(updated.getUsername());
		updated.setPassword(employee.getPassword());
		// Store the updated version
		for (int i = 0; i < dataSource.getEmployeeTable().size(); i++) {
			if (dataSource.getEmployeeTable().get(i).getId() == employee.getId()) {
				dataSource.getEmployeeTable().set(i, updated);
				return updated;
			}
		}
		return null;
	}


	
	@Override
	public Employee deleteEmployee(int id) {
		// Reference existing id
		Employee toBeRemoved = getEmployeeById(id);
		
		// For every employee in our table, remove if the todo's id equals the parameter
		dataSource.getEmployeeTable().removeIf(anything -> anything.getId() == id);
		return toBeRemoved;
	}

}