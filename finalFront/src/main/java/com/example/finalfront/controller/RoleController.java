package com.example.finalfront.controller;

import com.example.finalfront.models.Role;
import com.example.finalfront.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public String getRoles(Model model) {
        model.addAttribute("roles", roleService.getRoles());
        return "roles/list";
    }

    @GetMapping("/{id}")
    public String getRole(Model model, @PathVariable Long id) {
        model.addAttribute("role", roleService.getRole(id));
        return "roles/details";
    }

    @GetMapping("/{id}/update")
    public String updateRoleForm(Model model, @PathVariable Long id) {
        model.addAttribute("role", roleService.getRole(id));
        return "roles/update";
    }

    @PostMapping
    public String createRole(Role role) {
        roleService.createRole(role);
        return "redirect:/roles";
    }

    @PostMapping("/{id}/update")
    public String updateRole(@PathVariable Long id, Role role) {
        roleService.updateRole(id, role);
        return "redirect:/roles";
    }

    @PostMapping("/{id}/delete")
    public String deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return "redirect:/roles";
    }
}
