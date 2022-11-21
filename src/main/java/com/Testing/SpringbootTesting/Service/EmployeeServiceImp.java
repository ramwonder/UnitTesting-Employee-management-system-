package com.Testing.SpringbootTesting.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Testing.SpringbootTesting.Exception.ResourceNotFoundException;
import com.Testing.SpringbootTesting.Repository.EmployeeRepository;
import com.Testing.SpringbootTesting.model.Employee;

@Service
public class EmployeeServiceImp implements EmployeeService {


	EmployeeRepository employeeRepository;
	public EmployeeServiceImp(EmployeeRepository employeeRepository) {
		// TODO Auto-generated constructor stub
		this.employeeRepository=employeeRepository;
	}
	@Override
	public Employee saveEmployee(Employee emp) {
		// TODO Auto-generated method stub
		Optional<Employee> employee= employeeRepository.findByEmail(emp.getEmail());
		if(employee.isPresent())
		{
			throw new ResourceNotFoundException("Employee alredy exist with given email:"+ emp.getEmail());
		}
		
		return employeeRepository.save(emp);
	}
	@Override
	public List<Employee> getallEmployee() {
		// TODO Auto-generated method stub
		return employeeRepository.findAll();
	}
	@Override
	public Optional<Employee> GetEmployeeById(long id) {
		// TODO Auto-generated method stub
		return employeeRepository.findById(id);
	}
	@Override
	public Employee UpdateEmployee(Employee emp) {
		// TODO Auto-generated method stub
//		Employee employee=GetEmployeeById(emp.getId()).get();
//		employee.setFirstname(emp.getFirstname());
//		employee.setLastname(emp.getLastname());
//		employee.setEmail(emp.getEmail());
		
		
		return employeeRepository.save(emp);
	}
	@Override
	public void DeleteEmployee(long id) {
		// TODO Auto-generated method stub
		employeeRepository.deleteById(id);
		
	}

}
