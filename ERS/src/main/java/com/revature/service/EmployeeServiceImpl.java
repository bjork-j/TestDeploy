package com.revature.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.EmployeeDao;
import com.revature.dao.EmployeeDaoImpl;
import com.revature.exception.EmployeeNotFoundException;
import com.revature.models.Employee;

public class EmployeeServiceImpl implements EmployeeService {
	
	private final EmployeeDao dao = new EmployeeDaoImpl();
	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public Object process(HttpServletRequest request, HttpServletResponse response) {
		if (request.getMethod().equals("GET")) {
			// GET ALL LOGIC
			String[] path = request.getRequestURI().split("/");
			if (path.length == 4) {		// execute if the request is /PaceServletExamples/rest/employees
				return dao.getAllEmployees();
			}
			// GET ONE LOGIC
			if (path.length == 5) {		// execute if request looks like /PaceServletExamples/rest/employees/3
				try {
					int employeeId = Integer.valueOf(path[4]);
					return dao.getEmployeeById(employeeId);
				} catch (NumberFormatException e) {
					return "Cannot convert " + path[4] + " into a number";
				} catch (EmployeeNotFoundException e) {
					return e.getMessage();
				}
			}
		}
		
		if (request.getMethod().equals("POST")) {
			// CREATE LOGIC
			if (request.getHeader("Content-Type").equals("application/json")) {
				try {
					Employee employee = mapper.readValue(request.getReader(), Employee.class);
					return dao.createEmployee(employee);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				// 415 is an Unsupported Media Type
				response.sendError(415, "Please create using application/json");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (request.getMethod().equals("UPDATE")) {
			// UPDATE LOGIC
			if (request.getHeader("Content-Type").equals("application/json")) {
				try {
					Employee employee = mapper.readValue(request.getReader(), Employee.class);
					return dao.updateEmployee(employee);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				// 415 is an Unsupported Media Type
				response.sendError(415, "Please create using application/json");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (request.getMethod().equals("DELETE")) {
			// DELETE LOGIC
			if (request.getHeader("Content-Type").equals("application/json")) {
				String[] path = request.getRequestURI().split("/");
				try {
					int employeeId = Integer.valueOf(path[4]);
					return dao.deleteEmployee(employeeId);
				} catch (NumberFormatException e) {
					return "Cannot convert " + path[4] + " into a number";
				} catch (EmployeeNotFoundException e) {
					return e.getMessage();
				}
			}
			try {
				// 415 is an Unsupported Media Type
				response.sendError(415, "Please create using application/json");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}