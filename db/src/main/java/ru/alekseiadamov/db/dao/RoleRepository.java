package ru.alekseiadamov.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alekseiadamov.db.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
