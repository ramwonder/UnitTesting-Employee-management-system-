package com.Testing.SpringbootTesting.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.Testing.SpringbootTesting.Service.EmployeeServiceImp;
import com.Testing.SpringbootTesting.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.status.Status;

@WebMvcTest
public class EmployeeControllerTest {

	@Autowired
	private MockMvc mockmvc;
	
	@MockBean
	private EmployeeServiceImp employeeServiceImp;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void   givenEmployeeObject_whenCreateEmployee_thenReturnEmployee() throws Exception
	{
		//given -precondition
		Employee employee=Employee.builder()
					.id(1L)
					.firstname("Ramya")
					.lastname("Thirupathi")
					.email("ramya@gmail.com")
					.build();
		BDDMockito.given(employeeServiceImp.saveEmployee(ArgumentMatchers.any(Employee.class)))
		.willAnswer((invocation)-> invocation.getArgument(0));
		//When-action or behavior
		ResultActions response=	mockmvc.perform(MockMvcRequestBuilders.post("/employees")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(employee)));
	     
		//then b=verify the output
	response.andDo(MockMvcResultHandlers.print())
	                 .andExpect(MockMvcResultMatchers.status().isCreated())
	                 .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", CoreMatchers.is(employee.getFirstname())))  
	                 .andExpect(MockMvcResultMatchers.jsonPath("$.lastname",CoreMatchers.is(employee.getLastname())))
	                 .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
	
     }
		
	@Test
	public void   givenListofEmployee_whenGetAllEmployee_thenReturnListofEMployee() throws Exception
	{
		//given -precondition
		List<Employee> employee=new ArrayList<>();
		employee.add(Employee.builder()
				.id(1L)
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya@gmail.com")
				.build());
		employee.add(Employee.builder()
				.id(2L)
				.firstname("Shalini")
				.lastname("Periyashamy")
				.email("shalu@gmail.com")
				.build());
		BDDMockito.given(employeeServiceImp.getallEmployee()).willReturn(employee);
		
		
		//When-action or behavior
		
		ResultActions response=	mockmvc.perform(MockMvcRequestBuilders.get("/employees"));
	     
		//then b=verify the output
		response.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(employee.size())));
       
		
	}
	@Test
	public void   givenEmployeebyid_whenGetEmployeebyid_thenEmployeeObject() throws Exception
	{
		//given -precondition
		long emp_id=1L;
		Employee employee=Employee.builder()
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya@gmail.com")
				.build();
		BDDMockito.given(employeeServiceImp.GetEmployeeById(emp_id)).willReturn(Optional.of(employee));
		
		
		//When-action or behavior
		ResultActions response=	mockmvc.perform(MockMvcRequestBuilders.get("/employees/{id}",emp_id));
		//then b=verify the output
		response.andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstname",CoreMatchers.is(employee.getFirstname())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastname",CoreMatchers.is(employee.getLastname())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(employee.getEmail())));
       
		
		
		
		
	}
	@DisplayName("Employee get by id negative senario")
	@Test
	public void   givenEmployeebyidNagative_whenGetEmployeebyid_thenEmployeeObject() throws Exception
	{
		//given -precondition
		long emp_id=1L;
		Employee employee=Employee.builder()
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya@gmail.com")
				.build();
		BDDMockito.given(employeeServiceImp.GetEmployeeById(emp_id)).willReturn(Optional.empty());
		
		
		//When-action or behavior
		ResultActions response=	mockmvc.perform(MockMvcRequestBuilders.get("/employees/{id}",emp_id));
		//then b=verify the output
		response.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
		
		
		
		
		
	}
	@DisplayName("junit test update employee rest api")
	@Test
	public void   givenUpdateEmployee_whenUpdateEmployee_thenReturnUpdateEmployee() throws JsonProcessingException, Exception
	{
		//given -precondition
		long emp_id=1L;
		Employee savedemployee=Employee.builder()
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya@gmail.com")
				.build();
		Employee Updatedemployee=Employee.builder()
				.firstname("Ram")
				.lastname("Thiru")
				.email("ramyathiru@gmail.com")
				.build();
		
		BDDMockito.given(employeeServiceImp.GetEmployeeById(emp_id)).willReturn(Optional.of(savedemployee));
		BDDMockito.given(employeeServiceImp.UpdateEmployee(ArgumentMatchers.any(Employee.class)))
		.willAnswer((invocation)-> invocation.getArgument(0));
		//When-action or behavior
		ResultActions response=	mockmvc.perform(MockMvcRequestBuilders.put("/employees/{id}",emp_id)
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(Updatedemployee)));
	     
		
		//then b=verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print())
		.andExpect(MockMvcResultMatchers.jsonPath("$.firstname",CoreMatchers.is(Updatedemployee.getFirstname())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastname",CoreMatchers.is(Updatedemployee.getLastname())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email",CoreMatchers.is(Updatedemployee.getEmail())));
       
		
		
	}

	@DisplayName("update employee nagative senario")
	@Test
	public void   givenUpdateEmployeeNegative_whenUpdateEmployee_thenReturnUpdateEmployee() throws JsonProcessingException, Exception
	{
		//given -precondition
		long emp_id=1L;
		Employee savedemployee=Employee.builder()
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya@gmail.com")
				.build();
		Employee Updatedemployee=Employee.builder()
				.firstname("Ram")
				.lastname("Thiru")
				.email("ramyathiru@gmail.com")
				.build();
		
		BDDMockito.given(employeeServiceImp.GetEmployeeById(emp_id)).willReturn(Optional.empty());
		BDDMockito.given(employeeServiceImp.UpdateEmployee(ArgumentMatchers.any(Employee.class)))
		.willAnswer((invocation)-> invocation.getArgument(0));
		//When-action or behavior
		ResultActions response=	mockmvc.perform(MockMvcRequestBuilders.put("/employees/{id}",emp_id)
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(objectMapper.writeValueAsString(Updatedemployee)));
	     
		
		//then b=verify the output
		response.andExpect(MockMvcResultMatchers.status().isNotFound())
		.andDo(MockMvcResultHandlers.print());
		
		
		
	}

	@DisplayName("Delete Employee by id")
	@Test
	public void   givenEmployeeId_whenDeleteEmployee_thenReturnStatus200() throws Exception
	{
		//given -precondition
		
		long emp_id=1L;
	
		BDDMockito.willDoNothing().given(employeeServiceImp).DeleteEmployee(emp_id);
		//When-action or behavior
		ResultActions response=	mockmvc.perform(MockMvcRequestBuilders.delete("/employees/{id}",emp_id));
	     
		//then b=verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}

		
	}


