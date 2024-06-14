package com.devsuperior.demo.services;

import com.devsuperior.demo.dto.EmployeeDTO;
import com.devsuperior.demo.entities.Department;
import com.devsuperior.demo.entities.Employee;
import com.devsuperior.demo.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

  @Autowired private EmployeeRepository repository;

  @Transactional(readOnly = true)
  public Page<EmployeeDTO> findAll(Pageable pageable) {
    Page<Employee> result = repository.findAll(pageable);
    return result.map(EmployeeDTO::new);
  }

  @Transactional
  public EmployeeDTO create(EmployeeDTO dto) {
    Employee entity = new Employee();
    entity.setName(dto.getName());
    entity.setEmail(dto.getEmail());
    entity.setDepartment(new Department(dto.getDepartmentId(), null));
    entity = repository.save(entity);
    return new EmployeeDTO(entity);
  }
}
