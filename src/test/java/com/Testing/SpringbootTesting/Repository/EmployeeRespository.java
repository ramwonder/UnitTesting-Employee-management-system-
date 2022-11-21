package com.Testing.SpringbootTesting.Repository;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.Testing.SpringbootTesting.Integration.AbstractContainerBaseTest;
import com.Testing.SpringbootTesting.model.Employee;

@DataJpaTest
public class EmployeeRespository extends AbstractContainerBaseTest {

	@Autowired
	private EmployeeRepository employeeRepository;
	Employee employee;
	@BeforeEach
	public void setup()
	{
		 employee=Employee.builder()
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya67@gmail.com")
				.build();
	}
    //Juint test for save employee operation
	@DisplayName("Juint test for save employee operation")
	@Test
	public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

		//given-precondition or setup
//		Employee employee=Employee.builder()
//				.firstname("Ramya")
//				.lastname("Thirupathi")
//				.email("ramya67@gmail.com")
//				.build();
		//When-action or behavior
		Employee savedEmployee =employeeRepository.save(employee);
		//then b=verify the output
		Assertions.assertThat(savedEmployee).isNotNull();
		Assertions.assertThat(savedEmployee.getId()).isGreaterThan(0);
		
	}
	//junit test for  givenEmployeeList
	@Test
	public void   givenEmployeeList_whenFindAll_thenEmployeeList()
	{
		//given -precondition
		Employee employee=Employee.builder()
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya67@gmail.com")
				.build();
		Employee employee1=Employee.builder()
				.firstname("hema")
				.lastname("srinivasan")
				.email("ramya67@gmail.com")
				.build();
		employeeRepository.save(employee);
		employeeRepository.save(employee1);
		//When-action or behavior
		List<Employee> employeeList=employeeRepository.findAll();
		
		//then b=verify the output
		Assertions.assertThat(employeeList).isNotNull();
		Assertions.assertThat(employeeList.size()).isEqualTo(2);
		
		
	}
	@Test
	public void   givenEmployeeList_whenFindById_thenReturnEmployee()
	{
		//given -precondition
		Employee employee=Employee.builder()
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya67@gmail.com")
				.build();
		
		employeeRepository.save(employee);
		
		
		//When-action or behavior
		Employee employeeobject=employeeRepository.findById(employee.getId()).get();
		
		
		//then b=verify the output
		Assertions.assertThat(employeeobject).isNotNull();
		
		
	}
	@Test
	public void   givenEmployee_whenFindByEmail_thenReturnEmployee()
	{
		//given -precondition
		Employee employee=Employee.builder()
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya67@gmail.com")
				.build();
		
		employeeRepository.save(employee);
		
		//When-action or behavior
		Employee employeeobject=employeeRepository.findByEmail(employee.getEmail()).get();
		
		//then b=verify the output
		Assertions.assertThat(employeeobject).isNotNull();
		
	}

	
	@Test
	public void   givenEmployee_whenUpdateEmployee_thenReturnUpdatedEmployee()
	{
		//given -precondition
		Employee employee=Employee.builder()
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya67@gmail.com")
				.build();
		employeeRepository.save(employee);
		//When-action or behavior
		Employee employeeobject=employeeRepository.findById(employee.getId()).get();
		employeeobject.setEmail("ramyathirupathi90@gmail.com");
		employeeobject.setFirstname("Ramaya");
		
		Employee UpdateEmployee =employeeRepository.save(employeeobject);
		//then b=verify the output
		Assertions.assertThat(UpdateEmployee.getEmail()).isEqualTo("ramyathirupathi90@gmail.com");
		Assertions.assertThat(UpdateEmployee.getFirstname()).isEqualTo("Ramaya");
		
	}
	

	@Test
	public void   givenEmployee_whenRemove_thenReturnNull()
	{
		//given -precondition
		Employee employee=Employee.builder()
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya67@gmail.com")
				.build();
		employeeRepository.save(employee);
		
		//When-action or behavior
		employeeRepository.delete(employee);
		Optional<Employee> employeeobject=employeeRepository.findById(employee.getId());
		
		//then b=verify the output
		Assertions.assertThat(employeeobject).isEmpty();
		
	}
	@Test
	public void   givenFirstNameLastname_whenFindByjpql_thenReturnEmployeeObject()
	{
		//given -precondition
		Employee employee=Employee.builder()
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya67@gmail.com")
				.build();
		employeeRepository.save(employee);
		String firstname="Ramya";
		String lastname="Thirupathi";
		//When-action or behavior
		
	Employee savedemp= employeeRepository.findByname(firstname,lastname);
		//then b=verify the output
		Assertions.assertThat(savedemp).isNotNull();
	}

	@Test
	public void   givenFirstNameLastname_whenFindByNative_thenReturnEmployeeObject()
	{
		//given -precondition
		Employee employee=Employee.builder()
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya67@gmail.com")
				.build();
		employeeRepository.save(employee);
//		String firstname="Ramya";
//		String lastname="Thirupathi";
		//When-action or behavior
		
	Employee savedemp= employeeRepository.findByNativename(employee.getFirstname(),employee.getLastname());
		//then b=verify the output
		Assertions.assertThat(savedemp).isNotNull();
	}


	

	
}
