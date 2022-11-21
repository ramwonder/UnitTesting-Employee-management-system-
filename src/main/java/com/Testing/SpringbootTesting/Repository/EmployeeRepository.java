package com.Testing.SpringbootTesting.Repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.Testing.SpringbootTesting.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	Optional<Employee> findByEmail(String email);
    //custom query using jpql with index parameters
//	@Query("select e from Employee e where e.firstName=?1 and e.lastName=?2")
//     Employee fingByJpql(String firstname,String lastname);
	
	
	@Query("select e from Employee e where e.firstname=?1 and e.lastname=?2")
	public Employee findByname(String firstname, String lastname);
	//Nativesql
	@Query(value="select * from employees e where e.first_name=?1 and e.last_name=?2" ,nativeQuery=true)
	public Employee findByNativename(String firstname, String lastname);

}
