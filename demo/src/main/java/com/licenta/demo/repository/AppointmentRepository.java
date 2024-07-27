package com.licenta.demo.repository;

import com.licenta.demo.entity.Appointment;
import com.licenta.demo.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByEmail(String email);
    Optional<List<Appointment>> findAppointmentsByDoctorAndAppointmentTime(Doctor doctor, LocalDateTime time);
}
