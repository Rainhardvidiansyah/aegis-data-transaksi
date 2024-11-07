package com.aegis.transaksi.service;

import com.aegis.transaksi.entity.Users;
import com.aegis.transaksi.repository.EmployeeRepository;
import com.aegis.transaksi.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Users users){
        var employee = new Employee();
        employee.setUsers(users);

        return this.employeeRepository.save(employee);
    }

    public Optional<Employee> findEmployeeById(Long id){
        return this.employeeRepository.findById(id);
    }
}
