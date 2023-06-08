package com.example.aop.springaop.service;

import com.example.aop.springaop.annotation.CustomAnnotation;
import com.example.aop.springaop.model.Department;
import com.example.aop.springaop.model.Employee;
import com.example.aop.springaop.repository.DepartmentRepository;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	public Department save(Department department) {
		return departmentRepository.save(department);
	}

	@CustomAnnotation
	public Department findById(Integer id) {
		return departmentRepository.findById(id).orElse(null);
	}

	public List<Department> getDepartments() {
		log.info("Inside getAllDepartment method of DepartmentsService");
		return departmentRepository.findAll();
	}
}
