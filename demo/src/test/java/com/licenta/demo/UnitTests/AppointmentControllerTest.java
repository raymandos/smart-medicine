package com.licenta.demo.UnitTests;

import com.licenta.demo.controller.AppointmentController;
import com.licenta.demo.entity.Appointment;
import com.licenta.demo.entity.Clinic;
import com.licenta.demo.entity.Department;
import com.licenta.demo.entity.Doctor;
import com.licenta.demo.service.AppointmentService;
import com.licenta.demo.service.SendGridEmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.RedirectView;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.LocalDateTime;

class AppointmentControllerTest {

    @InjectMocks
    private AppointmentController appointmentController;
    @Mock
    private AppointmentService appointmentService;
    @Mock
    private SendGridEmailService emailService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
    }

    @Test
    void testMakeAppointment_Success() throws IOException {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String phone = "1234567890";
        Long clinic = 1L;
        Long department = 1L;
        Long doctor = 1L;
        LocalDateTime appointmentTime = LocalDateTime.of(2024, 6, 14, 10, 0);
        String details = "Details about the appointment";

        when(appointmentService.isAppointmentTimeAvailable(doctor, appointmentTime)).thenReturn(true);
        when(appointmentService.findClinicById(clinic)).thenReturn(new Clinic());
        when(appointmentService.findDoctorById(doctor)).thenReturn(new Doctor());
        when(appointmentService.findDepartmentById(department)).thenReturn(new Department());

        RedirectView redirectView = appointmentController.makeAppointment(firstName, lastName, email, phone, clinic, department, doctor, appointmentTime, details);

        assertEquals("/my-appointments/john.doe@example.com", redirectView.getUrl());

        verify(appointmentService, times(1)).saveAppointment(any(Appointment.class));
        verify(emailService, times(1)).sendEmail(eq(email), anyString(), anyString());
    }

    @Test
    void testMakeAppointment_TimeAlreadyBooked() throws IOException {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String phone = "1234567890";
        Long clinic = 1L;
        Long department = 1L;
        Long doctor = 1L;
        LocalDateTime appointmentTime = LocalDateTime.of(2024, 6, 14, 10, 0);
        String details = "Details about the appointment";

        when(appointmentService.isAppointmentTimeAvailable(doctor, appointmentTime)).thenReturn(false);

        RedirectView redirectView = appointmentController.makeAppointment(firstName, lastName, email, phone, clinic,
                department, doctor, appointmentTime, details);

        assertEquals("/#appointment?error", redirectView.getUrl());

        verify(appointmentService, times(0)).saveAppointment(any(Appointment.class));
        verify(emailService, times(0)).sendEmail(eq(email), anyString(), anyString());
    }

    @Test
    void testMakeAppointment_ExceptionThrown() throws IOException {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String phone = "1234567890";
        Long clinic = 1L;
        Long department = 1L;
        Long doctor = 1L;
        LocalDateTime appointmentTime = LocalDateTime.of(2024, 6, 14, 10, 0);
        String details = "Details about the appointment";

        when(appointmentService.isAppointmentTimeAvailable(doctor, appointmentTime)).thenReturn(true);
        when(appointmentService.findClinicById(clinic)).thenReturn(new Clinic());
        when(appointmentService.findDoctorById(doctor)).thenReturn(new Doctor());
        when(appointmentService.findDepartmentById(department)).thenReturn(new Department());
        doThrow(new RuntimeException("Database error")).when(appointmentService).saveAppointment(any(Appointment.class));

        RedirectView redirectView = appointmentController.makeAppointment(firstName, lastName, email, phone, clinic, department, doctor, appointmentTime, details);

        assertEquals("/#appointment?error", redirectView.getUrl());

        verify(appointmentService, times(1)).saveAppointment(any(Appointment.class));
        verify(emailService, times(0)).sendEmail(eq(email), anyString(), anyString());
    }

    @Test
    void testMakeAppointment_EmailIOException() throws IOException {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String phone = "1234567890";
        Long clinic = 1L;
        Long department = 1L;
        Long doctor = 1L;
        LocalDateTime appointmentTime = LocalDateTime.of(2024, 6, 14, 10, 0);
        String details = "Details about the appointment";

        when(appointmentService.isAppointmentTimeAvailable(doctor, appointmentTime)).thenReturn(true);
        when(appointmentService.findClinicById(clinic)).thenReturn(new Clinic());
        when(appointmentService.findDoctorById(doctor)).thenReturn(new Doctor());
        when(appointmentService.findDepartmentById(department)).thenReturn(new Department());
        doThrow(new IOException("Email service error")).when(emailService).sendEmail(eq(email), anyString(), anyString());

        RedirectView redirectView = appointmentController.makeAppointment(firstName, lastName, email, phone, clinic, department, doctor, appointmentTime, details);

        assertEquals("/#appointment?emailError", redirectView.getUrl());

        verify(appointmentService, times(1)).saveAppointment(any(Appointment.class));
        verify(emailService, times(1)).sendEmail(eq(email), anyString(), anyString());
    }
}