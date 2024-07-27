package com.licenta.demo.service;

import com.licenta.demo.entity.*;
import com.licenta.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DepartmentRepository departmentRepository;
    private final ClinicRepository clinicRepository;
    private final DoctorRepository doctorRepository;

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment findAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public List<Clinic> getAllClinics() {
        return clinicRepository.findAll();
    }

    public List<Doctor> getDoctors() {
        return doctorRepository.findAll();
    }

    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    public boolean isAppointmentTimeAvailable(Long doctorId, LocalDateTime appointmentTime) {

        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        if (appointmentRepository.findAppointmentsByDoctorAndAppointmentTime(doctor, appointmentTime).isEmpty()) return true;
        else return false;
    }

    public void saveAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public Clinic findClinicById(Long id) {
        return clinicRepository.findById(id).orElse(null);
    }

    public Doctor findDoctorById(Long id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public Department findDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public List<Appointment> findAppointmentsByEmail(String email) {
        return appointmentRepository.findAllByEmail(email);
    }

    public String getNameByDoctorId(Long id) {
        return doctorRepository.findById(id).get().getName();
    }
}
