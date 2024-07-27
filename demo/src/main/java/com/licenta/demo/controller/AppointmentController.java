package com.licenta.demo.controller;

import com.licenta.demo.entity.Appointment;
import com.licenta.demo.entity.MyUser;
import com.licenta.demo.service.AppointmentService;
import com.licenta.demo.service.SendGridEmailService;
import com.licenta.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final SendGridEmailService emailService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("clinics", appointmentService.getAllClinics());
        model.addAttribute("departments", appointmentService.getDepartments());
        model.addAttribute("doctors", appointmentService.getDoctors());
        return "index";
    }

    @GetMapping("/admin/home")
    public String indexAdmin(Model model) {
        model.addAttribute("clinics", appointmentService.getAllClinics());
        model.addAttribute("departments", appointmentService.getDepartments());
        model.addAttribute("doctors", appointmentService.getDoctors());
        return "index_admin";
    }

    @GetMapping("/user/home")
    public String indexUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        MyUser user = userService.findUserByUsername(currentPrincipalName);
        model.addAttribute("email", user.getEmail());
        model.addAttribute("clinics", appointmentService.getAllClinics());
        model.addAttribute("departments", appointmentService.getDepartments());
        model.addAttribute("doctors", appointmentService.getDoctors());
        return "index_user";
    }

    @PostMapping("/appointment")
    public RedirectView makeAppointment(@RequestParam String firstName, @RequestParam String lastName,
                                        @RequestParam String email, @RequestParam String phone, @RequestParam Long clinic,
                                        @RequestParam Long department, @RequestParam Long doctor,
                                        @RequestParam LocalDateTime appointmentTime, @RequestParam String details) {
        try {
            Appointment appointment = new Appointment();
            appointment.setFirstName(firstName);
            appointment.setLastName(lastName);
            appointment.setEmail(email);
            appointment.setPhone(phone);
            appointment.setStatus("Pending");
            appointment.setClinic(appointmentService.findClinicById(clinic));
            appointment.setDoctor(appointmentService.findDoctorById(doctor));
            appointment.setDepartment(appointmentService.findDepartmentById(department));
            appointment.setDetails(details);
            appointment.setAppointmentTime(appointmentTime);

            appointmentService.saveAppointment(appointment);

            String subject = "Appointment Confirmation";
            String text = "Dear " + firstName + " " + lastName + ",\n\nYour appointment has been successfully submitted.\n Doctor "
                    + appointmentService.getNameByDoctorId(doctor) + " will soon confirm your appointment." +
                    "\n Meanwhile you can access localhost:8080/my-appointments for details!"
                    + "\n\nBest Regards,\nSmartMedicine";
            emailService.sendEmail(email, subject, text);

            return new RedirectView("/my-appointments/" + appointment.getEmail());
        } catch (IOException e) {
            logger.error("Error occurred while sending email.", e);
            return new RedirectView("/#appointment?emailError");
        } catch (Exception e) {
            logger.error("Error occurred while processing appointment.");
            return new RedirectView("/#appointment?error");
        }
    }
    @GetMapping("/appointment")
    public String appointmentRedirect() {
        return "redirect:/home";
    }

    @GetMapping("/admin/appointment-management")
    public String appointmentManagement(Model model) {
        List<Appointment> appointments = appointmentService.findAllAppointments();
        model.addAttribute("appointments", appointments);
        return "appointment-management";
    }

    @PostMapping("/admin/appointment-management/edit/{id}")
    public String editAppointment(@PathVariable Long id, @RequestParam String status, RedirectAttributes redirectAttributes) {
        try {
            Appointment appointment = appointmentService.findAppointmentById(id);
            if (appointment != null) {
                appointment.setStatus(status);
                appointmentService.saveAppointment(appointment);
                redirectAttributes.addFlashAttribute("successMessage", "Appointment status updated successfully.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Appointment not found.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while updating the appointment.");
        }
        return "redirect:/admin/appointment-management";
    }

    @GetMapping("/my-appointments")
    public String myAppointmentsEmail() {
        return "my_appointments_email";
    }

    @PostMapping("/my-appointments/submitEmail")
    public RedirectView submitEmail(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Redirecting to your appointments");
        return new RedirectView("/my-appointments/" + email);
    }

    @GetMapping("/my-appointments/{email}")
    public String myAppointments(@PathVariable String email, Model model) {
        model.addAttribute("appointments", appointmentService.findAppointmentsByEmail(email));
        return "my_appointments";
    }

    @PostMapping("/my-appointments/{email}/cancel/{id}")
    public String cancelAppointment(@PathVariable String email, @PathVariable Long id) {
        try {
            Appointment appointment = appointmentService.findAppointmentById(id);
            if (appointment != null) {
                appointment.setStatus("Cancelled");
                appointmentService.saveAppointment(appointment);
            }
        } catch (Exception e) {
        }
        return "redirect:/my-appointments/" + email;
    }

}
