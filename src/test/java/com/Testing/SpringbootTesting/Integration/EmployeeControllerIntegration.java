package com.Testing.SpringbootTesting.Integration;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.Testing.SpringbootTesting.Repository.EmployeeRepository;
import com.Testing.SpringbootTesting.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc

public class EmployeeControllerIntegration  extends AbstractContainerBaseTest{
   
//	@Container
//	private  static MySQLContainer mySQLContainer=new MySQLContainer("mysql:latest");
	@Autowired
	private MockMvc mockmvc;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ObjectMapper objectMapper;
@BeforeEach
void setup()
{
	employeeRepository.deleteAll();
}
@Test
public void   givenEmployeeObject_whenCreateEmployee_thenReturnEmployee() throws Exception
{
	//given -precondition
//	System.out.println(mySQLContainer.getUsername());
//	System.out.println(mySQLContainer.getPassword());
//	System.out.println(mySQLContainer.getDatabaseName());
//	
	Employee employee=Employee.builder()
				.id(1L)
				.firstname("Ramya")
				.lastname("Thirupathi")
				.email("ramya@gmail.com")
				.build();
	
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
	employeeRepository.saveAll(employee);
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
	
	Employee employee=Employee.builder()
			.firstname("Ramya")
			.lastname("Thirupathi")
			.email("ramya@gmail.com")
			.build();
	
	employeeRepository.save(employee);
	//When-action or behavior
	ResultActions response=	mockmvc.perform(MockMvcRequestBuilders.get("/employees/{id}",employee.getId()));
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
	employeeRepository.save(employee);
	
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
	
	Employee savedemployee=Employee.builder()
			.firstname("Ramya")
			.lastname("Thirupathi")
			.email("ramya@gmail.com")
			.build();
	employeeRepository.save(savedemployee);
	Employee Updatedemployee=Employee.builder()
			.firstname("Ram")
			.lastname("Thiru")
			.email("ramyathiru@gmail.com")
			.build();
	
	//When-action or behavior
	ResultActions response=	mockmvc.perform(MockMvcRequestBuilders.put("/employees/{id}",savedemployee.getId())
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
	employeeRepository.save(savedemployee);
	Employee Updatedemployee=Employee.builder()
			.firstname("Ram")
			.lastname("Thiru")
			.email("ramyathiru@gmail.com")
			.build();
	
	
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
	
	
	Employee savedemployee=Employee.builder()
			.firstname("Ramya")
			.lastname("Thirupathi")
			.email("ramya@gmail.com")
			.build();
	employeeRepository.save(savedemployee);
	
	//When-action or behavior
	ResultActions response=	mockmvc.perform(MockMvcRequestBuilders.delete("/employees/{id}",savedemployee.getId()));
     
	//then b=verify the output
	response.andExpect(MockMvcResultMatchers.status().isOk())
	.andDo(MockMvcResultHandlers.print());
}



}
