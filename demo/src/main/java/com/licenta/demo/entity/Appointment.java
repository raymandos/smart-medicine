package com.licenta.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Clinic clinic;
    @ManyToOne
    private Department department;
    private LocalDateTime appointmentTime;
    private String status;
    private String details;
    private String phone;
}
