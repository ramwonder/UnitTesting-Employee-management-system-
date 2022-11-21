package com.Testing.SpringbootTesting.Service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.hibernate.mapping.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.Testing.SpringbootTesting.Exception.ResourceNotFoundException;
import com.Testing.SpringbootTesting.Repository.EmployeeRepository;
import com.Testing.SpringbootTesting.model.Employee;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EmployeeServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;
	@InjectMocks
	private EmployeeServiceImp employeeService;
	private Employee employee;
	
	@BeforeEach
	public void setup()
	{
//		employeeRepository=Mockito.mock(EmployeeRepository.class);
//	    employeeService=new EmployeeServiceImp(employeeRepository);
		 employee=Employee.builder()
				.id(1L)
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya@gmail.com")
				.build();
	}
	@DisplayName("junit test for saveEmployee method")
	@Test
	public void   givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject()
	{
		//given -precondition
		
		BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
		.willReturn(Optional.empty());
		BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
		
		System.out.println(employeeRepository);
		System.out.println(employeeService);
		//When-action or behavior
		Employee savedemp=employeeService.saveEmployee(employee);
		
		System.out.println(savedemp);
		//then b=verify the output
		Assertions.assertThat(savedemp).isNotNull();
		
		
	}
	@DisplayName("junit test for saveEmployee method which throws exception")
	@Test
	public void   givenEmployeeObject_whenSaveEmployeeWhichthrowException_thenReturnEmployeeObject()
	{
		//given -precondition
		
		BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
		.willReturn(Optional.of(employee));
	//	BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
		
		System.out.println(employeeRepository);
		System.out.println(employeeService);
		//When-action or behavior
		org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()->{
			employeeService.saveEmployee(employee);
		});
		
		//then
		verify(employeeRepository,Mockito.never()).save(any(Employee.class));
		
	}
	@DisplayName("Get all Employee positive senorio")
	@Test
	public void   givenEmployeeList_whenGetAllEmployee_thenReturnEMployeeList()
	{
		//given -precondition
	Employee employee1=Employee.builder()
				.id(2L)
				.firstname("shalini")
				.lastname("srini")
				.email("shalini@gmail.com")
				.build();
		BDDMockito.given(employeeRepository.findAll()).willReturn(List.of(employee,employee1));
		
		//When-action or behavior
		List<Employee> employeeList=employeeService.getallEmployee();
		
		
		//then b=verify the output
		Assertions.assertThat(employeeList).isNotNull();
		Assertions.assertThat(employeeList.size()).isEqualTo(2);
		
	}
	@DisplayName("Get all Employee nagative senorio")
	@Test
	public void   givenEmployeeList_whenGetAllEmployeeNegativesenorio_thenReturnEMployeeList()
	{
		//given -precondition
	Employee employee1=Employee.builder()
				.id(2L)
				.firstname("shalini")
				.lastname("srini")
				.email("shalini@gmail.com")
				.build();
		BDDMockito.given(employeeRepository.findAll()).willReturn(Collections.emptyList());
		
		//When-action or behavior
		List<Employee> employeeList=employeeService.getallEmployee();
		
		
		//then b=verify the output
		Assertions.assertThat(employeeList).isEmpty();;
		Assertions.assertThat(employeeList.size()).isEqualTo(0);
		
	}
	@DisplayName("junit test for get employee by id")
	@Test
	public void   givenEmployeeObject_whenEmployeeFindByid_thenReturnEmployee()
	{
		//given -precondition
		BDDMockito.given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
		
		
		
		//When-action or behavior

		Employee emp=employeeService.GetEmployeeById(employee.getId()).get();
		
		
		//then b=verify the output
		
		Assertions.assertThat(emp).isNotNull();
		
	}
	@DisplayName("junit for update employee")
	@Test
	public void   givenUpdateEmployeeObject_whenSaveEmployee_thenReturnUpdatedEmployee()
	{
		//given -precondition
		BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);
		employee.setEmail("shal@gmail.com");
		employee.setFirstname("Raj");
		
		//When-action or behavior
		
		Employee UpdatedEmployee=employeeService.UpdateEmployee(employee);
		//then b=verify the output
		
		Assertions.assertThat(UpdatedEmployee.getEmail()).isEqualTo("shal@gmail.com");
		Assertions.assertThat(UpdatedEmployee.getFirstname()).isEqualTo("Raj");
		
	}
    @DisplayName("junit for delete employee by id")
	@Test
	public void   given_when_then()
	{
		long emp_id=1L;
		//given -precondition
		BDDMockito.willDoNothing().given(employeeRepository).deleteById(1L);
		
		//When-action or behavior
		employeeService.DeleteEmployee(emp_id);
		
		//then b=verify the output
		
		verify(employeeRepository,Mockito.times(1)).deleteById(emp_id);
		
	}

	


	

}
