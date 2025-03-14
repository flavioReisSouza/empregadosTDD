package com.devsuperior.demo.controllers;

import com.devsuperior.demo.dto.EmployeeDTO;
import com.devsuperior.demo.services.EmployeeService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

  @Autowired private EmployeeService service;

  @GetMapping
  public ResponseEntity<Page<EmployeeDTO>> findAll(Pageable pageable) {
    PageRequest pageRequest =
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
    Page<EmployeeDTO> result = service.findAll(pageRequest);
    return ResponseEntity.ok().body(result);
  }

  @PostMapping
  public ResponseEntity<EmployeeDTO> create(@RequestBody EmployeeDTO dto) {
    dto = service.create(dto);
    URI uri =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(dto.getId())
            .toUri();
    return ResponseEntity.created(uri).body(dto);
  }
}