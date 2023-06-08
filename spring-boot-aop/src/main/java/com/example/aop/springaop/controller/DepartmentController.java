package com.example.aop.springaop.controller;

import com.example.aop.springaop.model.Department;
import com.example.aop.springaop.model.Employee;
import com.example.aop.springaop.service.DepartmentService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;

	@GetMapping
	public ResponseEntity<Object> getDepartment() {
//		log.info("Inside getAllEmployee method of EmployeeController, correlationId: {}"+correlationId);     //Custom for particular method of a class

		log.info("Inside getAllEmployee method of DepartmentController");
		List<Department> dpt = departmentService.getDepartments();
		log.info("Departments Details : " + dpt);
		return new ResponseEntity<>(dpt, HttpStatus.OK);
	}

	@PostMapping
	public Department save(@RequestBody Department department) {
		return departmentService.save(department);
	}

	@GetMapping("/{id}")
	public Department findById(@PathVariable(value = "id") Integer id) {
		return departmentService.findById(id);
	}
}
