package com.licenta.demo.controller;

import com.licenta.demo.entity.MyUser;
import com.licenta.demo.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/admin/user-management")
    public String userManagement(Model model) {
        List<MyUser> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user_management";
    }

    @PostMapping("/admin/user-management/edit/{id}")
    public String editUser(@PathVariable Long id, @RequestParam String role, RedirectAttributes redirectAttributes) {
        try {
            MyUser user = userService.findUserById(id);
            if (user != null) {
                user.setRole(role);
                userService.saveUser(user);
                redirectAttributes.addFlashAttribute("successMessage", "User role updated successfully.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "User not found.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while updating the user.");
        }
        return "redirect:/admin/user-management";
    }

    @PostMapping("/admin/user-management/delete/{id}")
    public String deleteUser(@PathVariable Long id) throws EntityNotFoundException {
        userService.deleteUser(id);
        return "redirect:/admin/user-management";
    }
}

@Getter
@Setter
class UserRoleUpdateRequest {
    private Long id;
    private String role;
}

