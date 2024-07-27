package com.licenta.demo.controller;

import com.licenta.demo.entity.Clinic;
import com.licenta.demo.service.ClinicService;
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
public class ClinicController {
    private final ClinicService clinicService;

    @GetMapping("/admin/clinic-management")
    public String clinicManagement(Model model) {
        List<Clinic> clinics = clinicService.findAll();
        model.addAttribute("clinics", clinics);
        return "clinic_management";
    }

    @PostMapping("/admin/clinic-management/add")
    public String addClinic(@ModelAttribute("clinic") final Clinic clinic) {
        clinicService.saveClinic(clinic);
        return "redirect:/admin/clinic-management";
    }

    @PostMapping("/admin/clinic-management/delete/{id}")
    public String deleteClinic(@PathVariable("id") Long id) throws EntityNotFoundException {
        clinicService.deleteClinicById(id);
        return "redirect:/admin/clinic-management";
    }

    @PostMapping("/admin/clinic-management/edit/{id}")
    public String updateClinic(@ModelAttribute("clinic") Clinic clinic, @PathVariable("id") Long id) throws EntityNotFoundException {
        clinicService.saveClinic(clinic);
        return "redirect:/admin/clinic-management";
    }

}
