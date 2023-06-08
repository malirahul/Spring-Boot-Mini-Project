package com.example.aop.springaop.controller;

import com.example.aop.springaop.model.Employee;
import com.example.aop.springaop.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.annotation.PostConstruct;

@RestController
@Slf4j
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    
//    @PostConstruct
//    public void init() throws Exception
//    {
//        log.info(
//            "Bean HelloWorld has been "
//            + "instantiated and I'm the "
//            + "init() method");
//        
//    }


    @PostMapping
    public Employee save(@RequestBody Employee employee) {
        log.info("Save the Data");
        return employeeService.save(employee);
    }

    
    @GetMapping
    public ResponseEntity<Object> getEmployee() {
        List<Employee> emp = employeeService.getEmployees();
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable(value = "id") Integer id) {
        return employeeService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable("id") int id, @RequestBody Employee employee) {
        boolean isEmployeeExist = employeeService.isEmployeeExist(id);
        if (isEmployeeExist) {
            employee.setId(id);
            employeeService.updateEmployee(employee);
            return new ResponseEntity<>("Employee is updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Employee does not exist", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable("id") int id) {
        boolean isEmpExists = employeeService.isEmployeeExist(id);
        if (isEmpExists) {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>("Employee is deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
