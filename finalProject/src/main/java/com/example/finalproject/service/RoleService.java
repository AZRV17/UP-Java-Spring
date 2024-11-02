package com.example.finalproject.service;

import com.example.finalproject.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();

    Role getRole(Long id);

    Role createRole(Role role);

    void deleteRole(Long id);

    Role updateRole(Role role);
}
