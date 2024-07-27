package com.licenta.demo.service;

import com.licenta.demo.entity.Department;
import com.licenta.demo.entity.Doctor;
import com.licenta.demo.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }
}
