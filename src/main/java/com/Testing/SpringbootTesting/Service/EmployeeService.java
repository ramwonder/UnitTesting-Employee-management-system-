package com.Testing.SpringbootTesting.Service;

import java.util.List;
import java.util.Optional;

import com.Testing.SpringbootTesting.model.Employee;

public interface EmployeeService {
	

	Employee saveEmployee(Employee emp);
	List<Employee> getallEmployee();
	Optional<Employee> GetEmployeeById(long id);
	Employee UpdateEmployee(Employee emp);
	public void DeleteEmployee(long id);
		
	
	
}
