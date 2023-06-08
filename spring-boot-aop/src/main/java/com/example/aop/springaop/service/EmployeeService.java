package com.example.aop.springaop.service;

import com.example.aop.springaop.model.Employee;
import com.example.aop.springaop.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	@Cacheable(key = "#id", value = "employees")
	public Employee findById(Integer id) {
		log.info("Inside findById method of Employee");
		return employeeRepository.findById(id).orElseThrow();
	}

	@Cacheable(value = "employees")
	public List<Employee> getEmployees() {
		log.info("Inside getAllEmployee method of EmployeeService");
		return employeeRepository.findAll();
	}

	@CachePut(key = "#emp", value = "employees")
	public void updateEmployee(Employee emp) {
		log.info("Inside updateEmployeeByID method of Employee");
		employeeRepository.save(emp);

	}

	@CachePut(key = "#id", value = "employees")
	public boolean isEmployeeExist(int id) {
		return employeeRepository.existsById(id);

	}

	@CacheEvict(key = "#id", value = "employees")
	public void deleteEmployee(int id) {
		log.info("Inside deleteEmployee method of Employee");
		employeeRepository.deleteById(id);

	}

}
