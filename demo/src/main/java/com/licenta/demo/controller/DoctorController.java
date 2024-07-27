package com.licenta.demo.controller;

import com.licenta.demo.entity.Clinic;
import com.licenta.demo.entity.Doctor;
import com.licenta.demo.service.ClinicService;
import com.licenta.demo.service.DepartmentService;
import com.licenta.demo.service.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final ClinicService clinicService;
    private final DepartmentService departmentService;

    @GetMapping("/admin/doctor-management")
    public String doctorManagement(Model model) {
        List<Doctor> doctors = doctorService.findAll();
        model.addAttribute("doctors", doctors);
        model.addAttribute("clinics", clinicService.findAll());
        model.addAttribute("departments", departmentService.findAll());
        return "doctor_management";
    }

    @PostMapping("/admin/doctor-management/add")
    public String addDoctor(@ModelAttribute("doctor") final Doctor doctor) {
        doctorService.saveDoctor(doctor);
        return "redirect:/admin/doctor-management";
    }

    @PostMapping("/admin/doctor-management/delete/{id}")
    public String deleteDoctor(@PathVariable("id") Long id) throws EntityNotFoundException {
        doctorService.deleteDoctorById(id);
        return "redirect:/admin/doctor-management";
    }

    @PostMapping("/admin/doctor-management/edit/{id}")
    public String updateDoctor(@ModelAttribute("doctor") Doctor doctor, @PathVariable("id") Long id) throws EntityNotFoundException {
        doctorService.saveDoctor(doctor);
        return "redirect:/admin/doctor-management";
    }
}
