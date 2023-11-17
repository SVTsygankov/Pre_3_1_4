package ru.SVTsygankov.security.service;

import ru.SVTsygankov.security.model.Role;
import java.util.List;

public interface RoleService {
    List<Role> findAll();
}

