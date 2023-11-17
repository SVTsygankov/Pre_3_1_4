package ru.SVTsygankov.security.service;

import org.springframework.stereotype.Service;
import ru.SVTsygankov.security.model.Role;
import ru.SVTsygankov.security.repository.RolesRepository;
import java.util.List;

@Service
public class RoleServiceImp implements RoleService {

    private final RolesRepository rolesRepository;

    public RoleServiceImp(RolesRepository roleRepository) {
        this.rolesRepository = roleRepository;
    }

    @Override
    public List<Role> findAll() {
        return rolesRepository.findAll();
    }
}
