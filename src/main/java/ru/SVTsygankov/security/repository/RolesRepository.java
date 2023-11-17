package ru.SVTsygankov.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.SVTsygankov.security.model.Role;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {
}
