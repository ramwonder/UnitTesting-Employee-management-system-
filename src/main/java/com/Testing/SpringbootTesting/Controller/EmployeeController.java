package com.Testing.SpringbootTesting.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Testing.SpringbootTesting.Service.EmployeeServiceImp;
import com.Testing.SpringbootTesting.model.Employee;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeServiceImp employeeServiceImp;
	
	@PostMapping("/employees")
	@ResponseStatus(HttpStatus.CREATED)
	public Employee CreateEmployee(@RequestBody Employee emp)
	{
		
		return employeeServiceImp.saveEmployee(emp);
	}
	
	@GetMapping("/employees")
	public List<Employee> GetallEmployee()
	{
		
		return employeeServiceImp.getallEmployee();
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> GetEmployeeById(@PathVariable Long id)
	{
		return employeeServiceImp.GetEmployeeById(id)
				.map(ResponseEntity::ok)
				.orElseGet(()->ResponseEntity.notFound().build());
	}
	
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> UpdateEmployee(@PathVariable Long id,@RequestBody Employee emp)
	{
		
		return employeeServiceImp.GetEmployeeById(id)
				.map(saveEmp->{
					saveEmp.setFirstname(emp.getFirstname());
					saveEmp.setLastname(emp.getLastname());
					saveEmp.setEmail(emp.getEmail());
				Employee updatedEmployee=employeeServiceImp.UpdateEmployee(saveEmp);
					return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);
					
				}).orElseGet(()->ResponseEntity.notFound().build());
	}
	
	
	@DeleteMapping("/employees/{id}")
	public ResponseEntity<String> DeleteEmployee(@PathVariable Long id)
	{
		employeeServiceImp.DeleteEmployee(id);
		return new ResponseEntity<String>("Employee deleted successfully!",HttpStatus.OK);
	}
	
	
}
